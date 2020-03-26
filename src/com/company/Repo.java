package com.company;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Repo {

    /**
     * Citeste din fisier
     * @param filename numele fisierului
     * @param matrix    matricea de adiacenta a grafului ponderata
     * @param sourceDest 2 valori sursa si destinatia
     * @throws IOException
     */
    public void readFile(String filename, List<List<Integer>> matrix, List<Integer> sourceDest) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader((this.getClass().getResource("").getPath() +"..\\..\\"+ filename).replace("%20"," ")));
        String s = reader.readLine();
        int n = Integer.parseInt(s);
        for (int i = 0; i < n; i++) {
            s = reader.readLine();
            String[] numbers = s.split(",");
            matrix.add(new ArrayList<>());
            for (String number : numbers) {
                matrix.get(i).add(Integer.parseInt(number));
            }
        }
        s = reader.readLine();
        sourceDest.add(Integer.parseInt(s));
        s = reader.readLine();
        sourceDest.add(Integer.parseInt(s));
        reader.close();
    }

    /**
     * Scrierea datelor in fisier
     * @param filename  numele fisierului
     * @param pathAll   nodurile prin care trece (dupa ordinea 'greedy')
     * @param sumAll    suma parcurgerii distantei prin fiecare nod
     * @throws IOException
     */
    public void writeFile(String filename, List<Integer> pathAll, long sumAll) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter((this.getClass().getResource("").getPath() +"..\\..\\"+ filename).replace("%20"," ")));

        writer.write(pathAll.size()+"");
        writer.newLine();
        for (int i = 0; i < pathAll.size()-1; i++) {
            writer.write((pathAll.get(i)+1)+",");
        }
        writer.write((pathAll.get(pathAll.size()-1)+1)+"");
        writer.newLine();
        writer.write(sumAll+"");
        writer.flush();
        writer.close();
    }
}
