package com.company;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        AlgoritmEvolutiv ae = new AlgoritmEvolutiv();
        ae.run(1000, 100);

        ae.write();
    }
}
