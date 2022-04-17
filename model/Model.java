/*
* Name: Mohammed Chabaan and Garrick Weiler
* Due Date: March 20th, 2022
* Class: Model.java
* Proffesor: Daniel Cormier
*/

package model;

import java.util.Random;

/**
 * This class represents a model of the game.
 */
public class Model {
    /**
     * 5x5 Grid of boolean values.
     */
    private boolean[][] grid = new boolean[5][5]; // So you want a play square do ya pal
    /**
     * The users choice of the game.
     */
    private boolean[][] currentGrid = new boolean[5][5]; // well youll also need a comparison grid
    /**
     * Random object to generate random numbers
     */
    private Random rand = new Random(); // invite chao into your house give it a try
    /**
     * Tells if the user is in mark mode or not.
     */
    private boolean isMark = false; // Just make sure mark knows if you need him or not he a determiner

    /**
     * Generates a random grid and resets the current grid.
     */
    public void generateGrid() {
        for (int i = 0; i < 5; i++) // grid isnt sure what hes feeling so he invites chaos over to set up his house
            for (int j = 0; j < 5; j++)
                grid[i][j] = rand.nextBoolean();

        resetCurrentGrid(); // grid empties the house
    }

    /**
     * Resets the current grid.
     */
    public void resetCurrentGrid() {
        for (int i = 0; i < 5; i++) // like i said ealier grid empties his house for chaos to come over and change
                                    // it
            for (int j = 0; j < 5; j++)
                currentGrid[i][j] = false;
        isMark = false; // we never start making the house with mark around
    }

    /**
     * Gets the value of the grid at the specified row and column.
     * 
     * @param x is the row index.
     * @param y is the column index.
     * @return the value of the grid at the specified row and column.
     */
    public boolean getGrid(int x, int y) {
        return grid[x][y]; // sometimes grid need to know what his house looks like
    }

    /**
     * Gets the value of the current grid at the specified row and column.
     * 
     * @param x the row of the grid.
     * @param y the column of the grid.
     * @return the value of the current grid at the specified row and column.
     */
    public boolean getCurrentGrid(int x, int y) {
        return currentGrid[x][y]; // grid likes to refer to the current state of the house to keep tabs on chaos
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
        boolean done = true; // grid almost all the time says annoyingly "are we done yet?"
        currentGrid[x][y] = true;
        for (int i = 0; i < 5; i++) // grid checking each part of his house after chaos is done
            for (int j = 0; j < 5; j++)
                if (grid[i][j])
                    if (!currentGrid[i][j]) {
                        done = false;
                        break;
                    }
        return done; // chaos lets grid know if hes looked at the whole house yet
    }

    /**
     * Checks if the game was perfect.
     * 
     * @return true if the game was perfect, false otherwise.
     */
    public boolean isPerfectGame() {
        boolean perfect = true;
        for (int i = 0; i < 5; i++) // sometimes afer guessing based of chaos's clues he manages to perfectly
                                    // describe the new house set up
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
    public boolean getIsMark() {
        return isMark; // Lets grid know if mark is around
    }

    /**
     * Sets the value of the {@link #isMark} to true.
     * 
     * @param isMark is the value of the {@link #isMark}.
     */
    public void setIsMark(boolean isMark) {
        this.isMark = isMark; // finds out if mark is around
    }

    /**
     * Generates first senario grids
     */
    public void firstSenario() {
        for (int i = 0; i < 5; i++) // the first of three house set ups that grid is fond of
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

    /**
     * Generates second senario grids
     */
    public void secondSenario() {
        for (int i = 0; i < 5; i++) // the second of three house set ups that grid is fond of
            for (int j = 0; j < 5; j++)
                grid[i][j] = false;

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                currentGrid[i][j] = false;
    }

    /**
     * Generates third senario grids
     */
    public void thirdSenario() {
        for (int i = 0; i < 5; i++) // the third of three house set ups that grid is fond of
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

    public void setBoard(boolean[][] board) { //
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                grid[i][j] = board[i][j];
    }
}
