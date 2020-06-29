package ru.nsu.g.mustafin.tetris.view.gui;

import ru.nsu.g.mustafin.tetris.model.Model;
import ru.nsu.g.mustafin.tetris.model.ScoreBoard;
import ru.nsu.g.mustafin.tetris.model.Updater;
import ru.nsu.g.mustafin.tetris.utils.Direction;
import ru.nsu.g.mustafin.tetris.utils.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static java.awt.event.KeyEvent.*;
import static java.awt.event.KeyEvent.VK_RIGHT;

public class GameViewGUI extends JFrame implements Updater, KeyListener {

    private int width, height, scale;
    private GamePanel gamePanel;
    private HeaderPanel headerPanel;
    private Model model;
    private JPanel content;
    private GameState gameState;
    private String name;

    public GameViewGUI(String name, Model model) {
        super("Tetris");
        this.name = name;
        this.model = model;
        Point size = model.getFieldSize();
        this.scale = 15;
        this.width = size.y * scale;
        this.height = size.x * scale;
        setMinimumSize(new Dimension(180, height));

        model.setView(this);
        addKeyListener(this);
        setFocusable(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                new MenuViewGUI();
            }
        });

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                Dimension dim = gamePanel.getSize();
                double scale_x = (double) dim.width / width * scale;
                double scale_y = (double) dim.height / height * scale;
                gamePanel.rescale(scale_x, scale_y);
                headerPanel.rescale((int) scale_y);
            }
        });

        setFocusable(true);
        content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(scale, scale, scale, scale));
        gamePanel = new GamePanel(width, height, scale);
        headerPanel = new HeaderPanel(width);
        content.add(headerPanel);
        content.add(gamePanel);
        add(content);
        pack();
        setVisible(true);
    }

    public void run() {
        model.start();
    }

    @Override
    public void update() {
        checkGameOver();
        gamePanel.paintBoard(model.getBoard());
        headerPanel.paintHeader(model.getScore(), model.getLevel(), model.getLines());
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameState == GameState.PLAYING) {
            switch (e.getKeyCode()) {
                case VK_UP:
                    model.move(Direction.UP);
                    break;
                case VK_DOWN:
                    model.move(Direction.DOWN);
                    break;
                case VK_LEFT:
                    model.move(Direction.LEFT);
                    break;
                case VK_RIGHT:
                    model.move(Direction.RIGHT);
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    void checkGameOver() {
        gameState = model.getGameState();
        if (gameState == GameState.GAMEOVER) {
            model.stop();
            ScoreBoard.saveScore(name, model.getScore());
            dispose();
        }
    }
}
