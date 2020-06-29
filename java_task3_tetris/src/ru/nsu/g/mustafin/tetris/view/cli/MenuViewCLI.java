package ru.nsu.g.mustafin.tetris.view.cli;

import ru.nsu.g.mustafin.tetris.model.Model;
import ru.nsu.g.mustafin.tetris.model.ScoreBoard;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class MenuViewCLI {

    private Scanner scanner;
    private int rows, columns;
    private String name;
    private Properties properties;

    public MenuViewCLI() {
        name = "Vasya";
        rows = 30;
        columns = 10;
        properties = new Properties();

        createConfig();

        try (InputStream in = new FileInputStream("config.properties")) {
            properties.load(in);
            name = properties.getProperty("name");
            rows = Integer.parseInt(properties.getProperty("rows"));
            columns = Integer.parseInt(properties.getProperty("columns"));
        } catch (Throwable ignored) {
        }

        scanner = new Scanner(System.in);
        printMenu();
        printSplit();
        printControls();
        run();
    }

    public void printControls() {
        String controls = "Controls in game:\n" +
                "Figure:\n" +
                "\"a\" - move left\n" +
                "\"s\" - move down\n" +
                "\"d\" - move right\n" +
                "\"w\" - rotate\n" +
                "\"e\" - exit";
        System.out.println(controls);
    }

    public void printSplit() {
        System.out.println("----------------");
    }

    public void printMenu() {
        String commands = "\"name\" - set name\n" +
                "\"rows\" - set rows\n" +
                "\"columns\" - set columns\n" +
                "\"start\" - start a new game\n" +
                "\"about\" - view about\n" +
                "\"scoreboard\" - view scoreboard\n" +
                "\"exit\" - exit";

        String values = String.format("Current name %s\nCurrent rows %d\nCurrent columns %d\n", name, rows, columns);
        System.out.print(values);
        printSplit();
        System.out.println(commands);
    }

    public void setName() {
        System.out.println("Type the name you want");
        String new_name;
        if (scanner.hasNextLine()) {
            new_name = scanner.nextLine();
            if (new_name.isEmpty()) {
                System.out.println("Name cannot be empty");
                printSplit();
                return;
            }
            name = new_name;
            saveProperties();
        }
    }

    public void setRows() {
        System.out.println("Type the number of rows you want between 4 and 120");
        int value = 0;
        if (scanner.hasNextLine()) {
            try {
                value = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Incorrect number format");
                printSplit();
                return;
            }
        }

        if (value < 4 || value > 100) {
            System.out.println("Number is out of range");
            return;
        }
        rows = value;
        saveProperties();
    }

    public void setColumns() {
        System.out.println("Type the number of columns you want between 4 and 40");
        int value = 0;
        if (scanner.hasNextLine()) {
            try {
                value = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ex) {
                System.out.println("Incorrect number format");
                printSplit();
                return;
            }
        }

        if (value < 4 || value > 40) {
            System.out.println("Number is out of range");
            return;
        }
        columns = value;
        saveProperties();
    }

    public void run() {
        String in;

        while (scanner.hasNextLine()) {
            in = scanner.nextLine();

            switch (in) {
                case "start":
                    startGame();
                    break;
                case "about":
                    printAbout();
                    break;
                case "scoreboard":
                    printScoreboard();
                    break;
                case "exit":
                    return;
                case "name":
                    setName();
                    printMenu();
                    break;
                case "rows":
                    setRows();
                    printMenu();
                    break;
                case "columns":
                    setColumns();
                    printMenu();
                    break;
                default:
                    System.out.println("Bad input");
                    printMenu();
            }
        }
    }

    public void printAbout() {
        String text = "Tetris by Mustafin Damir\nGroup 18201";
        System.out.println(text);
    }

    public void printScoreboard() {
        var score_list = ScoreBoard.getScoreList();
        String string_entry;

        System.out.println("Name\tScore");
        for (var entry : score_list) {
            string_entry = entry.getName() + "\t" + entry.getValue();
            System.out.println(string_entry);
        }

    }

    public void startGame() {
        Model model = new Model(rows, columns);
        var gameView = new GameViewCLI(name, model);
        gameView.run();

        printMenu();
    }

    private void createConfig() {
        File config = new File("config.properties");
        if (!config.exists()) {
            try (FileOutputStream out = new FileOutputStream(config)) {
                properties.setProperty("rows", "30");
                properties.setProperty("columns", "10");
                properties.setProperty("name", "Vasya");
                properties.store(out, null);
            } catch (IOException ignored) {
            }
        }
    }

    public void saveProperties() {
        try (FileOutputStream out = new FileOutputStream("config.properties")) {
            properties.setProperty("rows", String.valueOf(rows));
            properties.setProperty("columns", String.valueOf(columns));
            properties.setProperty("name", name);
            properties.store(out, null);
        } catch (IOException ignored) {
        }
    }


}
