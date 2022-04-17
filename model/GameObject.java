/*
* Name: Mohammed Chabaan and Garrick Weiler
* Due Date: April 17th, 2022
* Class: GameObject.java
* Proffesor: Daniel Cormier
*/
package model;

/**
 * Game Object is what gets sent over the network using the toString method.
 */
public class GameObject {
    /**
     * the board to send over the network.
     */
    private boolean[][] board;
    /**
     * the score to send over the network.
     */
    private int score;
    /**
     * the mitunes to send over the network.
     */
    private int minutes;
    /**
     * the seconds to send over the network.
     */
    private int seconds;

    /**
     * Constructor to the GameObject class.
     * 
     * @param board   the board to send over the network.
     * @param score   the score to send over the network.
     * @param minutes the minutes to send over the network.
     * @param seconds the seconds to send over the network.
     */
    public GameObject(boolean[][] board, int score, int minutes, int seconds) {
        this.board = board;
        this.score = score;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    /**
     * toString method to send over the network.
     * 
     * @return a string of all the the information in this class all dilimitered by
     *         a ','
     */
    @Override
    public String toString() {
        String output = "`";
        for (int i = 0; i < board.length; i++) // uploads board into a string to send over to the server
            for (int j = 0; j < board[i].length; j++)
                output += board[i][j] + ",";
        output += score + "," + minutes + "," + seconds;
        return output;
    }
}
