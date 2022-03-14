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
     * Random object to generate random numbers
     */
    private Random rand = new Random();

    /**
     * Generates a random grid
     */
    public void generateGrid(){
        System.out.println("Generating grid...");
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                grid[i][j] = rand.nextBoolean();
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Gets the value of the grid at the specified row and column.
     * @return the value of the grid at the specified row and column.
     */
    public boolean getGrid(int x, int y){
        return grid[x][y];
    }

    public void firstSenario() {
    }

    public void secondSenario() {
    }

    public void thirdSenario() {
    }
}
