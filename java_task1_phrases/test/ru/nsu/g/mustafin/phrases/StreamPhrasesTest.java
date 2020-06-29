package ru.nsu.g.mustafin.phrases;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.*;

public class StreamPhrasesTest {

    @Test
    public void read() {
        PhrasesTest.readTest(new StreamPhrases(2,2));
    }

    @Test
    public void print() {
        PhrasesTest.printTest(new StreamPhrases(2,2));
    }
}