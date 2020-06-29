package ru.nsu.g.mustafin.phrases;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Phrases implements IPhrases {
    private int m;
    private int n;
    private Pair[] phrase_frequency_pairs;

    Phrases(int n, int m) {
        if (n < 0 || m < 0) {
            throw new NumberFormatException("Number must be positive");
        }
        this.n = n;
        this.m = m;
    }

    Phrases() {
        n = m = 0;
    }

    @Override
    public void setN(int n) {
        if (n < 0) {
            throw new NumberFormatException("Number must be positive");
        }
        this.n = n;
    }

    @Override
    public void setM(int m) {
        if (m < 0) {
            throw new NumberFormatException("Number must be positive");
        }
        this.m = m;
    }

    @Override
    public Pair[] getPhrases() {
        return phrase_frequency_pairs;
    }

    @Override
    public void read(Scanner scanner) {
        StringBuilder sb_phrase = new StringBuilder();
        Map<String, Integer> phrases = new HashMap<>();
        int count = 0;

        while (scanner.hasNext()) {
            sb_phrase.append(scanner.next());
            count++;
            if (count == n) {
                phrases.put(sb_phrase.toString(), 1);
                sb_phrase.append(" ");
                break;
            }
            sb_phrase.append(" ");
        }

        while (scanner.hasNext()) {
            sb_phrase.delete(0, sb_phrase.indexOf(" ") + 1);
            sb_phrase.append(scanner.next());
            if (phrases.containsKey(sb_phrase.toString())) {
                phrases.replace(sb_phrase.toString(), phrases.get(sb_phrase.toString()) + 1);
            } else {
                phrases.put(sb_phrase.toString(), 1);
            }
            sb_phrase.append(" ");
        }

        phrase_frequency_pairs = new Pair[phrases.size()];
        int i = 0;
        for (var phrase : phrases.entrySet()) {
            phrase_frequency_pairs[i] = new Pair(phrase.getValue(), phrase.getKey());
            i++;
        }

        Arrays.sort(phrase_frequency_pairs);
    }

    @Override
    public void print(PrintStream out) {
        for (var p : phrase_frequency_pairs) {
            if (p.freq >= m) {
                out.printf("%s (%d)\n", p.phrase, p.freq);
            }
        }
    }
}
