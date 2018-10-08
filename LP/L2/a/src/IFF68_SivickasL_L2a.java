/*
Variantas - LD2a
Autorius - Lukas Sivickas
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

//TODO: perrasyti
// Klasė, sauganti reikiamų rikiavimo laukų ir jų kiekių objektus
class rikiavimoStruktura {

    private int rikiavimoLaukas;
    private int kiekis;

    // Konstruktorius
    public rikiavimoStruktura(int rikiavimoLaukas, int kiekis) {
        this.rikiavimoLaukas = rikiavimoLaukas;
        this.kiekis = kiekis;
    }

    // Duomenų ėmimo bei talpinimo metodai

    public int getRikiavimoLaukas() {
        return rikiavimoLaukas;
    }

    public void setRikiavimoLaukas(int rikiavimoLaukas) {
        this.rikiavimoLaukas = rikiavimoLaukas;
    }

    public int getKiekis() {
        return kiekis;
    }

    public void setKiekis(int kiekis) {
        this.kiekis = kiekis;
    }

    public void incKiekis() {
        this.kiekis += 1;
    }

    public void decKiekis() {
        this.kiekis -= 1;
    }

    @Override
    public String toString() {
        return "rikiavimoStruktura{" +
                "rikiavimoLaukas=" + rikiavimoLaukas +
                ", kiekis=" + kiekis +
                '}';
    }
}

//TODO: perrasyti
// Klase skirta saugoti automobilio objektus
class Automobilis {
    String modelis;
    int metai;
    double sanaudos;

    // Konstruktorius
    public Automobilis(String modelis, int metai, double sanaudos) {
        this.modelis = modelis;
        this.metai = metai;
        this.sanaudos = sanaudos;
    }

    // Getteriai ir setteriai
    public String getModelis() {
        return modelis;
    }

    public void setModelis(String modelis) {
        this.modelis = modelis;
    }

    public int getMetai() {
        return metai;
    }

    public void setMetai(int metai) {
        this.metai = metai;
    }

    public double getSanaudos() {
        return sanaudos;
    }

    public void setSanaudos(double sanaudos) {
        this.sanaudos = sanaudos;
    }

    @Override
    public String toString() {
        return "Automobilis{" +
                "modelis='" + modelis + '\'' +
                ", metai=" + metai +
                ", sanaudos=" + sanaudos +
                '}';
    }
}

//TODO: perrasyti
// Monitorius, skirtas veiksmų sinchronizacijai
class Monitorius {
    // Boolean masyvas, kuris pasako ar rasytojai vis dar kazka raso
    private boolean[] dirbantysRasytojai;
    // Bendrame masyve esanciu elementu kiekis
    private int strukturuKiekis;
    // Bendras masyvas, į kurį rasytojai talpina duomenis, o skaitytojai iš jo ima
    private rikiavimoStruktura[] rikiavimoStrukturos;

    /**
     * Monitoriaus konstruktorius
     *
     * @param n - rasytoju-procesu skaicius
     * @param m - skaitytoju-procesu skaicius
     */
    public Monitorius(int n, int m) {
        rikiavimoStrukturos = new rikiavimoStruktura[20];
        dirbantysRasytojai = new boolean[n];
        for (boolean g : dirbantysRasytojai) g = true;
        strukturuKiekis = 0;
    }

    // Grąžina bendrą masyvą
    public rikiavimoStruktura[] imtiStrukturas() {
        return rikiavimoStrukturos;
    }

    // Grąžina šiuo metu bendrame masyve esančių objektų kiekį
    public int imtiKieki() {
        return strukturuKiekis;
    }

    /**
     * Funkcija randa indeksa, kuriame ideti
     *
     * @param laukas - laukas pagal kuri ieskos vietos
     * @return indeksas masyve
     */
    private int rastiIterpimoIndeksa(int laukas) {
        // Jei masyvas tuscias, indeksas yra 0 (pirma vieta)
        if (strukturuKiekis == 0) {
            return 0;
        }
        for (int i = 0; i < strukturuKiekis; i++) {
            if (laukas <= rikiavimoStrukturos[i].getRikiavimoLaukas()) {
                return i;
            }
        }
        // Nebuvo rastas didesnis elementas, tai jis dedamas i gala
        return strukturuKiekis;
    }

    //    Sinchronizuotas metodas, reikalingas elementui įterpti į bendrą masyvą
    //    Duomenys talpinami, kai bendrame masyve yra vietos arba jau toks elementas
    //    yra ir padidinamas tik jo kiekis - kitu atveju metodas laukia, kad bus
    //    galima patalpinti objektą Automobilis
    public synchronized void iterptiAutomobili(Automobilis automobilis) {
        // Pasiimama per funkciją grąžinta įterpimo vieta
        int laukas = automobilis.getMetai();
        int vieta = rastiIterpimoIndeksa(laukas);

        // jei nera nei vieno lauko arba jeigu laukas bus idedamas gale
        // sukuriamas naujas laukas ir priskiriama kiekis 1
        if (vieta == strukturuKiekis) {
            rikiavimoStrukturos[vieta] = new rikiavimoStruktura(laukas, 1);
            strukturuKiekis++;
        }
        // jei yra laukas rastas, padidinamas kiekis vienetu
        else if (rikiavimoStrukturos[vieta].getRikiavimoLaukas() == laukas) {
            rikiavimoStrukturos[vieta].incKiekis();
        }
        // jei naujas laukas yra kazkur tarp esamu
        else {
            // prastumiam masyvo elementus, kad padaryti vietos naujam elementui
            for (int i = strukturuKiekis; i > vieta; i--) {
                rikiavimoStrukturos[i] = rikiavimoStrukturos[i - 1];
            }
            rikiavimoStrukturos[vieta] = new rikiavimoStruktura(laukas, 1);
            strukturuKiekis++;
        }
        notifyAll();
    }

    /**
     * sinchronizuotas metodas, skirtas salinti elementus is duomenu strukturos
     * Sinchronizacijos argumentacija
     * Metodas turi buti sinchronizuotas, nes trinti elementus gali noreti daugiau nei vienas skaitytojas
     * @param struktura - struktura, pagal kurios reiksmes bus trinami duomenys
     * @return ar buvo istrinti duomenys
     */
    public synchronized boolean trintiElementa(rikiavimoStruktura struktura) {
        // jei nera strukturu, bet yra bent vienas dirbantis rasytojas,
        // tai potencialiai gali buti idetos naujos strukturos ir del to laukiame
        while (strukturuKiekis == 0 && arYraDirbanciuRasytoju()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // jei nera elementu ir nera rasytoju, tai pasikeitimu nebus ir nera ko istrinti
        if (strukturuKiekis == 0 && !arYraDirbanciuRasytoju()) {
            notifyAll();
            return false;
        }
        boolean pasalinta = false;
        // Einama pro visą bendrą masyvą ir ieškoma reikiamos prekės (objekto)
        for (int i = 0; i < strukturuKiekis; i++) {
            // Skaitytojas randa norimą prekę (rastas tinkamas laukas)
            if (rikiavimoStrukturos[i].getRikiavimoLaukas() == struktura.getRikiavimoLaukas()) {
                int dabartinisKiekis = rikiavimoStrukturos[i].getKiekis();
                // jei reikiamas kiekis mazesnis uz esama
                if (struktura.getKiekis() < dabartinisKiekis) {
                    rikiavimoStrukturos[i].setKiekis(dabartinisKiekis - struktura.getKiekis());
                }
                // jei reikiamas kiekis didesnis arba lygus uz esama
                else {
                    // jei tai vienintele struktura arba jei tai struktura esanti gale masyvo tiesiog priskiriam null
                    if (i == 0 && strukturuKiekis == 1 || i == strukturuKiekis - 1) {
                        rikiavimoStrukturos[i] = null;
                        strukturuKiekis--;
                    }
                    // jei struktura ne vienintele ir vieta yra ne pabaigoje
                    else {
                        // paslenkamos strukturos i <<<<< puse
                        for (int j = i; j < strukturuKiekis - 1; j++)
                            rikiavimoStrukturos[j] = rikiavimoStrukturos[j + 1];
                        // saliname paskutine struktura, nes paskutine-1 ir paskutine yra vienodos
                        rikiavimoStrukturos[strukturuKiekis - 1] = null;
                        strukturuKiekis--;
                    }
                }
                pasalinta = true;
            }
        }
        // Pranešama kitoms laukiančioms gijoms, kad išeinama iš kritinės sekcijos
        notifyAll();
        return pasalinta;
    }

    /**
     * sinchronizuotas metodas, kuris nustato, kad rasytojas nr x nebedirbs
     * Sinchronizacijos argumentacija
     * Sinchronizuotas metodas del to, kad prie dirbantysRasytojai kintamojo gali noreti prieiti skaitytojas,
     * kuris nori patikrinti ar yra dirbanciu rasytoju
     */
    public synchronized void setRasytojasNebedirba(int rasytojas) {
        dirbantysRasytojai[rasytojas] = false;
        notifyAll();
    }

    /**
     * sinchronizuotas metodas, kuris tikrina ar yra dirbanciu rasytoju
     * Sinchronizacijos argumentacija
     * Sinchronizuotas metodas del to, kad prie dirbantysRasytojai kintamojo gali noreti prieiti rasytojas,
     * kuris nori pakeisti reiksme masyvo
     */
    public synchronized boolean arYraDirbanciuRasytoju() {
        for (int i = 0; i < dirbantysRasytojai.length; i++) {
            if (dirbantysRasytojai[i]) {
                notifyAll();
                return true;
            }
        }
        notifyAll();
        return false;
    }
}

/**
 * Rasytojo klase, kuri rasys duomenis
 */
class Rasytojas implements Runnable {

    private Automobilis[] automobiliai;
    private int rasytojoNr;

    //    Klasės konstruktorius
    public Rasytojas(int rasytojoNr, Automobilis[] autoDuomenys) {
        this.automobiliai = autoDuomenys;
        this.rasytojoNr = rasytojoNr;
    }


    /**
     * Perrasomas metodas run, kuris nurodo ka rasytojas darys
     */
    @Override
    public void run() {
        for (Automobilis automobilis : automobiliai) {
            IFF68_SivickasL_L2a.monitorius.iterptiAutomobili(automobilis);
        }
        // pasakoma monitoriui, kad rasytojas nebedirba
        IFF68_SivickasL_L2a.monitorius.setRasytojasNebedirba(rasytojoNr);
    }
}


/**
 * Skaitytojo klase, kuri "skaitys"(trins) duomenis
 */
class Skaitytojas implements Runnable {

    private rikiavimoStruktura[] strukturos;

    // Konstruktorius
    public Skaitytojas(rikiavimoStruktura[] duomenys) {
        this.strukturos = duomenys;
    }

    private boolean dataContainsNotNull() {
        for (rikiavimoStruktura struktura : strukturos) {
            if (struktura != null) return true;
        }
        return false;
    }

    /**
     * Perrasomas metodas run, kuris nusako, ka skaitytojas veiks
     */
    @Override
    public void run() {
        // Kol yra rasytoju "skaityti"(trinti) irasus
        while (!IFF68_SivickasL_L2a.monitorius.arYraDirbanciuRasytoju() && !dataContainsNotNull()) {
            for (int i = 0; i < strukturos.length; i++) {
                if (strukturos[i] != null &&
                        (strukturos[i].getKiekis() == 0 || IFF68_SivickasL_L2a.monitorius.trintiElementa(strukturos[i])))
                    strukturos[i] = null;
            }
        }
        // Kai jau visi rasytojai baige darba dar karta patikrinti ar viskas "perskaityta"
        for (rikiavimoStruktura d1 : strukturos)
            if (d1 != null)
                IFF68_SivickasL_L2a.monitorius.trintiElementa(d1);
    }
}


public class IFF68_SivickasL_L2a {

    // Rasytoju procesu skaicius
    private static final int RPS = 5;
    // Skaitytoju procesu skaicius
    private static final int SPS = 4;

    // Kuriamas public static monitorius, tam kad butu pasiekiamas is kitu klasiu
    public static Monitorius monitorius = new Monitorius(RPS, SPS);

    // Kuriami static ArrayListai, kad pasiektu nuskaitymo/spausdinimo metodai
    private static ArrayList<Automobilis[]> duomenysRasytojam = new ArrayList<>();
    private static ArrayList<rikiavimoStruktura[]> duomenysSkaitytojam = new ArrayList<>();

    // Kuriamas static PrintWriteris, kad butu pasiekiamas is spausdinimo metodu
    private static PrintWriter writer;


    /**
     * Duomenu skaitymo funkcija
     *
     * @param duomenuFailas - duomenu failo pavadinimas
     */
    private static void nuskaitytiDuomenis(String duomenuFailas) {
        try (Scanner scanner = new Scanner(new File(duomenuFailas))) {
            for (int i = 0; i < RPS; i++) {
                int duomenuKiekis = scanner.nextInt();
                Automobilis[] rasytojam = new Automobilis[duomenuKiekis];
                for (int p = 0; p < duomenuKiekis; p++) {
                    String modelis = scanner.next();
                    int pagaminimoMetai = scanner.nextInt();
                    double kuroSanaudos = scanner.nextDouble();
                    rasytojam[p] = new Automobilis(modelis, pagaminimoMetai, kuroSanaudos);
                }
                duomenysRasytojam.add(rasytojam);
            }

            scanner.nextLine();

            for (int i = 0; i < SPS; i++) {
                int duomenuKiekis = scanner.nextInt();
                rikiavimoStruktura[] skaitytojam = new rikiavimoStruktura[duomenuKiekis];
                for (int p = 0; p < duomenuKiekis; p++) {
                    int rikiavimoLaukas = scanner.nextInt();
                    int salinimoKiekis = scanner.nextInt();
                    skaitytojam[p] = new rikiavimoStruktura(rikiavimoLaukas, salinimoKiekis);
                }
                duomenysSkaitytojam.add(skaitytojam);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    /**
     * Duomenu spausdinimas faile
     */
    private static void duomenuSpausdinimas() {
        writer.println("**********************************************");
        writer.println("*             PRADINIAI DUOMENYS             *");
        writer.println("**********************************************\n");
        String skirtukas = "+--------------------------------------------+";
        // Spausdinam automobiliu duomenis
        writer.println(skirtukas);
        writer.println("|            Automobiliu duomenys            |");
        for (int i = 0; i < RPS; i++) {
            writer.println(skirtukas);
            writer.println(String.format("|            %3d duomenu rinkinys            |", i + 1));
            writer.println(skirtukas);
            writer.println(String.format("|%2s  | %-17s| %-8s| %-9s|", "#", "Modelis", "Metai", "Sanaudos"));
            writer.println(skirtukas);
            Automobilis[] automobiliai = duomenysRasytojam.get(i);
            for (int p = 0; p < automobiliai.length; p++) {
                Automobilis automobilis = automobiliai[p];
                writer.println(String.format("|%2d) | %-17s| %-8s| %-9s|", p + 1, automobilis.getModelis(), automobilis.getMetai(), automobilis.getSanaudos()));
            }
        }
        writer.println(skirtukas + "\n");

        skirtukas = "+---------------------------+";
        // Spausdinam rikiavimo strukturos duomenis
        writer.println(skirtukas);
        writer.println("|    Rikiavimo struktūros   |");
        for (int i = 0; i < SPS; i++) {
            writer.println(skirtukas);
            writer.println(String.format("|   %d rikiavimo struktura   |", i + 1));
            writer.println(skirtukas);
            writer.println(String.format("| %-3s| %-12s| %-7s|", "#", "Rik. laukas", "Kiekis"));
            writer.println(skirtukas);
            rikiavimoStruktura[] strukturos = duomenysSkaitytojam.get(i);
            for (int p = 0; p < strukturos.length; p++) {
                rikiavimoStruktura struktura = strukturos[p];
                writer.println(String.format("| %-3d| %-12s| %-7d|", p + 1, struktura.getRikiavimoLaukas(), struktura.getKiekis()));
            }
            writer.println(skirtukas + '\n');
        }
    }

    /**
     * Rezultatu spausdinimas faile
     */
    public static void rezultatuSpausdinimas() {
        writer.println("**********************************************");
        writer.println("*                 REZULTATAI                 *");
        writer.println("**********************************************\n");
        String skirtukas = "+---------------------------+";
        writer.println(skirtukas);
        writer.println("|  Duomenu strukt. masyvas  |");
        writer.println(skirtukas);
        writer.println(String.format("| %-3s| %-12s| %-7s|", "#", "Rik. laukas", "Kiekis"));
        writer.println(skirtukas);
        for (int i = 0; i < monitorius.imtiKieki(); i++) {
            rikiavimoStruktura struktura = monitorius.imtiStrukturas()[i];
            writer.println(String.format("| %-3d| %-12s| %-7d|", i + 1, struktura.getRikiavimoLaukas(), struktura.getKiekis()));
        }
        writer.println(skirtukas);
    }


    /**
     * Funkcija kuri kuria ir paleidzia skaitymo ir rasymo procesus
     */
    private static void pradetiProcesus() {

        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < RPS; i++) {
            threads.add(new Thread(new Rasytojas(i, duomenysRasytojam.get(i))));
        }
        for (int i = 0; i < SPS; i++) {
            threads.add(new Thread(new Skaitytojas(duomenysSkaitytojam.get(i))));
        }

        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Pagrindine funkcija
     *
     * @param args
     */
    public static void main(String[] args) {

        // Rezultatu failas
        final String rezultatuFailoPavadinimas = "IFF68_SivickasL_L2a_rez.txt";

        String[] duomenuVariantai = {
                "IFF68_SivickasL_L2_dat_1.txt",
                "IFF68_SivickasL_L2_dat_2.txt",
                "IFF68_SivickasL_L2_dat_3.txt"
        };

        /**
         * Variantas 0 - po salinimo duomenu struktura tuscia.
         * Variantas 1 - is duomenu strukturos nepasalinamas nei vienas elementas
         * Variantas 2 - is duomenu strukturos pasalinama dalis elementu.
         */
        int variantas = 1;

        nuskaitytiDuomenis(duomenuVariantai[variantas]);
        try {
            writer = new PrintWriter(rezultatuFailoPavadinimas);
        } catch (IOException e) {
            System.out.println(e);
            return;
        }
        duomenuSpausdinimas();
        pradetiProcesus();
        rezultatuSpausdinimas();
        writer.close();
    }
}











































