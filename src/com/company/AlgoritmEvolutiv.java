package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class AlgoritmEvolutiv {
    private Repo repo = new Repo();
    private List<List<Integer>> graph = new ArrayList<>();
    private List<Integer> sd = new ArrayList<>();

    private Random random = new Random();
    private List<Integer> rezPath = new ArrayList<>();
    private long rezSum;

    private long minimGlobal = 0;

    public AlgoritmEvolutiv() {
        try {
            repo.readFile("TSP/easy_01_tsp.txt", graph, sd);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Nu s-a putu face citirea din fisier");
        }
        for (int i = 0; i < graph.size(); i++) {
            long min = Long.MAX_VALUE;
            for (int j = 0; j < graph.get(i).size(); j++) {
                if (min > graph.get(i).get(j)) {
                    min = graph.get(i).get(j);
                }
            }
            minimGlobal += min;
        }
    }

     public void run(int popSize, int genStop) {

        var populatie = initPopulation(popSize);  // genereaza populatia
        boolean stop = false;
        int gen = 0;

        List<Integer> best = null;
        long distance = Long.MAX_VALUE;
        while (!stop && gen < genStop) {
            List<List<Integer>> newpop = new ArrayList<>();
            for (int i = 0; i < popSize; i++) {
                //alege 2 solutii
                var M = selectie(populatie);
                var F = selectie(populatie);

                List<Integer> born = incrutisare(M, F);
                born = mutatie(born);
                newpop.add(born);
            }

            for (List<Integer> candidat : newpop) {
                var val = fitness(candidat);

                if (val == minimGlobal) {    //cazul ideal
                    stop = true;
                    best = candidat;
                    distance = val;
                    break;
                }
                if (distance > val) {   //cazul cel mai bun
                    best = candidat;
                    distance = val;
                }
            }
            gen++;
        }

        rezPath=best;
        rezSum=distance;
    }

    // initializam populatia cu drumuri corecte
    private List<List<Integer>> initPopulation(int popSize) {
        List<List<Integer>> populatie = new ArrayList<>();
        List<Integer> valori;

        for (int i = 0; i < popSize; i++) {
            List<Integer> persoana = new ArrayList<>();

            valori = drumuri();
            for (int j = 0; j < graph.size(); j++) {
                int poz = random.nextInt(valori.size());
                persoana.add(valori.get(poz));
                valori.remove(poz);
            }
            populatie.add(persoana);
        }

        return populatie;
    }

    // valorile din care alegem pentru a gasi un drum valid
    private List<Integer> drumuri() {
        List<Integer> val = new ArrayList<>();
        for (int i = 0; i < graph.size(); i++) {
            val.add(i);
        }
        return val;
    }

    private List<Integer> selectie(List<List<Integer>> populatie) {
        int aleator = random.nextInt(populatie.size());
        return populatie.get(aleator);
    }

    //incrutisam cei doi parinti asfel incat conditiile de unicitate si start stop sa ramana valide
    private List<Integer> incrutisare(List<Integer> m, List<Integer> f) {
        List<Integer> born = new ArrayList<>();

        List<Boolean> exista = new ArrayList<>();
        for (int i = 0; i < m.size(); i++) {
            exista.add(false);
        }
        for (int i = 0; i < m.size(); i++) {
            if (i % 2 == 0) {
                conditie(m.get(i), f.get(i), born, exista);
            } else {
                conditie(f.get(i), m.get(i), born, exista);
            }
        }

        return born;
    }

    //conditia de unicitate
    private void conditie(Integer m, Integer f, List<Integer> born, List<Boolean> exista) {
        if (!exista.get(m)) {
            born.add(m);
            exista.set(m, true);
        } else {
            if(!exista.get(f)){
                born.add(f);
                exista.set(f, true);
            }else{
                for (Integer i = 0; i < exista.size(); i++) {
                    if(!exista.get(i)){
                        born.add(i);
                        exista.set(i, true);
                        break;
                    }
                }
            }

        }
    }

    //mutam copilul asfel incat conditiile de unicitate si start stop sa ramana valide
    private List<Integer> mutatie(List<Integer> born) {
        int poz1 = random.nextInt(born.size());
        int poz2 = random.nextInt(born.size());

        int aux = born.get(poz1);
        born.set(poz1, born.get(poz2));
        born.set(poz2, aux);
        return born;
    }

    private Long fitness(List<Integer> candidat) {
        long sum = 0;
        for (int i = 1; i < candidat.size(); i++) {
            sum += graph.get(candidat.get(i - 1)).get(candidat.get(i));
        }
        sum += graph.get(candidat.get(candidat.size()-1)).get(candidat.get(0));
        return sum;
    }

    public void write() throws IOException {
        repo.writeFile("rezultat/out.txt", rezPath, rezSum);
    }
}
