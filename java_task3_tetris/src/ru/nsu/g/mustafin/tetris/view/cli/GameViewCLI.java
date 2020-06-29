package ru.nsu.g.mustafin.tetris.view.cli;

import ru.nsu.g.mustafin.tetris.model.Model;
import ru.nsu.g.mustafin.tetris.model.ScoreBoard;
import ru.nsu.g.mustafin.tetris.model.Updater;
import ru.nsu.g.mustafin.tetris.utils.Direction;
import ru.nsu.g.mustafin.tetris.utils.GameState;

import java.awt.*;
import java.util.Scanner;

public class GameViewCLI implements Updater {
    Scanner scanner;
    private String name;
    private Model model;
    private int rows, columns;
    private GameState gameState;
    private int[][] board;

    public GameViewCLI(String name, Model model) {
        scanner = new Scanner(System.in);
        this.name = name;
        this.model = model;
        model.setView(this);
        Point size = model.getFieldSize();
        rows = size.x;
        columns = size.y;
        board = new int[][]{};
    }

    public void run() {
        model.start();
        gameState = model.getGameState();

        String in;
        while (gameState == GameState.PLAYING && scanner.hasNext()) {
            in = scanner.next();
            switch (in) {
                case "a":
                    model.move(Direction.LEFT);
                    break;
                case "d":
                    model.move(Direction.RIGHT);
                    break;
                case "s":
                    model.move(Direction.DOWN);
                    break;
                case "w":
                    model.move(Direction.UP);
                    break;
                case "e":
                    model.stop();
                    gameState = GameState.GAMEOVER;
                    return;
            }
        }
        model.stop();

    }

    void checkGameOver() {
        gameState = model.getGameState();
        if (gameState == GameState.GAMEOVER) {
            ScoreBoard.saveScore(name, model.getScore());
        }
    }

    public void printBoundary() {
        for (int i = 0; i < columns + 2; i++) {
            System.out.print("#");
        }
        System.out.println();
    }

    @Override
    public void update() {
        checkGameOver();
        System.out.flush();
        board = model.getBoard();
        printBoundary();

        for (int j = 0; j < rows; j++) {
            System.out.print("#");
            for (int i = 0; i < columns; i++) {
                System.out.print((board[i][j] == 0) ? " " : "O");
            }
            System.out.println("#");
        }
        printBoundary();
    }
}
