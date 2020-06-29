package ru.nsu.g.mustafin.factory.gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Properties;

public class MenuView extends JFrame {
    private JPanel content;
    private JPanel mainMenuPanel;
    private FactoryView factoryView;
    private JButton startButton;
    private int[] values;
    private String[] names;
    private JSpinner[] values_spinners;
    private Properties properties;
    private JCheckBox logbox;

    public MenuView() {
        super("Factory");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        content = new JPanel();
        values = new int[11];
        names = new String[12];
        values_spinners = new JSpinner[11];
        mainMenuPanel = new JPanel();
        startButton = new JButton("Start");
        logbox = new JCheckBox("Log factory");
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        properties = new Properties();

        this.initValues();
        if (!createConfig()) {
            this.assignValues();
        }
        this.initSpinners();
        this.initMainMenuPanel();
        this.initListeners();

        this.setSize(500, 500);
        content.add(mainMenuPanel);
        this.add(content);
        this.setVisible(true);
    }

    private void assignValues() {
        try (InputStream in = new FileInputStream("config.properties")) {
            properties.load(in);
            for (int i = 0; i < 11; i++) {
                values[i] = Integer.parseInt(properties.getProperty(capitalize(names[i])));
            }
            logbox.setSelected(Boolean.parseBoolean(properties.getProperty(capitalize(names[11]))));
        } catch (Throwable ignored) {
        }
    }

    public void initSpinners() {
        for (int i = 0; i < 4; i++) {
            values_spinners[i] = new JSpinner(new SpinnerNumberModel(values[i], 5, 5000, 5));
        }
        for (int i = 4; i < 6; i++) {
            values_spinners[i] = new JSpinner(new SpinnerNumberModel(values[i], 1, 16, 1));
        }
        for (int i = 6; i < 11; i++) {
            values_spinners[i] = new JSpinner(new SpinnerNumberModel(values[i], 100, 60000, 50));
        }
    }

    public void initValues() {
        names[0] = "accessoryStorageSize";
        names[1] = "bodyStorageSize";
        names[2] = "motorStorageSize";
        names[3] = "carStorageSize";
        names[4] = "workers";
        names[5] = "dealers";
        names[6] = "supplyAccessoryTime";
        names[7] = "supplyBodyTime";
        names[8] = "supplyMotorTime";
        names[9] = "workerTime";
        names[10] = "dealerTime";
        names[11] = "factoryLog";

        values[0] = values[1] = values[2] = values[3] = 100;
        values[4] = values[5] = 2;
        values[6] = values[7] = values[8] = values[9] = values[10] = 1000;

    }

    public void initMainMenuPanel() {
        mainMenuPanel.setLayout(new GridLayout(13, 1));
        JPanel[] panels = new JPanel[11];
        for (int i = 0; i < 11; i++) {
            panels[i] = new JPanel(new GridLayout(1, 2));
            panels[i].add(new JLabel(addSpaces(names[i])));
            panels[i].add(values_spinners[i]);
        }

        for (var panel : panels) {
            mainMenuPanel.add(panel);
        }

        mainMenuPanel.add(logbox);
        mainMenuPanel.add(startButton);
    }

    void initListeners() {
        startButton.addActionListener(e -> {
            this.saveProperties();
            this.assignValues();
            factoryView = new FactoryView(values, logbox.isSelected());
            this.dispose();
            factoryView.startFactory();
        });
    }

    public void saveProperties() {
        try (FileOutputStream out = new FileOutputStream("config.properties")) {
            for (int i = 0; i < 11; i++) {
                properties.setProperty(capitalize(names[i]), values_spinners[i].getValue().toString());
            }
            properties.setProperty(capitalize(names[11]), String.valueOf(logbox.isSelected()));
            properties.store(out, null);
        } catch (IOException ignored) {
        }
    }

    private static String addSpaces(String str){
        return Character.toUpperCase(str.charAt(0)) +
                str.substring(1).replaceAll("(?<!_)(?=[A-Z])", " ");
    }

    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private boolean createConfig() {
        File config = new File("config.properties");
        if (!config.exists()) {
            try (FileOutputStream out = new FileOutputStream(config)) {
                for (int i = 0; i < 11; i++) {
                    properties.setProperty(capitalize(names[i]), String.valueOf(values[i]));
                }
                properties.setProperty(capitalize(names[11]), "true");
                properties.store(out, null);
            } catch (IOException ignored) {
            }
            return true;
        } else {
            return false;
        }
    }
}

