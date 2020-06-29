package ru.nsu.g.mustafin.phrases;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public interface IPhrases {

    static void process(String[] args, IPhrases phrases) {
        Parser p = new Parser();
        Args arguments;

        try {
            arguments = p.parse(args);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        if (arguments.n < 1) {
            return;
        }

        phrases.setN(arguments.n);
        phrases.setM(arguments.m);

        if (arguments.filename.equals("-")) {
            phrases.read(new Scanner(System.in));
        } else {
            try (FileInputStream fis = new FileInputStream(arguments.filename)) {
                Scanner sc = new Scanner(fis);
                phrases.read(sc);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                return;
            }
        }

        phrases.print(System.out);
    }

    void setN(int n);

    void setM(int m);

    Pair[] getPhrases();

    void read(Scanner scanner);

    void print(PrintStream out);
}

class Pair implements Comparable<Pair> {
    int freq;
    String phrase;

    Pair(int freq, String phrase) {
        this.freq = freq;
        this.phrase = phrase;
    }

    @Override
    public int compareTo(Pair o) {
        return o.freq - freq;
    }
}
