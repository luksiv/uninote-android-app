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
 * Struktura sauganti rikiavimo struktura (rikiavimo laukas, kiekis)
 */
struct rikiavimoStruktura
{

    int rikiavimoLaukas;
    int kiekis;

  public:
    // getteriai ir setteriai
    int getRikiavimoLaukas()
    {
        return rikiavimoLaukas;
    }

    int getKiekis()
    {
        return kiekis;
    }

    void incKiekis()
    {
        kiekis += 1;
    }

    void decKiekisBy(int decKiekis)
    {
        #pragma omp critical(keiciaKieki)
        {
            kiekis -= decKiekis;
        }
    }

    void reset()
    {
        #pragma omp critical(keiciaKieki)
        {
            rikiavimoLaukas = -1;
            kiekis = -1;
        }
    }
};

/**
 * Struktura skirta saugoti automobilio duomenis
 */
struct Automobilis
{
    string modelis;
    int metai;
    double sanaudos;

  public:
    string getModelis()
    {
        return modelis;
    }

    int getMetai()
    {
        return metai;
    }

    double getSanaudos()
    {
        return sanaudos;
    }
};

/**
 * Rasytojo struktura
 */
struct Rasytojas
{
    Automobilis automobiliai[maxEle];
    int automobiliuKiekis = 0;
    int rasytojoNr;

  public:
    Automobilis getAutomobili(int index)
    {
        return automobiliai[index];
    }
};

/**
 * Skaitytojo struktura
 */
struct Skaitytojas
{
    rikiavimoStruktura strukturos[maxEle];
    int strukturuKiekis = 0;
};

/**
 * Monitorius skirtas apsaugoti kritines sekcijas nuo lygiagrecios prieigos ir uztikrinanti tarpusavio isskyrima
 */
class Monitorius
{
  private:
    rikiavimoStruktura rikiavimoStrukturos[maxBen];
    int strukturuKiekis = 0;
    bool dirbantysRasytojai[RPS];

    /**
    * Funkcija randa indeksa, kuriame ideti
    *
    * int laukas - laukas pagal kuri ieskos vietos
    * grazina indeksa masyve
    */
    int rastiIterpimoIndeksa(int laukas)
    {
        // Jei masyvas tuscias, indeksas yra 0 (pirma vieta)
        if (strukturuKiekis == 0)
        {
            return 0;
        }
        // ieskoma didesne reiksme, jei randama, tai reiskia, kad elementas yra pries ta didesne reiksme
        for (int i = 0; i < strukturuKiekis; i++)
        {
            if (laukas <= rikiavimoStrukturos[i].getRikiavimoLaukas())
            {
                return i;
            }
        }
        // Nebuvo rastas didesnis elementas, tai jis dedamas i gala
        return strukturuKiekis;
    }

  public:
    // Konstruktorius
    Monitorius()
    {
        for (int i = 0; i < RPS; i++)
        {
            dirbantysRasytojai[i] = true;
        }
    }

    int getStrukturuKiekis()
    {
        return strukturuKiekis;
    }

    rikiavimoStruktura getStruktura(int index)
    {
        return rikiavimoStrukturos[index];
    }

    /**
     * sinchronizuotas metodas, kuris nustato, kad rasytojas nr x nebedirbs
     * Sinchronizacijos argumentacija
     * Sinchronizuotas metodas del to, kad prie dirbantysRasytojai kintamojo gali noreti prieiti skaitytojas,
     * kuris nori patikrinti ar yra dirbanciu rasytoju
     */
    void setRasytojasNebedirba(int rasytojoNr)
    {
        #pragma omp critical(accessDirbantysRasytojai)
        {
            dirbantysRasytojai[rasytojoNr] = false;
        }
    }

    /**
     * sinchronizuotas metodas, kuris tikrina ar yra dirbanciu rasytoju
     * Sinchronizacijos argumentacija
     * Sinchronizuotas metodas del to, kad prie dirbantysRasytojai kintamojo gali noreti prieiti rasytojas,
     * kuris nori pakeisti reiksme masyvo
     */
    bool arYraDirbanciuRasytoju()
    {
        bool arYra = false;
        #pragma omp critical(accessDirbantysRasytojai)
        {
            for (int i = 0; i < RPS; i++)
            {
                if (dirbantysRasytojai[i])
                {
                    arYra = true;
                    break;
                }
            }
        }
        return arYra;
    }

    /**
     * Sinchronizuotas metodas, skirtas iterpti automobilius i duomenu strukturos masyva
     * Sinchronizacijos argumentacija
     * Metodas turi buti sinchronizuotas, nes daugiau nei vienas rasytojas gali noreti tuo paciu metu rasyti i masyva
     *
     * Automobilis automobilis - automobilio objektas, kurio duomenys bus dedami i masyva
     */
    void iterptiAutomobili(Automobilis automobilis)
    {
        #pragma omp critical(updateArray)
        {
            int laukas = automobilis.getMetai();
            int indeksas = rastiIterpimoIndeksa(laukas);

            // jei nera nei vieno lauko arba jeigu laukas bus idedamas gale
            // sukuriamas naujas laukas ir priskiriama kiekis 1
            if (indeksas == strukturuKiekis)
            {
                rikiavimoStruktura naujaStruktura;
                naujaStruktura.rikiavimoLaukas = laukas;
                naujaStruktura.kiekis = 1;
                rikiavimoStrukturos[indeksas] = naujaStruktura;
                strukturuKiekis++;
            }
            // jei yra laukas rastas, padidinamas kiekis vienetu
            else if (rikiavimoStrukturos[indeksas].getRikiavimoLaukas() == laukas)
            {
                rikiavimoStrukturos[indeksas].incKiekis();
            }
            // jei naujas laukas yra kazkur tarp esamu
            else
            {
                // prastumiam masyvo elementus, kad padaryti vietos naujam elementui
                for (int i = strukturuKiekis; i > indeksas; i--)
                {
                    rikiavimoStrukturos[i] = rikiavimoStrukturos[i - 1];
                }
                rikiavimoStruktura naujaStruktura;
                naujaStruktura.rikiavimoLaukas = laukas;
                naujaStruktura.kiekis = 1;
                rikiavimoStrukturos[indeksas] = naujaStruktura;
                strukturuKiekis++;
            }
        }
    }

    /**
     * sinchronizuotas metodas, skirtas salinti elementus is duomenu strukturos
     * Sinchronizacijos argumentacija
     * Metodas turi buti sinchronizuotas, nes trinti elementus gali noreti daugiau nei vienas skaitytojas
     *
     * @param rikiavimoStruktura - struktura, pagal kuri bus trinami duomenys
     * @return ar buvo istrinti duomenys
     */
    bool trintiElementa(rikiavimoStruktura struktura)
    {
        // jei nera strukturu, bet yra bent vienas dirbantis rasytojas,
        // tai potencialiai gali buti idetos naujos strukturos ir del to laukiame
        while (strukturuKiekis == 0 && arYraDirbanciuRasytoju())
        {
        }

        // jei nera elementu ir nera rasytoju, tai pasikeitimu nebus ir nera ko istrinti
        if (strukturuKiekis == 0 && !arYraDirbanciuRasytoju())
        {
            return false;
        }
        bool rastaIrPasalinta = false;
        #pragma omp critical(updateArray)
        {
            // ieskome rikiavimo lauko visame masyve
            for (int i = 0; i < strukturuKiekis; i++)
            {
                if (rikiavimoStrukturos[i].getRikiavimoLaukas() == struktura.getRikiavimoLaukas())
                {
                    // jei reikiamas kiekis mazesnis uz esama
                    if (rikiavimoStrukturos[i].kiekis > struktura.kiekis)
                    {
                        rikiavimoStrukturos[i].decKiekisBy(struktura.kiekis);
                    }
                    // jei kiekis didesnis arba lygus uz esamam
                    else
                    {
                        // jei tai vienintele struktura arba jei tai struktura esanti masyvo gale,
                        // tai tiesiog priskiriam null
                        if (i == 0 && strukturuKiekis == 1 || i == strukturuKiekis - 1)
                        {
                            rikiavimoStrukturos[i].reset();
                            strukturuKiekis--;
                        }
                        // jei struktura ne vienintele ir vieta yra ne pabaigoje
                        else
                        {
                            // paslenkamos strukturos i <<<<< puse
                            for (int j = i; j < strukturuKiekis - 1; j++)
                                rikiavimoStrukturos[j] = rikiavimoStrukturos[j + 1];
                            // saliname paskutine struktura, nes paskutine-1 ir paskutine yra vienodos
                            rikiavimoStrukturos[strukturuKiekis - 1].reset();
                            strukturuKiekis--;
                        }
                    }
                    rastaIrPasalinta = true;
                }
            }
        }
        return rastaIrPasalinta;
    }
};

void duomenuNuskaitymas(string failoVieta, Rasytojas R[], Skaitytojas S[])
{
    ifstream failas(failoVieta);
    /*
	Gamintojo duomenu nuskaitymas
	*/
    int automobiliuKiekis;
    for (int i = 0; i < RPS; i++)
    {
        failas >> automobiliuKiekis;
        R[i].automobiliuKiekis = automobiliuKiekis;
        for (int j = 0; j < automobiliuKiekis; j++)
        {
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
    int strukturuKiekis;
    for (int i = 0; i < SPS; i++)
    {
        failas >> strukturuKiekis;
        S[i].strukturuKiekis = strukturuKiekis;
        for (int j = 0; j < strukturuKiekis; j++)
        {
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

void duomenuSpausdinimas(string failoVieta, Rasytojas R[], Skaitytojas S[])
{
    ofstream failas(failoVieta);
    failas << "**********************************************" << endl;
    failas << "*             PRADINIAI DUOMENYS             *" << endl;
    failas << "**********************************************" << endl;
    string skirtukas = "+--------------------------------------------+";
    failas << skirtukas << endl;
    failas << "|            Automobiliu duomenys            |" << endl;

    for (int i = 0; i < RPS; i++)
    {
        failas << skirtukas << endl;
        failas << "|             " << i + 1 << " duomenu rinkinys             |" << endl;
        failas << skirtukas << endl;
        failas << setw(2) << right << "| # " << setw(17) << left << "| Modelis " << setw(12) << left << "| Metai " << setw(12) << left << "| Sanaudos "
               << "|" << endl;
        failas << skirtukas << endl;
        for (int j = 0; j < R[i].automobiliuKiekis; j++)
        {
            failas << "| " << setw(2) << left << j + 1 << setw(2) << "| " << setw(15) << left << R[i].automobiliai[j].modelis << "| " << setw(10) << left << R[i].automobiliai[j].metai << "| " << setw(10) << left << R[i].automobiliai[j].sanaudos << "|" << endl;
        }
    }
    failas << skirtukas << endl
           << endl;

    skirtukas = "+---------------------------+";
    failas << skirtukas << endl;
    failas << "|    Rikiavimo struktūros   |" << endl;
    failas << skirtukas << endl;
    for (int i = 0; i < SPS; i++)
    {
        failas << skirtukas << endl;
        failas << "|   " << i + 1 << " rikiavimo struktura   |" << endl;
        failas << skirtukas << endl;
        failas << setw(4) << left << "| #" << setw(15) << left << "| Rik. laukas" << setw(8) << left << "| Kiekis |" << endl;
        failas << skirtukas << endl;
        for (int j = 0; j < S[i].strukturuKiekis; j++)
        {
            failas << "| " << setw(2) << left << j + 1 << setw(2) << "| " << setw(13) << left << S[i].strukturos[j].rikiavimoLaukas << "| " << setw(7) << left << S[i].strukturos[j].kiekis << "|" << endl;
        }
    }
    failas << skirtukas << endl
           << endl;
    failas.close();
}

void rezultatuSpausdinimas(string failoVieta, Monitorius *monitorius)
{
    ofstream failas(failoVieta, ios::app);
    failas << "**********************************************" << endl;
    failas << "*                 REZULTATAI                 *" << endl;
    failas << "**********************************************" << endl;
    string skirtukas = "+---------------------------+";
    failas << skirtukas << endl;
    failas << "|  Duomenu strukt. masyvas  |" << endl;
    failas << skirtukas << endl;
    if (monitorius->getStrukturuKiekis() < 1)
    {
        failas << "|      Masyvas tuscias      |" << endl;
    }
    else
    {
        failas << setw(4) << left << "| #" << setw(15) << left << "| Rik. laukas" << setw(8) << left << "| Kiekis |" << endl;
        failas << skirtukas << endl;
        for (int i = 0; i < monitorius->getStrukturuKiekis(); i++)
        {
            rikiavimoStruktura struktura = monitorius->getStruktura(i);
            failas << "| " << setw(2) << left << i + 1 << setw(2) << "| " << setw(13) << left << struktura.rikiavimoLaukas << "| " << setw(7) << left << struktura.kiekis << "|" << endl;
        }
    }
    failas << skirtukas << endl;
}

void pradetiProcesus(Monitorius *monitorius, Rasytojas R[], Skaitytojas S[])
{
    // VPS (visu procesu skaicius) bus RPS (Rasytoju) ir SPS (Skaitytoju) suma, todel nustatom, kad bus VPS procesu
    #pragma omp parallel num_threads(VPS)
    {
        // gauname gijos id
        int gijosId = omp_get_thread_num();
        // gijos kuriu id mazesnis nei RPS bus rasytojai
        if (gijosId < RPS)
        {
            // RASYTOJAI
            // Cia nusakoma ka darys rasytojai
            Rasytojas rasytojas = R[gijosId];
            rasytojas.rasytojoNr = gijosId;
            // iterpiame visus automobilius
            for (int i = 0; i < rasytojas.automobiliuKiekis; i++)
            {
                monitorius->iterptiAutomobili(rasytojas.getAutomobili(i));
            }
            // nustatome, kad rasytojas nebedirba
            monitorius->setRasytojasNebedirba(rasytojas.rasytojoNr);

            #pragma omp critical
            {
                cout << "Rasytojas [" << gijosId << "] baige darba." << endl;
            }
        }
        // visos kitos gijos bus skaitytoju
        else
        {
            // SKAITYTOJAI
            // Cia nusakoma ka darys skaitytojai
            int skaitytojoNr = gijosId - RPS;
            while (true)
            {
                bool istryneKazka = false;
                for (int i = 0; i < S[skaitytojoNr].strukturuKiekis; i++)
                {
                    // istrinami elementai
                    bool istryne = false;
                    if (S[skaitytojoNr].strukturos[i].kiekis > 0)
                        if (istryneKazka = monitorius->trintiElementa(S[skaitytojoNr].strukturos[i]))
                            istryneKazka = true;
                }
                // jei skaitytojas nieko neistryne ir nebera rasytoju, tai reiskia nebeturi, ko istrinti
                if (!istryneKazka && !monitorius->arYraDirbanciuRasytoju())
                {
                    // paskutini karta praeiname pro strukturas, nes gal kanors kol tikrinome idejo
                    for (int i = 0; i < S[skaitytojoNr].strukturuKiekis; i++)
                        if (S[skaitytojoNr].strukturos[i].kiekis > 0)
                        {
                            monitorius->trintiElementa(S[skaitytojoNr].strukturos[i]);
                        }
                    #pragma omp critical
                    {
                        cout << "Skaitytojas [" << skaitytojoNr << "] baige darba." << endl;
                    }
                    break;
                }
            }
        }
    }
}

int main()
{
    Rasytojas R[RPS];
    Skaitytojas S[SPS];
    /*  
    * kuriant monitoriu padarome ji pointeriu, 
    * kad visi procesai naudotusi tuo paciu objektu (tame paciame adrese), o ne savo kopijomis
    */
    Monitorius *monitorius = new Monitorius();

    /*
    * Variantas 0 - po salinimo duomenu struktura tuscia.
    * Variantas 1 - is duomenu strukturos nepasalinamas nei vienas elementas
    * Variantas 2 - is duomenu strukturos pasalinama dalis elementu. 
    * Variantas 2 - Pasalinama : (2002 2006 2010 2011 2012 2016 2018 ) 
    * Variantas 2 - Lieka      : (1995 2004 2007 2015 2017)
    */
    int variantas = 2;

    duomenuNuskaitymas(duomenuVariantai[variantas], R, S);
    cout << "Duomenys nuskaityti" << endl;
    duomenuSpausdinimas(rezultatuFailoPavadinimas, R, S);
    cout << "Duomenys isspausdinti" << endl;
    cout << "Pradedami rasytoju ir skaitytoju procesai" << endl;
    pradetiProcesus(monitorius, R, S);
    cout << "Visi procesai baigti" << endl;
    rezultatuSpausdinimas(rezultatuFailoPavadinimas, monitorius);
    cout << "Programa baigta" << endl;
    return 0;
}