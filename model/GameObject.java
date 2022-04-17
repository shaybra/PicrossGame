package model;

public class GameObject {

    private boolean[][] board;
    private int score;
    private int minutes;
    private int seconds;

    public GameObject(boolean[][] board, int score, int minutes, int seconds) {
        this.board = board;
        this.score = score;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    @Override
    public String toString() {
        String output = "`";
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                output += board[i][j] + ",";
        output += score + "," + minutes + "," + seconds;
        return output;
    }
}
