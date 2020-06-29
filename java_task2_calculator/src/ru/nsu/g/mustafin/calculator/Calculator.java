package ru.nsu.g.mustafin.calculator;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Calculator implements Runnable, Closeable {

    Scanner scanner;
    Interpreter interpreter;

    Calculator(String[] args) {
        String filename = (args.length > 0) ? args[0] : null;
        interpreter = new Interpreter();
        if (filename == null) {
            scanner = new Scanner(System.in);
            return;
        }
        try {
            FileReader fr = new FileReader(filename);
            scanner = new Scanner(fr);
        } catch (FileNotFoundException f) {
            System.out.println(f.getMessage());
        }
    }


    @Override
    public void run() {
        while (scanner.hasNext()){
            interpreter.run(scanner);
        }
    }

    @Override
    public void close() {
        scanner.close();
    }
}
