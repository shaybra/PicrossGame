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
     * Tells if the user is in mark mode or not.
     */
    private boolean isMark = false;

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
     * @param x is the row index.
     * @param y is the column index.
     * @return the value of the grid at the specified row and column.
     */
    public boolean getGrid(int x, int y) {
        return grid[x][y];
    }

    /**
     * Gets the value of the current grid at the specified row and column.
     * @param x the row of the grid.
     * @param y the column of the grid.
     * @return the value of the current grid at the specified row and column.
     */
    public boolean getCurrentGrid(int x, int y) {
        return currentGrid[x][y];
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

    /**
     * Gets the {@link #grid}.
     * 
     * @return the {@link #grid}.
     */
    public boolean getIsMark(){
        return isMark;
    }

    /**
     * Sets the value of the {@link #isMark} to true.
     * @param isMark is the value of the {@link #isMark}.
     */
    public void setIsMark(boolean isMark){
        this.isMark = isMark;
    }

    public void firstSenario() {
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++) {
                if (i == 2 && j == 2) {
                    grid[i][j] = true;
                } else {
                    grid[i][j] = false;
                }
            }

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                currentGrid[i][j] = false;
    }

    public void secondSenario() {
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                grid[i][j] = false;

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                currentGrid[i][j] = false;
    }

    public void thirdSenario() {
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++) {
                if (i == 0 && j == 4) {
                    grid[i][j] = true;
                } else if (i == 1 && j == 0) {
                    grid[i][j] = true;
                } else if (i == 2 && j == 4) {
                    grid[i][j] = true;
                } else if (i == 3 && j == 0) {
                    grid[i][j] = true;
                } else
                    grid[i][j] = false;
                }
            

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                currentGrid[i][j] = false;
    }
}
