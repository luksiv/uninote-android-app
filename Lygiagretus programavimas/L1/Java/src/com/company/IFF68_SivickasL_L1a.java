/*
Variantas - LD1a
Autorius - Lukas Sivickas
 */

package com.company;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

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

    public static void readFromJson(String location) {
        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader(location));

            JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject.get("grupes"));
            System.out.println();
            JSONArray grupes = (JSONArray) jsonObject.get("grupes");
            JSONObject grupes2 = (JSONObject) grupes.get(0);
            JSONArray vaikuGrupe = (JSONArray) grupes2.get("vaikai");
            Iterator<JSONObject> iterator = vaikuGrupe.iterator();
            while(iterator.hasNext()){

            }

//            JSONObject grupes = (JSONObject) jsonObject.get("grupes");
//            System.out.println(grupes.get("jauniai"));
//            System.out.println();
//            System.out.println(grupes.get("suauge"));
//            System.out.println();

//            // loop array
//            JSONArray msg = (JSONArray) grupes.get("suauge");
//            Iterator<String> iterator = msg.iterator();
//            while (iterator.hasNext()) {
//                System.out.println(iterator.next());
//            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        String dataLocation = System.getProperty("user.dir")+ "/IFF68_SivickasL_L1a_dat.json";
        String dataLocation = "C:\\Users\\Lukas\\Desktop\\SemestroDarbai\\Lygiagretus programavimas\\L1\\Java\\src\\com\\company\\IFF68_SivickasL_L1a_dat.json";
        String outputLocation = "IFF68_SivickasL_L1a_rez.txt";

        readFromJson(dataLocation);

    }
}
