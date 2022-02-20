import javax.swing.JButton;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller class for the game.
 */
public class Controller implements ActionListener {
    /**
     * score of the game.
     */
    private int score = 0;
    /**
     * Seconds of the game timer.
     */
    private int seconds = 0;
    /**
     * Minutes of the game timer.
     */
    private int minutes = 0;
    /**
     * Frame of the game.
     * 
     * @see Frame
     */
    private Frame mainFrame;
    /**
     * View of the game.
     * 
     * @see View
     */
    private View view;
    /**
     * ChatFrame of the game.
     * 
     * @see ChatFrame
     */
    private ChatFrame chat = new ChatFrame();

    /**
     * Constructor for the Controller class.
     * 
     * @param frame the frame of the game.
     * @see Frame
     */
    Controller(Frame frame) {
        mainFrame = frame;
        // add action listener to the buttons
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                frame.getGridPanel().getGridButtons()[i][j].addActionListener(this);
        frame.getFooterPanel().getResetButton().addActionListener(this);
        frame.getFooterPanel().getChatButton().addActionListener(this);
        frame.getFooterPanel().getEraseButton().addActionListener(this);
        frame.getFooterPanel().getMarkButton().addActionListener(this);
        frame.getFooterPanel().getCheckButton().addActionListener(this);
        chat.getSend().addActionListener(this);
        // add action listener to the timer that runs every second
        new Timer(1000, this).start();
    }

    /**
     * ActionPerformed method for the Controller class.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String output = new String();
        if (e.getActionCommand() == null)
            time();
        else {
            switch (e.getActionCommand()) {
                case "Reset":
                    reset();
                    break;
                case "Chat":
                    chat.setSize(306, 920);
                    // set the location of the chat frame to right of the main frame with 10 pixels
                    // of space
                    chat.setLocation(mainFrame.getX() + mainFrame.getWidth() + 10, mainFrame.getY());
                    chat.setVisible(!chat.isVisible());
                    break;
                case "Erase":
                    output = "Erase button clicked\n";
                    break;
                case "Mark":
                    output = "Mark button clicked\n";
                    break;
                case "Check":
                    output = "Check button clicked\n";
                    break;
                case "Send":
                    output = chat.getInput().getText() + "\n";
                    break;
                default:
                    JButton button = (JButton) e.getSource();
                    int row = 0;
                    int col = 0;
                    // to be changed to actual scoring in the future
                    score++;
                    mainFrame.getFooterPanel().getScoreLabel().setText("Score: " + score);
                    // get the row and column of the button in the grid that was clicked
                    for (int i = 0; i < 5; i++)
                        for (int j = 0; j < 5; j++)
                            if (mainFrame.getGridPanel().getGridButtons()[i][j] == button) {
                                row = i + 1;
                                col = j + 1;
                            }
                    output = "row: " + row + " col: " + col + "\n";
                    break;
            }
            chat.updateOutput(output);
        }
    }

    /**
     * Resets the game.
     */
    private void reset() {
        score = 0;
        seconds = 0;
        minutes = 0;
        mainFrame.getFooterPanel().getTimerLabel().setText("Timer: 00:00");
        mainFrame.getFooterPanel().getScoreLabel().setText("Score: 0");
        mainFrame.getTopPanel().reset();
        mainFrame.getSidePanel().reset();
    }

    /**
     * Updates the timer.
     */
    private void time() {
        seconds++;
        if (seconds == 60) {
            seconds = 0;
            minutes++;
        }
        mainFrame.getFooterPanel().getTimerLabel().setText(String.format("Timer: %02d:%02d", minutes, seconds));
    }
}
