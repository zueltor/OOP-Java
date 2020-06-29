package ru.nsu.g.mustafin.phrases;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.*;

public class PhrasesTest {

    @Test
    public void read() {
        readTest(new Phrases(2, 2));
    }

    @Test
    public void print() {
        printTest(new Phrases(2, 2));
    }

    public static void readTest(IPhrases p) {
        String input = "a a a a b b b b b";
        Scanner scanner = new Scanner(input);
        p.read(scanner);
        Pair[] pairs = new Pair[]{new Pair(4, "b b"), new Pair(3, "a a"), new Pair(1, "a b")};

        assertPhrases(pairs, p.getPhrases());
    }

    static void assertPhrases(Pair[] a, Pair[] b) {

        assertEquals(a.length, b.length);

        for (int i = 0; i < a.length; i++) {
            assertEquals(a[i].freq, b[i].freq);

            assertEquals(a[i].phrase, b[i].phrase);
        }
    }

    public static void printTest(IPhrases p) {
        String input = "a a a a b b b b b";
        Scanner scanner = new Scanner(input);
        p.read(scanner);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        p.print(printStream);

        assertEquals("b b (4)\na a (3)\n", byteArrayOutputStream.toString());

        byteArrayOutputStream.reset();
        scanner = new Scanner(input);
        p.setM(4);
        p.read(scanner);
        p.print(printStream);

        assertEquals("b b (4)\n", byteArrayOutputStream.toString());
    }
}