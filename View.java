/**
 * View class for the game.
 */
public class View {
    /**
     * {@link Frame} of the game.
     */
    private Frame frame;
    /**
     * {@link ChatFrame} of the game.
     */
    private ChatFrame chat;

    /**
     * Constructor for the View class.
     * @param frame the frame of the game.
     */
    public View(Frame frame, ChatFrame chat) {
        this.frame = frame;
        this.chat = chat;
    }

    /**
     * Updates the timer.
     * @param minutes the minutes of the timer.
     * @param seconds the seconds of the timer.
     */
    public void updateTime(int minutes, int seconds) {
        frame.getFooterPanel().getTimerLabel().setText(String.format("Timer: %02d:%02d", minutes, seconds));
    }

    /**
     * Resets the UI.
     */
    public void reset(){
        frame.getFooterPanel().getTimerLabel().setText("Timer: 00:00");
        frame.getFooterPanel().getScoreLabel().setText("Score: 0");
        frame.getTopPanel().reset();
        frame.getSidePanel().reset();
    }

    /**
     * Updates the output of the chat area.
     * @param output the output to be added to the chat.
     */
    public void updateChat(String output) {
        chat.getOutput().append(output);
        chat.getOutput().setCaretPosition(chat.getOutput().getDocument().getLength());
    }

}
