package ru.nsu.g.mustafin.tetris.view.gui;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private int default_width, default_height, scale;
    private double scale_x, scale_y;
    private int[][] occupiedCells;
    private Graphics2D g2d;

    public static Color getColor(int c) {
        switch (c) {
            case 0:
                return Color.GRAY;
            case 1:
                return Color.GREEN;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.ORANGE;
            case 4:
                return Color.CYAN;
            case 5:
                return Color.PINK;
            case 6:
                return Color.RED;
            default:
                return Color.YELLOW;
        }
    }

    public void rescale(double scale_x, double scale_y) {
        this.scale_x = scale_x;
        this.scale_y = scale_y;
    }

    public GamePanel(int width, int height, int scale) {
        occupiedCells = new int[][]{};
        this.scale = scale;
        scale_x = scale;
        scale_y = scale;
        this.default_width = width;
        this.default_height = height;
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public Color getBackground() {
        return Color.gray;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        paintBoard();
    }

    public void paintBoard() {
        g2d.setStroke(new BasicStroke(0.25f));
        g2d.setColor(Color.BLACK);
        int width = (int) (default_width * scale_x / scale);
        int height = (int) (default_height * scale_y / scale);
        g2d.fillRect(0, 0, width, height);
        for (int i = 0; i < occupiedCells.length; i++) {
            for (int j = 0; j < occupiedCells[i].length; j++) {
                g2d.setColor(getColor(occupiedCells[i][j]));
                g2d.fillRect((int) (i * scale_x), (int) (j * scale_y), (int) scale_x - 2, (int) scale_y - 2);
            }
        }
    }


    public void paintBoard(int[][] occ) {
        occupiedCells = occ;
        repaint();
    }
}
