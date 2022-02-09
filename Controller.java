import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private ActionListener timeTask;
    private int score = 0;
    private int seconds = 0;
    private int minutes = 0;
    
    private JFrame console = new JFrame("Console");
    private JFrame chat = new JFrame("Chat");

    Controller(Frame frame) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                frame.getGridPanel().getGridButtons()[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton button = (JButton) e.getSource();
                        int row = 0;
                        int col = 0;
                        // to be changed to actual scoring in the future
                        score++;
                        frame.getFooterPanel().getScoreLabel().setText("Score: " + score);
                        for (int i = 0; i < 5; i++) {
                            for (int j = 0; j < 5; j++) {
                                if (frame.getGridPanel().getGridButtons()[i][j] == button) {
                                    row = i + 1;
                                    col = j + 1;
                                }
                            }
                        }
                        System.out.println("row: " + row + " col: " + col);
                    }
                });
            }
        }
        frame.getFooterPanel().getResetButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }

            private void reset() {
                score = 0;
                seconds = 0;
                minutes = 0;
                frame.getFooterPanel().getTimerLabel().setText("Timer: 00:00");
                frame.getFooterPanel().getScoreLabel().setText("Score: 0");
                frame.getTopPanel().reset();
                frame.getSidePanel().reset();
            }
        });
        frame.getFooterPanel().getConsoleButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                console.setSize(306, 920);
                console.setLocation(frame.getX() - console.getWidth() - 10, frame.getY());
                console.setVisible(console.isVisible() ? false : true);
            }
        });
        frame.getFooterPanel().getChatButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chat.setSize(306, 920);
                chat.setLocation(frame.getX() + frame.getWidth() + 10, frame.getY());
                chat.setVisible(chat.isVisible() ? false : true);
            }
        });
        frame.getFooterPanel().getEraseButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Erase button clicked");
            }
        });
        frame.getFooterPanel().getMarkButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Mark button clicked");
            }
        });
        frame.getFooterPanel().getCheckButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Check button clicked");
            }
        });
        timeTask = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds++;
                if (seconds == 60) {
                    seconds = 0;
                    minutes++;
                }
                frame.getFooterPanel().getTimerLabel().setText(String.format("Timer: %02d:%02d", minutes, seconds));
            }
        };
        new Timer(1000, timeTask).start();
    }
}
