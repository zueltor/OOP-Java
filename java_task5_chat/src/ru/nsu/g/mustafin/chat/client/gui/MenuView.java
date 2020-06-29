package ru.nsu.g.mustafin.chat.client.gui;

import ru.nsu.g.mustafin.chat.client.Client;
import ru.nsu.g.mustafin.chat.utils.exceptions.ServerErrorException;
import ru.nsu.g.mustafin.chat.utils.exceptions.UnknownResponseException;
import ru.nsu.g.mustafin.chat.utils.serializers.Serializer;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Properties;

public class MenuView extends JFrame {
    private JPanel content;
    private JPanel mainMenuPanel;
    private JPanel selectNamePanel;
    private Client client;
    private ChatView chat;
    private JButton startButton;
    private JTextField serverField;
    private JSpinner portSpinner;
    private Properties properties;
    private JButton selectButton;
    private JTextField nameField;
    private Serializer serializer;

    public MenuView(Serializer serializer) {
        super("Chat");
        this.serializer = serializer;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        properties = new Properties();
        this.createConfig();

        initMainMenuPanel();
        initSelectNamePanel();
        initListeners();

        setSize(250, 300);
        content.add(mainMenuPanel);
        add(content);
        setResizable(false);
        setVisible(true);
    }

    public void initMainMenuPanel() {
        mainMenuPanel = new JPanel();
        String server = "127.0.0.1";
        int port = 1337;

        try (InputStream in = new FileInputStream("config.properties")) {
            properties.load(in);
            server = properties.getProperty("server");
            port = Integer.parseInt(properties.getProperty("port"));
        } catch (Throwable ignored) {
        }

        startButton = new JButton("Start");
        portSpinner = new JSpinner(new SpinnerNumberModel(port, 1025, 65000, 1));
        serverField = new JTextField(server);
        mainMenuPanel.setLayout(new GridLayout(5, 1));

        JPanel portPanel = new JPanel(new GridLayout(1, 2));
        JPanel serverPanel = new JPanel(new GridLayout(1, 2));
        portPanel.add(new JLabel("Port"));
        portPanel.add(portSpinner);

        serverPanel.add(new JLabel("Server"));
        serverPanel.add(serverField);

        mainMenuPanel.add(serverPanel);
        mainMenuPanel.add(portPanel);
        mainMenuPanel.add(startButton);
    }

    public void initSelectNamePanel() {
        selectNamePanel = new JPanel();
        String name = "Damir";
        selectButton = new JButton("Start");
        Properties properties = new Properties();

        try (InputStream in = new FileInputStream("config.properties")) {
            properties.load(in);
            name = properties.getProperty("name");
        } catch (Throwable ignored) {
        }

        nameField = new JTextField(name);
        selectNamePanel.setLayout(new GridLayout(5, 1));
        JPanel namePanel = new JPanel(new GridLayout(1, 2));
        namePanel.add(new JLabel("Name"));

        namePanel.add(nameField);
        selectNamePanel.add(namePanel);
        selectNamePanel.add(new JPanel());
        selectNamePanel.add(selectButton);
    }

    void initListeners() {
        startButton.addActionListener(e -> {

            int port = (Integer) portSpinner.getValue();
            String server = serverField.getText();
            this.saveProperties();

            try {
                client = new Client(server, port, serializer);
                chat = new ChatView(client);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Failed to connect");
                return;
            }

            changeContent(selectNamePanel);

        });

        selectButton.addActionListener(e -> {
            try {
                client.login(nameField.getText());
            } catch (ServerErrorException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
                return;
            } catch (UnknownResponseException ex) {
                chat.dispose();
                JOptionPane.showMessageDialog(this, ex.getMessage());
                return;
            } catch (InterruptedException | IOException ex) {
                JOptionPane.showMessageDialog(this, "Server stopped working");
                chat.dispose();
                this.dispose();
                return;
            }

            chat.unhide();
            this.dispose();

            try {
                client.list();
            } catch (ServerErrorException | UnknownResponseException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } catch (InterruptedException | IOException ex) {
                JOptionPane.showMessageDialog(this, "Server stopped working");
                chat.dispose();
                this.dispose();
            }
        });
    }

    public void changeContent(JPanel new_content) {
        content.removeAll();
        content.add(new_content);
        content.validate();
    }

    public void saveProperties() {
        try (FileOutputStream out = new FileOutputStream("config.properties")) {
            properties.setProperty("port", portSpinner.getValue().toString());
            properties.setProperty("server", serverField.getText());
            properties.setProperty("name", nameField.getText());
            properties.store(out, null);
        } catch (IOException ignored) {
        }
    }

    private void createConfig() {
        File config = new File("config.properties");
        if (!config.exists()) {
            try (FileOutputStream out = new FileOutputStream(config)) {
                properties.setProperty("port", "1337");
                properties.setProperty("server", "127.0.0.1");
                properties.setProperty("name", "Damir");
                properties.store(out, null);
            } catch (IOException ignored) {
            }
        }
    }
}
