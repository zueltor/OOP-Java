package ru.nsu.g.mustafin.tetris.view.gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Properties;

public class MainMenuPanel extends JPanel {

    private JButton startButton;
    private JTextField nameField;
    private JButton aboutButton;
    private JButton scoreButton;
    private JSpinner rowsSpinner;
    private JSpinner columnsSpinner;
    private Properties properties;

    public MainMenuPanel() {
        String name = "name";
        int rows = 30;
        int columns = 10;
        this.properties = new Properties();

        createConfig();

        try (InputStream in = new FileInputStream("config.properties")) {
            properties.load(in);
            name = properties.getProperty("name");
            rows = Integer.parseInt(properties.getProperty("rows"));
            columns = Integer.parseInt(properties.getProperty("columns"));
        } catch (Throwable ignored) {
        }

        startButton = new JButton("Start");
        nameField = new JTextField(name);
        aboutButton = new JButton("About");
        scoreButton = new JButton("Scoreboard");
        rowsSpinner = new JSpinner(new SpinnerNumberModel(30, 4, 120, 1));
        rowsSpinner.setValue(rows);
        columnsSpinner = new JSpinner(new SpinnerNumberModel(10, 4, 40, 1));
        columnsSpinner.setValue(columns);
        this.setLayout(new GridLayout(10, 1));

        JPanel namePanel = new JPanel(new GridLayout(1, 2));
        namePanel.add(new JLabel("Name"));
        namePanel.add(nameField);

        JPanel rowsPanel = new JPanel(new GridLayout(1, 2));
        rowsPanel.add(new JLabel("Rows:"));
        rowsPanel.add(rowsSpinner);

        JPanel columnsPanel = new JPanel(new GridLayout(1, 2));
        columnsPanel.add(new JLabel("Columns:"));
        columnsPanel.add(columnsSpinner);


        add(namePanel);
        add(rowsPanel);
        add(columnsPanel);

        add(startButton);
        add(scoreButton);
        add(aboutButton);
    }

    public void saveProperties() {
        try (FileOutputStream out = new FileOutputStream("config.properties")) {
            properties.setProperty("rows", rowsSpinner.getValue().toString());
            properties.setProperty("columns", columnsSpinner.getValue().toString());
            properties.setProperty("name", nameField.getText());
            properties.store(out, null);
        } catch (IOException ignored) {
        }
    }

    private void createConfig(){
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

    public Integer getRows() {
        return (Integer) rowsSpinner.getValue();
    }

    public Integer getColumns() {
        return (Integer) columnsSpinner.getValue();
    }

    public String getName() {
        return nameField.getText();
    }

    public JButton getAboutButton() {
        return aboutButton;
    }

    public JButton getScoressButton() {
        return scoreButton;
    }

    public JButton getStartButton() {
        return startButton;
    }
}
