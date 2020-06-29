package ru.nsu.g.mustafin.phrases;

import java.io.PrintStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class StreamPhrases implements IPhrases {
    private int n;
    private int m;
    private Pair[] phrase_frequency_pairs;

    StreamPhrases(int n, int m) {
        if (n < 0 || m < 0) {
            throw new NumberFormatException("Number must be positive");
        }
        this.n = n;
        this.m = m;
    }

    StreamPhrases() {
        n = m = 0;
    }

    @Override
    public void setN(int n) {
        if (m < 0) {
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
        List<String> phrases = new ArrayList<>();
        int count = 0;

        while (scanner.hasNext()) {
            sb_phrase.append(scanner.next());
            count++;
            if (count == n) {
                phrases.add(sb_phrase.toString());
                sb_phrase.append(" ");
                break;
            }
            sb_phrase.append(" ");
        }

        while (scanner.hasNext()) {
            sb_phrase.delete(0, sb_phrase.indexOf(" ") + 1);
            sb_phrase.append(scanner.next());
            phrases.add(sb_phrase.toString());
            sb_phrase.append(" ");
        }

        phrase_frequency_pairs = phrases
                .stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((x, y) -> (int) (y.getValue() - x.getValue()))
                .map(x -> new Pair(x.getValue().intValue(), x.getKey())).toArray(Pair[]::new);
    }

    @Override
    public void print(PrintStream out) {
        Arrays.stream(phrase_frequency_pairs)
                .filter(t -> t.freq >= m)
                .forEach(x -> out.printf("%s (%d)\n", x.phrase, x.freq));

    }
}
