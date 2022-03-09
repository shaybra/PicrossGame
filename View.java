/**
 * View class for the game.
 */
public class View {
    /**
     * Updates the timer.
     * 
     * @param minutes the minutes of the timer.
     * @param seconds the seconds of the timer.
     */
    public void updateTime(int minutes, int seconds, Frame frame) {
        frame.getFooterPanel().getTimerLabel().setText(String.format("Timer: %02d:%02d", minutes, seconds));
    }

    /**
     * Resets the UI.
     */
    public void reset(Frame frame) {
        frame.getFooterPanel().getTimerLabel().setText("Timer: 00:00");
        frame.getFooterPanel().getScoreLabel().setText("Score: 0");
        frame.getTopPanel().reset();
        frame.getSidePanel().reset();
    }

    /**
     * Updates the output of the chat area.
     * 
     * @param output the output to be added to the chat.
     */
    public void updateChat(String output, ChatFrame chat) {
        chat.getOutput().append(output);
        chat.getOutput().setCaretPosition(chat.getOutput().getDocument().getLength());
    }

    /**
     * Updates the chat window when Chat button is clicked.
     * @param chat the chat frame.
     * @param frame the main frame.
     */
    public void chatWindow(ChatFrame chat, Frame frame) {
        chat.setSize(306, 920);
        // set the location of the chat frame to right of the main frame with 10 pixels
        // of space
        chat.setLocation(frame.getX() + frame.getWidth() + 10, frame.getY());
        chat.setVisible(!chat.isVisible());
    }

    /**
     * Updates the score.
     * @param frame the main frame.
     * @param score the score.
     * @return the updated score.
     */
    public int updateScore(Frame frame, int score) {
        // to be changed to actual scoring in the future
        score++;
        frame.getFooterPanel().getScoreLabel().setText("Score: " + score);
        return score;
    }
}
