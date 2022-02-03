import javax.swing.Timer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private ActionListener timeTask;
    private int score = 0;
    private int seconds = 0;
    private int minutes = 0;

    Controller(Frame frame) {
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
            }
        });
        frame.getFooterPanel().getConsoleButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Console button clicked");
            }
        });
        frame.getFooterPanel().getChatButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Chat button clicked");
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
