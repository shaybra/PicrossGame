import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    private int score = 0;
    private int seconds = 0;
    private int minutes = 0;
    private Frame mainFrame;
    private JFrame console = new JFrame("Console");
    private JFrame chat = new JFrame("Chat");

    Controller(Frame frame) {
        mainFrame = frame;
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                frame.getGridPanel().getGridButtons()[i][j].addActionListener(this);
        frame.getFooterPanel().getResetButton().addActionListener(this);
        frame.getFooterPanel().getConsoleButton().addActionListener(this);
        frame.getFooterPanel().getChatButton().addActionListener(this);
        frame.getFooterPanel().getEraseButton().addActionListener(this);
        frame.getFooterPanel().getMarkButton().addActionListener(this);
        frame.getFooterPanel().getCheckButton().addActionListener(this);
        new Timer(1000, this).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == null)
            time();
        else
            switch (e.getActionCommand()) {
                case "Reset":
                    reset();
                    break;
                case "Console":
                    console.setSize(306, 920);
                    console.setLocation(mainFrame.getX() - console.getWidth() - 10, mainFrame.getY());
                    console.setVisible(console.isVisible() ? false : true);
                    break;
                case "Chat":
                    chat.setSize(306, 920);
                    chat.setLocation(mainFrame.getX() + mainFrame.getWidth() + 10, mainFrame.getY());
                    chat.setVisible(chat.isVisible() ? false : true);
                    break;
                case "Erase":
                    System.out.println("Erase button clicked");
                    break;
                case "Mark":
                    System.out.println("Mark button clicked");
                    break;
                case "Check":
                    System.out.println("Check button clicked");
                    break;
                default:
                    JButton button = (JButton) e.getSource();
                    int row = 0;
                    int col = 0;
                    // to be changed to actual scoring in the future
                    score++;
                    mainFrame.getFooterPanel().getScoreLabel().setText("Score: " + score);
                    for (int i = 0; i < 5; i++)
                        for (int j = 0; j < 5; j++)
                            if (mainFrame.getGridPanel().getGridButtons()[i][j] == button) {
                                row = i + 1;
                                col = j + 1;
                            }
                    System.out.println("row: " + row + " col: " + col);
                    break;
            }
    }

    private void reset() {
        score = 0;
        seconds = 0;
        minutes = 0;
        mainFrame.getFooterPanel().getTimerLabel().setText("Timer: 00:00");
        mainFrame.getFooterPanel().getScoreLabel().setText("Score: 0");
        mainFrame.getTopPanel().reset();
        mainFrame.getSidePanel().reset();
    }

    private void time() {
        seconds++;
        if (seconds == 60) {
            seconds = 0;
            minutes++;
        }
        mainFrame.getFooterPanel().getTimerLabel().setText(String.format("Timer: %02d:%02d", minutes, seconds));
    }
}
