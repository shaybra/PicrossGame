import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private ActionListener timeTask;
    private int score = 0;
    private int seconds = 0;
    private int minutes = 0;

    Controller(JButton resetButton, JButton consoleButton, JButton chatButton, JButton eraseButton, JButton markButton, JButton checkButton, JLabel timerLabel, JLabel scoreLabel) {
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }

            private void reset() {
                score = 0;
                seconds = 0;
                minutes = 0;
                timerLabel.setText("Timer: 00:00");
                scoreLabel.setText("Score: 0");
            }
        });
        consoleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Console button clicked");
            }
        });
        chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Chat button clicked");
            }
        });
        eraseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Erase button clicked");
            }
        });
        markButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Mark button clicked");
            }
        });
        checkButton.addActionListener(new ActionListener() {
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
                timerLabel.setText(String.format("Timer: %02d:%02d", minutes, seconds));
            }
        };
        new Timer(1000, timeTask).start();
    }
}
