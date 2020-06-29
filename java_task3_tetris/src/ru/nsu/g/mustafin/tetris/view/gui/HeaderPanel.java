package ru.nsu.g.mustafin.tetris.view.gui;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {
    private int width, height, scale;
    private int score;
    private int level;
    private int lines;
    private int max_scale;
    private Graphics2D g2d;

    public HeaderPanel(int width) {
        scale = 15;
        max_scale = 15;
        this.width = width;
        this.height = scale * 3 + 10;
        setPreferredSize(new Dimension(this.width, this.height));
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(width * 100, this.height);
    }

    public void rescale(int scale) {
        this.scale = Math.min(scale, max_scale);
    }

    @Override
    public Color getBackground() {
        return Color.black;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2d = (Graphics2D) g;
        paintHeader();
    }

    public void paintHeader(int score, int level, int lines) {
        this.score = score;
        this.level = level;
        this.lines = lines;
        repaint();
    }

    public void paintHeader() {
        g2d.setStroke(new BasicStroke(0.25f));
        g2d.setColor(Color.white);
        g2d.setFont(new Font("TimesRoman", Font.PLAIN, scale));
        g2d.drawString("Score: " + score, 5, scale);
        g2d.drawString("Level: " + level, 5, 2 * scale);
        g2d.drawString("Lines cleared: " + lines, 5, 3 * scale);
    }

}
