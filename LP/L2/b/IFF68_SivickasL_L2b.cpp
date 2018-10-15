/*
Variantas - LD2b
Autorius - Lukas Sivickas
 */
#include <iostream>
#include <iomanip>
#include <fstream>
#include <string>
#include <omp.h>

using namespace std;

// Rezultatu failas
string rezultatuFailoPavadinimas = "IFF68_SivickasL_L2a_rez.txt";
// Duomenu failai
string duomenuVariantai[] = {"IFF68_SivickasL_L2_dat_1.txt", "IFF68_SivickasL_L2_dat_2.txt", "IFF68_SivickasL_L2_dat_3.txt"};
// Rasytojo procesu kiekis
const int RPS = 5;
// Skaitytoju procesu kiekis
const int SPS = 4;
// Viso procesu kiekis
const int VPS = RPS + SPS;
// Max elementu duomenu masyve
const int maxEle = 10;
// Max elementu bendram masyve
const int maxBen = 100;

/**
 * Klase sauganti rikiavimo struktura (rikiavimo laukas, kiekis)
 */
struct rikiavimoStruktura {

    int rikiavimoLaukas;
    int kiekis;
    
    public:
        // getteriai ir setteriai
        int getRikiavimoLaukas() {
            return rikiavimoLaukas;
        }

        int getKiekis() {
            return kiekis;
        }

        void incKiekis() {
            kiekis += 1;
        }

        void decKiekis() {
            kiekis -= 1;
        }
};

struct Automobilis{
    string modelis;
    int metai;
    double sanaudos;

    public:
        string getModelis() {
            return modelis;
        }

        int getMetai() {
            return metai;
        }

        double getSanaudos() {
            return sanaudos;
        }
};

/**
 * Rasytojo klase, kuri rasys duomenis
 */
struct Rasytojas{
    Automobilis automobiliai[maxEle];
    int automobiliuKiekis = 0;
    int rasytojoNr;
};

/**
 * Skaitytojo klase, kuri "skaitys"(trins) duomenis
 */
struct Skaitytojas{
    rikiavimoStruktura strukturos[maxEle];
    int strukturuKiekis = 0;
};

class Monitorius{
    private:
        rikiavimoStruktura rikiavimoStrukturos[maxBen];
        int strukturuKiekis = 0;
        bool dirbantysRasytojai[RPS];

        /**
         * Funkcija randa indeksa, kuriame ideti
         *
         * @param laukas - laukas pagal kuri ieskos vietos
         * @return indeksas masyve
         */
        int rastiIterpimoIndeksa(int laukas) {
            // Jei masyvas tuscias, indeksas yra 0 (pirma vieta)
            if (strukturuKiekis == 0) {
                return 0;
            }
            // ieskoma didesne reiksme, jei randama, tai reiskia, kad elementas yra pries ta didesne reiksme
            for (int i = 0; i < strukturuKiekis; i++) {
                if (laukas <= rikiavimoStrukturos[i].getRikiavimoLaukas()) {
                    return i;
                }
            }
            // Nebuvo rastas didesnis elementas, tai jis dedamas i gala
            return strukturuKiekis;
        }

    public:
        Monitorius(){
            for( int i = 0; i < RPS; i++){
                dirbantysRasytojai[i] = true;
            }
        }

        int getStrukturuKiekis(){
            return strukturuKiekis;
        }

        rikiavimoStruktura getStruktura(int index){
            if(!(index < 0 || index >= maxBen))
                return rikiavimoStrukturos[index];
            else{
                throw out_of_range("getStruktura(int index) | index out of range.");
            }
        }

        void setRasytojasNebedirba(int rasytojoNr){
        dirbantysRasytojai[rasytojoNr] = false;
        }

        /**
         * sinchronizuotas metodas, kuris tikrina ar yra dirbanciu rasytoju
         * Sinchronizacijos argumentacija
         * Sinchronizuotas metodas del to, kad prie dirbantysRasytojai kintamojo gali noreti prieiti rasytojas,
         * kuris nori pakeisti reiksme masyvo
         */
        bool arYraDirbanciuRasytoju() {
            for (int i = 0; i < RPS; i++) {
                if (dirbantysRasytojai[i]) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Sinchronizuotas metodas, skirtas iterpti automobilius i duomenu strukturos masyva
         * Sinchronizacijos argumentacija
         * Metodas turi buti sinchronizuotas, nes daugiau nei vienas rasytojas gali noreti tuo paciu metu rasyti i masyva
         * @param automobilis - automobilio objektas, kurio duomenys bus dedami i masyva
         */
        void iterptiAutomobili(Automobilis automobilis) {
            #pragma omp critical{
                int laukas = automobilis.getMetai();
                int indeksas = rastiIterpimoIndeksa(laukas);

                // jei nera nei vieno lauko arba jeigu laukas bus idedamas gale
                // sukuriamas naujas laukas ir priskiriama kiekis 1
                if (indeksas == strukturuKiekis) {
                    rikiavimoStruktura naujaStruktura;
                    naujaStruktura.rikiavimoLaukas = laukas;
                    naujaStruktura.kiekis = 1;
                    rikiavimoStrukturos[indeksas] = naujaStruktura;
                    strukturuKiekis++;
                }
                // jei yra laukas rastas, padidinamas kiekis vienetu
                else if (rikiavimoStrukturos[indeksas].getRikiavimoLaukas() == laukas) {
                    rikiavimoStrukturos[indeksas].incKiekis();
                }
                // jei naujas laukas yra kazkur tarp esamu
                else {
                    // prastumiam masyvo elementus, kad padaryti vietos naujam elementui
                    for (int i = strukturuKiekis; i > indeksas; i--) {
                        rikiavimoStrukturos[i] = rikiavimoStrukturos[i - 1];
                    }
                    rikiavimoStruktura naujaStruktura;
                    naujaStruktura.rikiavimoLaukas = laukas;
                    naujaStruktura.kiekis = 1;
                    rikiavimoStrukturos[indeksas] = naujaStruktura;
                    strukturuKiekis++;
                }
            }
        };

void duomenuNuskaitymas(string failoVieta, Rasytojas R[], Skaitytojas S[]);
void duomenuSpausdinimas(string failoVieta, Rasytojas R[], Skaitytojas S[]);
void rezultatuSpausdinimas(string failoVieta, Monitorius *monitorius);
void pradetiProcesus();

int main(){

    Rasytojas R[RPS];
    Skaitytojas S[SPS];
    Monitorius *monitorius = new Monitorius();

    /*
    * Variantas 0 - po salinimo duomenu struktura tuscia.
    * Variantas 1 - is duomenu strukturos nepasalinamas nei vienas elementas
    * Variantas 2 - is duomenu strukturos pasalinama dalis elementu.
    */
    int variantas = 0;

    duomenuNuskaitymas(duomenuVariantai[variantas], R, S);
    duomenuSpausdinimas(rezultatuFailoPavadinimas, R, S);

    rezultatuSpausdinimas(rezultatuFailoPavadinimas, monitorius);
    
    delete(monitorius);
    return 0;
}

void duomenuNuskaitymas(string failoVieta, Rasytojas R[], Skaitytojas S[]) {
	ifstream failas(failoVieta);
	/*
	Gamintojo duomenu nuskaitymas
	*/
	int kiekis;
	for (int i = 0; i < RPS; i++) {
		failas >> kiekis;
		R[i].automobiliuKiekis = kiekis;
		for (int j = 0; j < kiekis; j++) {
			Automobilis automobilis;
			string modelis;
			int metai;
			double sanaudos;
			failas >> modelis >> metai >> sanaudos;
			automobilis.modelis = modelis;
			automobilis.metai = metai;
			automobilis.sanaudos = sanaudos;
			R[i].automobiliai[j] = automobilis;
		}
	}
	failas.ignore(256, '\n');
	for (int i = 0; i < SPS; i++) {
		failas >> kiekis;
		S[i].strukturuKiekis = kiekis;
		for (int j = 0; j < kiekis; j++) {
			rikiavimoStruktura rikStrukt;
			int rikiavimoLaukas;
			int kiekis;
			failas >> rikiavimoLaukas >> kiekis;
			rikStrukt.rikiavimoLaukas = rikiavimoLaukas;
			rikStrukt.kiekis = kiekis;
			S[i].strukturos[j] = rikStrukt;
		}
	}
	failas.close();
}

void duomenuSpausdinimas(string failoVieta, Rasytojas R[], Skaitytojas S[]){
    ofstream failas(failoVieta);
    failas << "**********************************************" << endl;
    failas << "*             PRADINIAI DUOMENYS             *" << endl;
    failas << "**********************************************" << endl;
    string skirtukas = "+--------------------------------------------+";
    failas << skirtukas << endl;
    failas << "|            Automobiliu duomenys            |" << endl;

	for (int i = 0; i < RPS; i++){
        failas << skirtukas << endl;
        failas << "|             " << i+1 <<  " duomenu rinkinys             |" << endl;
        failas << skirtukas << endl;
        failas << setw(2) << right <<"| # " << setw(17) << left <<"| Modelis " << setw(12) << left <<"| Metai " << setw(12) << left <<"| Sanaudos " << "|" << endl;
        failas << skirtukas << endl;
        for (int j = 0; j < R[i].automobiliuKiekis; j++){
            failas << "| " << setw(2) << left << j+1 << setw(2) << "| " << setw(15) << left << R[i].automobiliai[j].modelis << "| " << setw(10) << left << R[i].automobiliai[j].metai <<"| " << setw(10) << left << R[i].automobiliai[j].sanaudos << "|" << endl;
        }
    }
    failas << skirtukas << endl << endl;

    skirtukas = "+---------------------------+";
    failas << skirtukas << endl;
    failas << "|    Rikiavimo struktūros   |" << endl;
    failas << skirtukas << endl;
    for (int i = 0; i < SPS; i++){
        failas << skirtukas << endl;
        failas << "|   " << i+1 << " rikiavimo struktura   |" << endl;
        failas << skirtukas << endl;
        failas << setw(4) << left << "| #" << setw(15) << left << "| Rik. laukas" << setw(8) << left << "| Kiekis |" << endl;
        failas << skirtukas << endl;
        for(int j = 0; j < S[i].strukturuKiekis; j++){
            failas << "| " << setw(2) << left << j+1 << setw(2) << "| " << setw(13) << left << S[i].strukturos[j].rikiavimoLaukas << "| " << setw(7) << left << S[i].strukturos[j].kiekis << "|" << endl;
        }
    }
    failas << skirtukas << endl << endl;
	failas.close();
}

void rezultatuSpausdinimas(string failoVieta, Monitorius *monitorius){
    ofstream failas(failoVieta, ios::app);
    failas << "**********************************************" << endl;
    failas << "*                 REZULTATAI                 *" << endl;
    failas << "**********************************************" << endl;
    string skirtukas = "+---------------------------+";
    failas << skirtukas << endl;
    failas << "|  Duomenu strukt. masyvas  |" << endl;
    failas << skirtukas << endl;
    if(monitorius->getStrukturuKiekis() < 1){
        failas << "|      Masyvas tuscias      |" << endl;
    } else {
        failas << setw(4) << left << "| #" << setw(15) << left << "| Rik. laukas" << setw(8) << left << "| Kiekis |" << endl;
        failas << skirtukas << endl;
        for (int i = 0; i < monitorius->getStrukturuKiekis(); i++) {
            rikiavimoStruktura struktura = monitorius->getStruktura(i);
            failas << "| " << setw(2) << left << i+1 << setw(2) << "| " << setw(13) << left << struktura.rikiavimoLaukas << "| " << setw(7) << left << struktura.kiekis << "|" << endl;
        }
    }
    failas << skirtukas << endl;    
}
