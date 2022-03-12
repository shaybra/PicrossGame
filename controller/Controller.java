package controller;

import javax.swing.JButton;
import javax.swing.Timer;

import view.ChatFrame;
import view.Frame;

import model.Model;

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
     * Frame of the game, it's the view.
     * 
     * @see Frame
     */
    private Frame mainFrame;
    /**
     * ChatFrame of the game.
     * 
     * @see ChatFrame
     */
    private ChatFrame chat = new ChatFrame(this);
    /**
     * Model of the game.
     * 
     * @see Model
     */
    private Model model = new Model();

    /**
     * Constructor for the Controller class.
     * 
     * @param frame the frame of the game.
     * @see Frame
     */
    public Controller(Frame frame) {
        mainFrame = frame;
        reset();
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
                    chat.chatWindow(mainFrame.getX() + mainFrame.getWidth() + 10, mainFrame.getY());
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
                    // get the row and column of the button in the grid that was clicked
                    for (int i = 0; i < 5; i++)
                        for (int j = 0; j < 5; j++)
                            if (mainFrame.getGridPanel().getGridButton(i, j) == button) {
                                if(model.getGrid(i, j)){
                                    mainFrame.getGridPanel().correct(i, j);
                                    score = mainFrame.getFooterPanel().updateScore(score);
                                }else
                                    mainFrame.getGridPanel().incorrect(i, j);
                            }
                    break;
            }
            chat.updateChat(output);
        }
    }

    /**
     * Resets the game.
     */
    private void reset() {
        score = 0;
        seconds = 0;
        minutes = 0;

        mainFrame.getGridPanel().reset();

        model.generateGrid();
        mainFrame.getSidePanel().generateHints(model);
        mainFrame.getTopPanel().generateHints(model);

        mainFrame.getFooterPanel().resetFooter();
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
        mainFrame.getFooterPanel().updateTime(minutes, seconds);
    }
}
