package view;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;

/**
 * FooterPanel is a JPanel that contain the contents of the footer.
 */
public class FooterPanel extends JPanel {
    /**
     * JButton to reset the game.
     */
    JButton resetButton = new JButton("Reset");
    /**
     * JButton to open the chat.
     * @see ChatFrame
     */
    JButton chatButton = new JButton("Chat");
    /**
     * JButton to erase from the grid.
     */
    JButton eraseButton = new JButton("Erase");
    /**
     * JButton to mark the grid.
     */
    JButton markButton = new JButton("Mark");
    /**
     * JButton to check the grid.
     */
    JButton checkButton = new JButton("Check");

    /**
     * JLabel to display the number of minutes and seconds.
     */
    JLabel timerLabel = new JLabel("Timer: 00:00");
    /**
     * JLabel to display the score.
     */
    JLabel scoreLabel = new JLabel("Score: 0");

    /**
     * GridBagConstraints to set the constraints of the components.
     */
    GridBagConstraints c = new GridBagConstraints();

    /**
     * Constructor for FooterPanel.
     */
    public FooterPanel() {
        setLayout(new GridBagLayout());

        c.fill = GridBagConstraints.HORIZONTAL;
        
        // timer label
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.weightx = 0.3;
        c.weighty = 0.5;
        timerLabel.setBackground(new Color(0xAFB6C1));
        timerLabel.setForeground(new Color(0x2364C7));
        timerLabel.setHorizontalAlignment(JLabel.CENTER);
        timerLabel.setToolTipText("Time elapsed");
        add(timerLabel, c);

        // score label
        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.weightx = 0.3;
        c.weighty = 0.5;
        scoreLabel.setBackground(new Color(0xAFB6C1));
        scoreLabel.setForeground(new Color(0x2364C7));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setToolTipText("Score");
        add(scoreLabel, c);

        // check button
        c.gridx = 6;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.15;
        c.weighty = 0.5;
        checkButton.setBackground(new Color(0xAFB6C1));
        checkButton.setForeground(new Color(0x2364C7));
        checkButton.setToolTipText("Check the grid");
        add(checkButton, c);

        // mark button
        c.gridx = 7;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.15;
        c.weighty = 0.5;
        markButton.setBackground(new Color(0xAFB6C1));
        markButton.setForeground(new Color(0x2364C7));
        markButton.setToolTipText("Mark the grid");
        add(markButton, c);


        // reset button
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.weightx = 0.3;
        c.weighty = 0.5;
        resetButton.setBackground(new Color(0xAFB6C1));
        resetButton.setForeground(new Color(0x2364C7));
        resetButton.setToolTipText("Reset the grid");
        add(resetButton, c);

        // chat button
        c.gridx = 6;
        c.gridy = 1;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.weightx = 0.3;
        c.weighty = 0.5;
        chatButton.setBackground(new Color(0xAFB6C1));
        chatButton.setForeground(new Color(0x2364C7));
        chatButton.setToolTipText("Chat");
        add(chatButton, c);
    }

    /**
     * Add action listener to the buttons.
     * @param controller the controller of the game.
     */
    public void addListener(Controller controller){
        resetButton.addActionListener(controller);
        chatButton.addActionListener(controller);
        eraseButton.addActionListener(controller);
        markButton.addActionListener(controller);
        checkButton.addActionListener(controller);
    }

    /**
     * Updates the timer.
     * 
     * @param minutes the minutes of the timer.
     * @param seconds the seconds of the timer.
     */
    public void updateTime(int minutes, int seconds){
        timerLabel.setText(String.format("Timer: %02d:%02d", minutes, seconds));
    }

    /**
     * Updates the score.
     * @param score the score.
     * @return the updated score.
     */
    public int updateScore(int score) {
        // to be changed to actual scoring in the future
        score++;
        scoreLabel.setText("Score: " + score);
        return score;
    }

    /**
     * Resets the timer and score.
     */
    public void resetFooter(){
        timerLabel.setText("Timer: 00:00");
        scoreLabel.setText("Score: 0");
    }
    
    /**
     * Getter for the reset button.
     * @return {@link #resetButton}
     */
    public JButton getResetButton() {
        return resetButton;
    }

    /**
     * Getter for the chat button.
     * @return {@link #chatButton}
     */
    public JButton getChatButton() {
        return chatButton;
    }

    /**
     * Getter for the erase button.
     * @return {@link #eraseButton}
     */
    public JButton getEraseButton() {
        return eraseButton;
    }

    /**
     * Getter for the mark button.
     * @return {@link #markButton}
     */
    public JButton getMarkButton() {
        return markButton;
    }   

    /**
     * Getter for the check button.
     * @return {@link #checkButton}
     */
    public JButton getCheckButton() {
        return checkButton;
    }

    /**
     * Getter for the timer label.
     * @return {@link #timerLabel}
     */
    public JLabel getTimerLabel() {
        return timerLabel;
    }

    /**
     * Getter for the score label.
     * @return {@link #scoreLabel}
     */
    public JLabel getScoreLabel() {
        return scoreLabel;
    }
}
