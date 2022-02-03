import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;

public class FooterPanel extends JPanel {
    JButton resetButton = new JButton("Reset");
    JButton consoleButton = new JButton("Console");
    JButton chatButton = new JButton("Chat");
    JButton eraseButton = new JButton("Erase");
    JButton markButton = new JButton("Mark");
    JButton checkButton = new JButton("Check");

    JLabel timerLabel = new JLabel("Timer: 00:00");
    JLabel scoreLabel = new JLabel("Score: 0");

    GridBagConstraints c = new GridBagConstraints();

    public FooterPanel() {
        setLayout(new GridBagLayout());

        // timer label
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.5;
        c.weighty = 0.5;
        timerLabel.setBackground(new Color(0xAFB6C1));
        timerLabel.setForeground(new Color(0x2364C7));
        timerLabel.setHorizontalAlignment(JLabel.CENTER);
        timerLabel.setToolTipText("Time elapsed");
        add(timerLabel, c);

        // score label
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.5;
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
        c.weightx = 0.5;
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
        c.weightx = 0.5;
        c.weighty = 0.5;
        markButton.setBackground(new Color(0xAFB6C1));
        markButton.setForeground(new Color(0x2364C7));
        markButton.setToolTipText("Mark the grid");
        add(markButton, c);

        // erase button
        c.gridx = 8;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.5;
        c.weighty = 0.5;
        eraseButton.setBackground(new Color(0xAFB6C1));
        eraseButton.setForeground(new Color(0x2364C7));
        eraseButton.setToolTipText("Erase the grid");
        add(eraseButton, c);

        // reset button
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.5;
        c.weighty = 0.5;
        resetButton.setBackground(new Color(0xAFB6C1));
        resetButton.setForeground(new Color(0x2364C7));
        resetButton.setToolTipText("Reset the grid");
        add(resetButton, c);

        // console button
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.5;
        c.weighty = 0.5;
        consoleButton.setBackground(new Color(0xAFB6C1));
        consoleButton.setForeground(new Color(0x2364C7));
        consoleButton.setToolTipText("Open the console");
        add(consoleButton, c);

        // chat button
        c.gridx = 3;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.5;
        c.weighty = 0.5;
        chatButton.setBackground(new Color(0xAFB6C1));
        chatButton.setForeground(new Color(0x2364C7));
        chatButton.setToolTipText("Chat");
        add(chatButton, c);
    }

    public JButton getResetButton() {
        return resetButton;
    }

    public JButton getConsoleButton() {
        return consoleButton;
    }

    public JButton getChatButton() {
        return chatButton;
    }

    public JButton getEraseButton() {
        return eraseButton;
    }

    public JButton getMarkButton() {
        return markButton;
    }   

    public JButton getCheckButton() {
        return checkButton;
    }

    public JLabel getTimerLabel() {
        return timerLabel;
    }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }
}
