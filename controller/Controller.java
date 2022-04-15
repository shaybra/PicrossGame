/*
* Name: Mohammed Chabaan and Garrick Weiler
* Due Date: March 20th, 2022
* Class: Controller.java
* Proffesor: Daniel Cormier
*/
package controller;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.Timer;

import model.Client;
import model.Model;
import view.Frame;
import view.NetwokDialog;

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
     * Timer of the game.
     */
    private Timer timer;
    /**
     * Network dialog for the game.
     */
    private NetwokDialog netwokDialog;

    private Socket socket;

    private Client client;

    /**
     * Constructor for the Controller class.
     * 
     * @param frame the frame of the game.
     * @see Frame
     */
    public Controller(Frame frame) {
        mainFrame = frame; // sets up the base look of grid's house
        // add action listener to the timer that runs every second
        timer = new Timer(1000, this);
        timer.start();
        newGame();
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
            switch (e.getActionCommand()) { // everyones constantly listening to grid to understand when he wants to do
                                            // something
                case "Reset": // grid starts from the top trying to recall how he thinks chaos set up the
                              // house
                    reset();
                    break;
                case "Chat": // grid checks his mail
                    mainFrame.getChat().chatWindow(mainFrame.getX() + mainFrame.getWidth() + 10, mainFrame.getY());
                    break;
                case "Mark": // asks mark to come over
                    model.setIsMark(true);
                    mainFrame.getGridPanel().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                    break;
                case "Check": // asks mark to leave
                    model.setIsMark(false);
                    mainFrame.getGridPanel().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    break;
                case "Send": // lets grid send mail
                    output = mainFrame.getChat().getInput().getText();
                    if (!output.isEmpty() && client != null)
                        client.sendMessage(output);
                    break;
                case "New": // grid asks chaos to change the inside of his house again
                    newGame();
                    break;
                case "Exit": // grid stops plaing with chaos for the day
                    mainFrame.dispose();
                    System.exit(0);
                    break;
                case "About": // the sign at the front of grids house
                    mainFrame.showAboutDialog();
                    break;
                case "First senario": // grid trys the first house configuration hes used too
                    reset();
                    model.firstSenario();
                    mainFrame.getTopPanel().generateHints(model);
                    mainFrame.getSidePanel().generateHints(model);
                    break;
                case "Second senario": // grid trys the second house configuration hes used too
                    reset();
                    model.secondSenario();
                    mainFrame.getTopPanel().generateHints(model);
                    mainFrame.getSidePanel().generateHints(model);
                    if (mainFrame.perfectGame() == 0)
                        newGame();
                    break;
                case "Third senario": // grid trys the third house configuration hes used too
                    reset();
                    model.thirdSenario();
                    mainFrame.getTopPanel().generateHints(model);
                    mainFrame.getSidePanel().generateHints(model);
                    break;
                case "Solution": // sometimes grid just likes to get chaos to tell him how the house is set up
                    for (int i = 0; i < 5; i++)
                        for (int j = 0; j < 5; j++)
                            if (model.getGrid(i, j))
                                mainFrame.getGridPanel().addCheck(i, j);
                    break;
                case "Connect":
                    netwokDialog = new NetwokDialog(mainFrame);
                    mainFrame.getMenu().connected(netwokDialog.pressedConnect());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            client = new Client(connectSocket(), netwokDialog.getName());
                            if (client != null)
                                try {
                                    client.receiveMessage(mainFrame.getChat());
                                } catch (InvocationTargetException | InterruptedException e) {
                                    e.printStackTrace();
                                }
                        }
                    }).start();
                    break;
                case "Disconnect":
                    mainFrame.getMenu().connected(false);
                    break;
                default: // grid plays with chaos
                    JButton button = (JButton) e.getSource();
                    boolean done = false;
                    // get the row and column of the button in the grid that was clicked
                    for (int i = 0; i < 5; i++)
                        for (int j = 0; j < 5; j++)
                            if (mainFrame.getGridPanel().getGridButton(i, j) == button) {
                                done = model.updateCurrentGrid(i, j);
                                if (!model.getIsMark())
                                    if (model.getGrid(i, j)) {
                                        mainFrame.getGridPanel().correct(i, j);
                                        score = mainFrame.getFooterPanel().updateScore(score);
                                    } else {
                                        mainFrame.getGridPanel().incorrect(i, j);
                                        mainFrame.getGridPanel().addMark(i, j);
                                    }
                                else {
                                    if (model.getGrid(i, j)) {
                                        mainFrame.getGridPanel().correct(i, j);
                                        mainFrame.getGridPanel().addMark(i, j);
                                    } else {
                                        mainFrame.getGridPanel().incorrect(i, j);
                                        score = mainFrame.getFooterPanel().updateScore(score);
                                    }
                                }
                            }
                    if (done) {
                        timer.stop();
                        for (int i = 0; i < 5; i++) {
                            for (int j = 0; j < 5; j++) {
                                if (!model.getGrid(i, j) && !model.getCurrentGrid(i, j)) {
                                    score = mainFrame.getFooterPanel().updateScore(score);
                                }
                            }
                        }
                        if (model.isPerfectGame() || score == 25) {
                            mainFrame.getFooterPanel().updateScore(24);
                            if (mainFrame.perfectGame() == 0)
                                newGame();
                        } else {
                            if (mainFrame.gameOver() == 0)
                                newGame();
                        }
                    }
                    break;
            }
            if (!output.isEmpty())
                mainFrame.getChat().updateChat("You: " + output + "\n");
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

        model.generateGrid(); // gets a new configuration for grids house with chaos's hints
        mainFrame.getSidePanel().generateHints(model);
        mainFrame.getTopPanel().generateHints(model);

        mainFrame.getFooterPanel().resetFooter();

        if (model.isPerfectGame()) // grid always asks chaos if his house is empty
            if (mainFrame.perfectGame() == 0)
                newGame();
        timer.start();
    }

    /**
     * Resets the game.
     */
    public void reset() {
        score = 0;
        seconds = 0;
        minutes = 0;

        mainFrame.getGridPanel().reset(); // lets grid start from the top

        mainFrame.getFooterPanel().resetFooter();

        model.resetCurrentGrid();
        timer.start();
    }

    /**
     * Updates the timer.
     */
    private void time() {
        seconds++;
        if (seconds == 60) { // chaos always runs a timer for grid
            seconds = 0;
            minutes++;
        }
        mainFrame.getFooterPanel().updateTime(minutes, seconds);
    }

    public Socket connectSocket() {
        socket = new Socket();
        try {
            socket.connect(
                    new InetSocketAddress(InetAddress.getByName(netwokDialog.getAddress()), netwokDialog.getPort()),
                    10000);
            socket.setSoTimeout(10000);
            return socket;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}
