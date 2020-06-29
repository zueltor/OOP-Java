package ru.nsu.g.mustafin.tetris.view.gui;

import ru.nsu.g.mustafin.tetris.model.ScoreBoard;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

public class ScoresPanel extends JPanel {

    private Properties prop;
    private JButton menuButton;
    private JPanel scoresList;

    public ScoresPanel() {
        prop = new Properties();
        menuButton = new JButton("Menu");
        setLayout(new BorderLayout());
        JPanel infoPanel = new JPanel(new GridLayout(1, 4));
        infoPanel.add(new JLabel("Name"));
        infoPanel.add(new JLabel("Score"));
        add(infoPanel, BorderLayout.NORTH);
        add(menuButton, BorderLayout.SOUTH);
        scoresList = new JPanel();
        scoresList.setLayout(new BoxLayout(scoresList, BoxLayout.Y_AXIS));
        add(new JScrollPane(scoresList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
    }

    public void loadScores() {
        scoresList.removeAll();
        var list = ScoreBoard.getScoreList();
        for (var entry : list) {
            JPanel panel = new JPanel(new GridLayout(1, 2));
            panel.add(new JLabel(entry.getName()));
            panel.add(new JLabel(String.valueOf(entry.getValue())));
            scoresList.add(panel);
        }
    }

    public JButton getMenuButton() {
        return menuButton;
    }
}