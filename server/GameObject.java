package server;

import java.io.Serializable;

public class GameObject implements Serializable {

    private boolean[][] board;
    private int score;
    private String time;

    public GameObject(boolean[][] board, int score, String time) {
        this.board = board;
        this.score = score;
        this.time = time;
    }

    public boolean getBoard(int x, int y) {
        return board[x][y];
    }

    public int getScore() {
        return score;
    }

    public String getTime() {
        return time;
    }
}
