package model;

public class GameObject {

    private boolean[][] board;
    private int score;
    private String time;

    public GameObject(boolean[][] board, int score, String time) {
        this.board = board;
        this.score = score;
        this.time = time;
    }

    @Override
    public String toString() {
        String output = "`";
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                output += board[i][j] + ",";
        output += score + "," + time;
        return output;
    }
}
