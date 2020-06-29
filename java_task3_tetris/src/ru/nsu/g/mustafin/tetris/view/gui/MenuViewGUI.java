package ru.nsu.g.mustafin.tetris.view.gui;

import ru.nsu.g.mustafin.tetris.model.Model;

import javax.swing.*;

public class MenuViewGUI extends JFrame {
    private JPanel content;
    private MainMenuPanel mainMenuPanel;
    private ScoresPanel scoresPanel;
    private AboutPanel aboutPanel;

    public MenuViewGUI() {
        super("Tetris");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainMenuPanel = new MainMenuPanel();
        scoresPanel = new ScoresPanel();
        aboutPanel = new AboutPanel();

        initListeners();

        setSize(250, 300);
        content.add(mainMenuPanel);
        add(content);
        setResizable(false);
        setVisible(true);
    }

    void initListeners() {
        mainMenuPanel.getStartButton().addActionListener(e -> {
            Integer rows = mainMenuPanel.getRows();
            Integer columns = mainMenuPanel.getColumns();
            String name = mainMenuPanel.getName();
            mainMenuPanel.saveProperties();
            Model model = new Model(rows, columns);
            var gameView= new GameViewGUI(name, model);
            gameView.run();

            this.dispose();
        });
        mainMenuPanel.getScoressButton().addActionListener(e -> scores());

        mainMenuPanel.getAboutButton().addActionListener(e -> about());

        scoresPanel.getMenuButton().addActionListener(e -> menu());

        aboutPanel.getMenuButton().addActionListener(e -> menu());
    }

    public void changeContent(JPanel new_content) {
        content.removeAll();
        content.add(new_content);
        content.validate();
        content.repaint();
    }

    public void about() {
        changeContent(aboutPanel);
    }

    public void scores() {
        scoresPanel.loadScores();
        changeContent(scoresPanel);
    }

    public void menu() {
        changeContent(mainMenuPanel);
    }


}
