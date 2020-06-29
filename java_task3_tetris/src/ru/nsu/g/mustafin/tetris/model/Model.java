package ru.nsu.g.mustafin.tetris.model;

import ru.nsu.g.mustafin.tetris.utils.Direction;
import ru.nsu.g.mustafin.tetris.utils.GameState;

import javax.swing.*;
import java.awt.*;

public class Model {

    public static final int[] NextLevelThreshold = {1, 3, 7, 13, 21, 31, 43};
    public static final int[] ScoreList = {50, 150, 350, 1000, 2000};
    public static final int[] DelayList = {400, 345, 290, 235, 180, 125, 70, 30};
    private static final int max_level = NextLevelThreshold.length;
    private int rows, columns;
    private int score, lines, level;
    private FallingFigure fallingFigure;
    private int[][] board;
    private Updater view;
    private static Timer timer;
    private GameState gameState;

    public Model(int rows, int columns) {
        score = 0;
        level = 0;
        lines = 0;
        this.rows = rows;
        this.columns = columns;
        board = new int[columns][rows];
        fallingFigure = new FallingFigure(columns);
        timer = new Timer(getDelay(), e -> fall());
        timer.setInitialDelay(0);
        setView(()->{});
        gameState = GameState.PLAYING;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setView(Updater view) {
        this.view = view;
    }

    public void stop() {
        timer.stop();
    }

    public Point getFieldSize() {
        return new Point(rows, columns);
    }

    public void start() {
        timer.start();
    }

    public int getScore() {
        return score;
    }

    public void fall() {

        if (gameState == GameState.GAMEOVER) {
            return;
        }

        if (fallingFigure.isNotValid()) {
            fallingFigure.setNewRandomFigure();
            if (fallingFigure.isCollision(board)) {
                gameState = GameState.GAMEOVER;
                view.update();
                return;
            }
        }

        fallingFigure.move(0, 1);

        if (fallingFigure.isCollision(board)) {
            fallingFigure.move(0, -1);
            fallingFigure.addToBoard(board);
        }

        checkLines();
        view.update();
    }

    public void move(Direction direction) {
        if (gameState == GameState.GAMEOVER) {
            return;
        }

        if (fallingFigure.isNotValid()) {
            fallingFigure.setNewRandomFigure();
            if (fallingFigure.isCollision(board)) {
                gameState = GameState.GAMEOVER;
                view.update();
                return;
            }
        }

        int dx = 0, dy = 0;

        switch (direction) {
            case DOWN:
                dy = 1;
                break;
            case UP:
                fallingFigure.rotate();
                break;
            case LEFT:
                dx = -1;
                break;
            case RIGHT:
                dx = 1;
                break;
        }

        fallingFigure.move(dx, dy);

        if (fallingFigure.isCollision(board)) {
            if (direction == Direction.UP) {
                fallingFigure.rotateBack();
            } else {
                fallingFigure.move(-dx, -dy);
                if (dy > 0) {
                    fallingFigure.addToBoard(board);
                    checkLines();
                }
            }
        } else if (dy > 0) {
            score += 2 * (level + 1);
        }
        view.update();
    }

    public int[][] getBoard() {
        var ret_board = new int[columns][rows];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                ret_board[i][j] = board[i][j];
            }
        }
        for (var point : fallingFigure.getPoints()) {
            ret_board[point.x][point.y] = fallingFigure.getColor();
        }
        return ret_board;
    }

    public int getLines() {
        return lines;
    }

    public int getLevel() {
        return level;
    }

    private boolean isClearBoard() {
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                if (board[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void lowerLines(int n) {
        for (int j = n; j > 0; j--) {
            for (int i = 0; i < columns; i++) {
                board[i][j] = board[i][j - 1];
            }

        }
        for (int i = 0; i < columns; i++) {
            board[i][0] = 0;
        }
    }

    private void checkLines() {
        int lines_cleared = 0;
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < columns; i++) {
                if (board[i][j] == 0) {
                    break;
                }
                if (i == columns - 1) {
                    lowerLines(j);
                    lines_cleared++;
                }
            }
        }

        if (lines_cleared > 0) {
            if (isClearBoard()) {
                score += ScoreList[ScoreList.length - 1] * (level + 1);
            } else {
                score += ScoreList[lines_cleared - 1] * (level + 1);
            }
        }
        lines += lines_cleared;

        while (level < max_level && lines >= NextLevelThreshold[level]) {
            level++;
        }
        timer.setDelay(getDelay());
    }

    private int getDelay() {
        return DelayList[level];
    }
}