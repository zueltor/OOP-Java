package ru.nsu.g.mustafin.tetris.model;

import org.junit.Test;
import ru.nsu.g.mustafin.tetris.utils.Direction;
import ru.nsu.g.mustafin.tetris.utils.GameState;

import static org.junit.Assert.*;

public class ModelTest {

    @Test
    public void test() {
        int rows = 50;
        int columns = 20;
        Model model = new Model(rows, columns);
        var size = model.getFieldSize();
        assertEquals(size.x, rows);
        assertEquals(size.y, columns);
        model.fall();
        int[][] board = model.getBoard();

        assertEquals(numCellsOnBoard(board, rows, columns), 4);
        for (int i = 0; i < rows; i++) {
            model.move(Direction.DOWN);
        }
        board = model.getBoard();
        assertEquals(numCellsOnBoard(board, rows, columns), 8);
        int score = model.getScore();
        assertTrue(score > rows - 5);
        assertEquals(model.getLines(), 0);
        assertEquals(model.getLevel(), 0);
        assertSame(model.getGameState(), GameState.PLAYING);

        for (int i = 0; i < columns * 30; i++) {
            model.move(Direction.LEFT);
        }
        board = model.getBoard();
        assertEquals(numCellsOnBoard(board, rows, columns), 8);
        assertEquals(model.getScore(), score);
        assertEquals(model.getLines(), 0);
        assertEquals(model.getLevel(), 0);
        assertSame(model.getGameState(), GameState.PLAYING);

        for (int i = 0; i < columns * 30; i++) {
            model.move(Direction.RIGHT);
        }
        board = model.getBoard();
        assertEquals(numCellsOnBoard(board, rows, columns), 8);
        assertEquals(model.getScore(), score);
        assertEquals(model.getLines(), 0);
        assertEquals(model.getLevel(), 0);
        assertSame(model.getGameState(), GameState.PLAYING);

        for (int i = 0; i < columns * 30; i++) {
            model.move(Direction.UP);
        }
        board = model.getBoard();
        assertEquals(numCellsOnBoard(board, rows, columns), 8);
        assertEquals(model.getScore(), score);
        assertEquals(model.getLines(), 0);
        assertEquals(model.getLevel(), 0);
        assertSame(model.getGameState(), GameState.PLAYING);

        for (int i = 0; i < rows; i++) {
            model.fall();
        }
        board = model.getBoard();
        assertEquals(numCellsOnBoard(board, rows, columns), 12);
        assertEquals(model.getScore(), score);
        assertEquals(model.getLines(), 0);
        assertEquals(model.getLevel(), 0);
        assertSame(model.getGameState(), GameState.PLAYING);

        for (int i = 0; i < rows * 100; i++) {
            for (int j = 0; j < columns; j++) {
                model.move(Direction.RIGHT);
            }
            model.fall();
        }

        board = model.getBoard();
        assertTrue(numCellsOnBoard(board, rows, columns) >= rows);
        assertEquals(model.getScore(), score);
        assertEquals(model.getLines(), 0);
        assertEquals(model.getLevel(), 0);
        assertSame(model.getGameState(), GameState.GAMEOVER);

    }

    private int numCellsOnBoard(int[][] board, int rows, int columns) {
        int count = 0;
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                if (board[i][j] != 0) {
                    count++;
                }
            }
        }
        return count;
    }
}