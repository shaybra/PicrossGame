package model;

import java.util.Random;

/**
 * This class represents a model of the game.
 */
public class Model {
    /**
     * 5x5 Grid of boolean values.
     */
    private boolean[][] grid = new boolean[5][5];
    /**
     * The users choice of the game.
     */
    private boolean[][] currentGrid = new boolean[5][5];
    /**
     * Random object to generate random numbers
     */
    private Random rand = new Random();

    /**
     * Generates a random grid and resets the current grid.
     */
    public void generateGrid() {
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                grid[i][j] = rand.nextBoolean();
        resetCurrentGrid();
    }

    /**
     * Resets the current grid.
     */
    public void resetCurrentGrid() {
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                currentGrid[i][j] = false;
    }

    /**
     * Gets the value of the grid at the specified row and column.
     * 
     * @return the value of the grid at the specified row and column.
     */
    public boolean getGrid(int x, int y) {
        return grid[x][y];
    }

    /**
     * Sets the value of the {@link #currentGrid} at the specified row and column to
     * true.
     * 
     * @param x is the row index of the {@link #currentGrid}.
     * @param y is the column index of the {@link #currentGrid}.
     * @return true if the game is done, false otherwise.
     */
    public boolean updateCurrentGrid(int x, int y) {
        boolean done = true;
        currentGrid[x][y] = true;
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                if (grid[i][j])
                    if (!currentGrid[i][j]) {
                        done = false;
                        break;
                    }
        return done;
    }

    /**
     * Checks if the game was perfect.
     * 
     * @return true if the game was perfect, false otherwise.
     */
    public boolean isPerfectGame() {
        boolean perfect = true;
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                if (grid[i][j] != currentGrid[i][j]) {
                    perfect = false;
                    break;
                }
        return perfect;
    }

    public void firstSenario() {
    }

    public void secondSenario() {
    }

    public void thirdSenario() {
    }
}
