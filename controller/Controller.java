package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.Timer;

import model.Model;
import view.Frame;

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
        newGame();
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
                    mainFrame.getChat().chatWindow(mainFrame.getX() + mainFrame.getWidth() + 10, mainFrame.getY());
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
                    output = mainFrame.getChat().getInput().getText() + "\n";
                    break;
                case "New":
                    newGame();
                    break;
                case "Exit":
                    mainFrame.dispose();
                    System.exit(0);
                    break;
                case "About":
                    mainFrame.showAboutDialog();
                    break;
                case "First senario":
                    reset();
                    model.firstSenario();
                    mainFrame.getTopPanel().generateHints(model);
                    mainFrame.getSidePanel().generateHints(model);
                    break;
                case "Second senario":
                    reset();
                    model.secondSenario();
                    mainFrame.getTopPanel().generateHints(model);
                    mainFrame.getSidePanel().generateHints(model);
                    if(mainFrame.perfectGame() == 0)
                                newGame();
                    break;
                case "Third senario":
                    reset();
                    model.thirdSenario();
                    mainFrame.getTopPanel().generateHints(model);
                    mainFrame.getSidePanel().generateHints(model);
                    break;
                case "Solution":
                    //output solution
                    for (int i = 0; i < 5; i++)
                        for (int j = 0; j < 5; j++)
                                if(model.getGrid(i, j))
                                    mainFrame.getGridPanel().correct(i, j);
                                else
                                    mainFrame.getGridPanel().incorrect(i, j);
                    break;
                default:
                    JButton button = (JButton) e.getSource();
                    boolean done = false;
                    // get the row and column of the button in the grid that was clicked
                    for (int i = 0; i < 5; i++)
                        for (int j = 0; j < 5; j++)
                            if (mainFrame.getGridPanel().getGridButton(i, j) == button) {
                                done = model.updateCurrentGrid(i, j);
                                if(model.getGrid(i, j)){
                                    mainFrame.getGridPanel().correct(i, j);
                                    score = mainFrame.getFooterPanel().updateScore(score);
                                }else
                                    mainFrame.getGridPanel().incorrect(i, j);
                            }
                    if(done)
                        if(model.isPerfectGame()){
                            if(mainFrame.perfectGame() == 0)
                                newGame();
                        } else{
                            if(mainFrame.gameOver() == 0)
                                newGame();
                        }
                    break;
            }
            mainFrame.getChat().updateChat(output);
        }
    }

    /**
     * Makes a new game.
     */
    private void newGame() {
        score = 0;
        seconds = 0;
        minutes = 0;

        mainFrame.getGridPanel().reset();

        model.generateGrid();
        mainFrame.getSidePanel().generateHints(model);
        mainFrame.getTopPanel().generateHints(model);

        mainFrame.getFooterPanel().resetFooter();

        if(model.isPerfectGame())
            if(mainFrame.perfectGame() == 0)
                newGame();
    
    }
    /**
     * Resets the game.
     */
    public void reset(){
        score = 0;
        seconds = 0;
        minutes = 0;

        mainFrame.getGridPanel().reset();

        mainFrame.getFooterPanel().resetFooter();

        model.resetCurrentGrid();
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
