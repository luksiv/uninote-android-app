/*
Variantas - LD1a
Autorius - Lukas Sivickas
 */
/*
Atsakymai i klausimus:
1. Tokia, kokia uzrasyti.
2. Atsitiktine.
3. Atsitiktini skaiciu.
4. Tokia, kokia surasyti duomenu masyve.
 */

/*
    SVARBU: Programos veikimui reikalinga JSON.simple biblioteka.
    Ja atsisiusti galima: https://code.google.com/archive/p/json-simple/downloads
 */
package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

/*
 * Klase skirta darbams su gijomis
 */
class MyThread extends Thread {

    private String threadName; // gijos pavadinimas
    private ArrayList<Data> data; // duomenys su kuriais gija dirbs
    private ArrayList<String> outputData; // bendras masyvas i kuri gija rasys rezultatus

    // Konstruktorius
    MyThread(String name, ArrayList<Data> data, ArrayList<String> array) {
        super(name);
        this.threadName = name;
        this.data = data;
        this.outputData = array;
    }

    // Overridintas metodas run, kuriame nusakomas gijos darbas
    @Override
    public void run() {
        for (int i = 0; i < data.size(); i++) {
            String output = String.format("%s %d %s %d %f",
                    threadName,
                    i + 1,
                    data.get(i).varpav,
                    data.get(i).numeris,
                    data.get(i).bestlap);
            outputData.add(output);
        }
    }
}

/*
 * Klase skirta saugoti duomenu strukturos irasus
 */
class Data {
    public String varpav; // vardo ir pavardes pirmu 3 raidziu kombinacija
    public Integer numeris; // vairuotojo numeris
    public Double bestlap; // geriausias lapo laikas

    // Konstruktorius
    public Data(String inVarpav, Integer inNumeris, Double inBestlap) {
        varpav = inVarpav;
        numeris = inNumeris;
        bestlap = inBestlap;
    }

    // Overridintas toString metodas isvedimui
    @Override
    public String toString() {
        return String.format("%s %d %f", varpav, numeris, bestlap);
    }
}


public class IFF68_SivickasL_L1a {

    // Duomenu vieta
    final static String dataLocation = System.getProperty("user.dir") + "\\src\\com\\company\\IFF68_SivickasL_L1a_dat.json";
    // Isvedimo vieta
    final static String outputLocation = "IFF68_SivickasL_L1a_rez.txt";

    /**
     * Metodas skirtas is json failo nuskaityti duomenis, sudeti i ArrayList ir grazinti
     *
     * @param key grupes pavadinimas (pvz.: "jauniai", "u21", "suauge")
     * @return ArrayList<Data> masyva su nuskaitytais irasais.
     */
    public static ArrayList<Data> readFromJsonFile(String key) {
        JSONParser parser = new JSONParser();
        ArrayList<Data> results = new ArrayList<>();
        try {

            Object obj = parser.parse(new FileReader(dataLocation));

            JSONObject jsonObject = (JSONObject) obj;
            JSONArray grupes = (JSONArray) jsonObject.get("grupes");
            JSONObject grupes2 = (JSONObject) grupes.get(0);
            JSONArray values = (JSONArray) grupes2.get(key);
            for (Object value : values) {
                JSONObject entry = (JSONObject) value;
                String varpav = (String) entry.get("varpav");
                Integer num = Integer.parseInt(entry.get("numeris").toString());
                Double bestLap = Double.parseDouble(entry.get("bestlap").toString());
                results.add(new Data(varpav, num, bestLap));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return results;
    }

    /**
     * Metodas skirtas isvesti duomenis is ArrayList<Data> objekto i txt faila
     *
     * @param name grupes pavadinimas
     * @param data duomenys pateikti ArrayList<Data> objekte
     */
    public static void writeDataToFile(String name, ArrayList<Data> data) {
        try {
            File file = new File(outputLocation);

            // true = append file
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);

            String header = String.format("******* %s *******\n", name);
            header += String.format("%-3s %-7s %-7s %-5s","#","VarPav", "Numeris", "Geriausias_Laikas");
            bw.append(header + '\n');
            for (int i = 0; i < data.size(); i++) {
                String line = String.format("%-3d %-7s %-7d %-5f", i + 1,
                        data.get(i).varpav,
                        data.get(i).numeris,
                        data.get(i).bestlap);
                bw.append(line + '\n');
            }
            bw.append('\n');
            if (bw != null)
                bw.close();

            if (fw != null)
                fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodas skirtas isvesti bendro masyvo duomenis i tekstini faila
     *
     * @param data duomenys ArrayList<String> objekte
     */
    public static void writeCommonListToFile(ArrayList<String> data) {
        try {
            File file = new File(outputLocation);

            // true = append file
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);

            String header = String.format("----------------------------------------\n");
            header += String.format("%-3s %-10s %-12s %-8s %-7s %s", "#", "Gijos_Pav", "Vieta_Masyve", "Var_Pav", "Numeris", "Geriausias_Laikas");
            bw.append(header + '\n');
            for (int i = 0; i < data.size(); i++) {
                String[] duom = data.get(i).split(" ");
                String output = String.format("%-3s %-10s %-12s %-8s %-7s %s\n", i, duom[0], duom[1], duom[2], duom[3], duom[4]);
                bw.append(output);
            }
            bw.append('\n');
            if (bw != null)
                bw.close();

            if (fw != null)
                fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void prepareOutputFile(){
        File file = new File(outputLocation);
        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                file.delete();
                file.createNewFile();
            }
        } catch (IOException ex){
            System.out.println(ex);
        }
    }

    public static void main(String[] args) {
        // Getting the data from a json file and storing them into ArrayLists
        ArrayList<Data> vaikai = readFromJsonFile("vaikai");
        ArrayList<Data> jaunuciai = readFromJsonFile("jaunuciai");
        ArrayList<Data> jauniai = readFromJsonFile("jauniai");
        ArrayList<Data> u21 = readFromJsonFile("u21");
        ArrayList<Data> suauge = readFromJsonFile("suauge");

        prepareOutputFile();

        // Writing data from ArrayLists to txt file
        writeDataToFile("Vaikai", vaikai);
        writeDataToFile("Jaunuciai", jaunuciai);
        writeDataToFile("Jauniai", jauniai);
        writeDataToFile("U21", u21);
        writeDataToFile("Suauge", suauge);

        // Created a common ArrayList to write data into
        ArrayList<String> commonList = new ArrayList<>();

        // Creating threads and storing them in to an ArrayList
        ArrayList<MyThread> threads = new ArrayList<>();
        threads.add(new MyThread("thread1", vaikai, commonList));
        threads.add(new MyThread("thread2", jaunuciai, commonList));
        threads.add(new MyThread("thread3", jauniai, commonList));
        threads.add(new MyThread("thread4", u21, commonList));
        threads.add(new MyThread("thread5", suauge, commonList));

        // Starting the threads
        for (MyThread thread : threads) {
            thread.start();
        }

        // Waiting for all the threads to finish
        for (MyThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("All threads have finished...");
        // Writing the common list to a text file.
        writeCommonListToFile(commonList);


    }
}
