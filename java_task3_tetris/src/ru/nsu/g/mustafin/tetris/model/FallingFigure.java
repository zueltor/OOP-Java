package ru.nsu.g.mustafin.tetris.model;

import java.awt.*;

class FallingFigure {

    int index;
    int columns;
    int rotation;
    int center;
    Point position;

    public FallingFigure(int columns) {
        this.columns = columns;
        index = Figures.getRandomFigureIndex();
        rotation = Figures.getRandomFigureRotation();
        setCenter();
        position = new Point(center, 0);
    }

    void setCenter() {
        center = columns / 2;
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (var point : Figures.FiguresList[index][rotation]) {
            if (point.x < min) {
                min = point.x;
            }
            if (point.x > max) {
                max = point.x;
            }
        }
        center -= Math.round(((float) (max - min + 1)) / 2);
    }

    public boolean isNotValid() {
        return index < 0;
    }

    public void rotate() {
        rotation = (rotation + 1) % 4;
    }

    public void rotateBack() {
        rotation = (rotation + 3) % 4;
    }

    public Point[] getPoints() {
        if (isNotValid()) {
            return new Point[]{};
        }
        Point[] points = new Point[Figures.FiguresList[index][rotation].length];

        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(Figures.FiguresList[index][rotation][i]);
        }
        for (var point : points) {
            point.x += position.x;
            point.y += position.y;
        }
        return points;
    }

    public void move(int dx, int dy) {
        position.x += dx;
        position.y += dy;

    }

    public int getColor() {
        return index + 1;
    }

    public boolean isCollision(int[][] board) {
        Point[] points = getPoints();

        for (var point : points) {
            if (point.x >= board.length ||
                    point.x < 0 ||
                    point.y >= board[point.x].length ||
                    board[point.x][point.y] != 0) {
                return true;
            }
        }
        return false;
    }

    public void setNewRandomFigure() {
        index = Figures.getRandomFigureIndex();
        rotation = Figures.getRandomFigureRotation();
        setCenter();
        position.setLocation(center, 0);
    }

    public void addToBoard(int[][] board) {
        Point[] points = getPoints();
        for (var point : points) {
            board[point.x][point.y] = index + 1;
        }
        index = -1;
    }
}
