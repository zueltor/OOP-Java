package ru.nsu.g.mustafin.tetris.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScoreBoard {
    private static String fileName = "scores.txt";

    public static void saveScore(String new_name, int new_value) {
        if (new_name.isEmpty()) {
            new_name = "NoName";
        }
        List<Entry> entry_list = getScoreList();

        entry_list.add(new Entry(new_name, new_value));

        entry_list.sort((a, b) -> b.getValue() - a.getValue());

        try (PrintStream ps = new PrintStream(fileName)) {
            for (var entry : entry_list) {
                ps.println(entry.toString());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static List<Entry> getScoreList() {

        List<Entry> entry_list = new ArrayList<>();
        int count = 0;
        String[] line;
        try (FileInputStream fis = new FileInputStream(fileName)) {
            Scanner sc = new Scanner(fis);
            while (sc.hasNextLine()) {
                line = sc.nextLine().split(":");
                entry_list.add(new Entry(line[0], Integer.parseInt(line[1])));
                count++;
                if (count > 10) {
                    break;
                }
            }
        } catch (IOException ignored) {
        }
        return entry_list;
    }

}

