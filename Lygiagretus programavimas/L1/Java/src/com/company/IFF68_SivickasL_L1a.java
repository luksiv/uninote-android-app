/*
Variantas - LD1a
Autorius - Lukas Sivickas
 */

package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class MyThread extends Thread {

    private String threadName;
    private ArrayList<Data> data;
    private ArrayList<String> outputData;

    MyThread(String name, ArrayList<Data> data, ArrayList<String> array) {
        super(name);
        this.threadName = name;
        this.data = data;
        this.outputData = array;


    }

    @Override
    public void run() {
        for (int i = 0; i < data.size(); i++) {
            String output = String.format("%s %d %s %d %f",
                    threadName,
                    i+1,
                    data.get(i).varpav,
                    data.get(i).numeris,
                    data.get(i).bestlap);
            outputData.add(output);
        }
    }
}

class Data {
    public String varpav;
    public Integer numeris;
    public Double bestlap;

    public Data(String inVarpav, Integer inNumeris, Double inBestlap) {
        varpav = inVarpav;
        numeris = inNumeris;
        bestlap = inBestlap;
    }

    @Override
    public String toString() {
        return String.format("%s %d %f", varpav, numeris, bestlap);
    }
}

public class IFF68_SivickasL_L1a {

    final static String dataLocation = System.getProperty("user.dir") + "\\src\\com\\company\\IFF68_SivickasL_L1a_dat.json";
    final static String outputLocation = "IFF68_SivickasL_L1a_rez.txt";

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

    public static void writeDataToFile(String name, ArrayList<Data> data) {
        try {
            File file = new File(outputLocation);

            if (!file.exists()) {
                file.createNewFile();
            }

            // true = append file
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);

            String header = String.format("*** %s ***", name);
            header += "\nVarpav Numeris Ger. laikas";
            bw.append(header + '\n');
            for (int i = 0; i < data.size(); i++) {
                String line = String.format("%d) %s %d %f", i+1,
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

    public static void writeCommonListToFile(ArrayList<String> data) {
        try {
            File file = new File(outputLocation);

            // true = append file
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter(fw);

            String header = String.format("----------------------------------------");
            bw.append(header + '\n');
            for (int i = 0; i < data.size(); i++) {
                bw.append(data.get(i) + '\n');
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

    public static void main(String[] args) {
        // Getting the data from a json file and storing them into ArrayLists
        ArrayList<Data> vaikai = readFromJsonFile("vaikai");
        ArrayList<Data> jaunuciai = readFromJsonFile("jaunuciai");
        ArrayList<Data> jauniai = readFromJsonFile("jauniai");
        ArrayList<Data> u21 = readFromJsonFile("u21");
        ArrayList<Data> suauge = readFromJsonFile("suauge");

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
        writeCommonListToFile(commonList);


    }
}
