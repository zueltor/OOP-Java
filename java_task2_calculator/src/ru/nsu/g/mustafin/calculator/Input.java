package ru.nsu.g.mustafin.calculator;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Input implements Iterator<String>, Closeable {
    public Scanner scanner;
    ArrayList<String> commands;
    int current_command;

    Input(Scanner scanner){
        this.scanner=scanner;
    }

    Input(String filename) {
        current_command = 0;
        commands=new ArrayList<>();
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
    public boolean hasNext() {
        return scanner.hasNext();
    }

    @Override
    public String next() {
        commands.add(scanner.next());
        return commands.get(current_command++);
    }

    @Override
    public void close() {
        scanner.close();
    }
}
