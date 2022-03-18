package view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Model;

/**
 * SidePanel is a JPanel that contains the side hints.
 */
public class SidePanel extends JPanel {
    /**
     * JLabel array that contains the hints.
     */
    private JLabel hints[][] = new JLabel[5][3];

    /**
     * Constructor for SidePanel.
     */
    public SidePanel() {
        setLayout(new GridLayout(5, 3, 5, 0));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                hints[i][j] = new JLabel();
                hints[i][j].setForeground(new Color(0xFFFFFF));
                hints[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                hints[i][j].setVerticalAlignment(SwingConstants.CENTER);
                add(hints[i][j]);
            }
        }
    }

    /**
     * Regenerates the hints in the array.
     */
    public void generateHints(Model model) {
        int count, col;
        resetHints();
        for (int i = 4; i >= 0; i--) {
            count = 0;
            col = 2;
            for (int j = 4; j >= 0; j--) {
                if (model.getGrid(i, j)) {
                    count++;
                    if (count == 5) {
                        hints[i][col--].setText(new StringBuilder().append(count).toString());
                        count = 0;
                    }
                } else {
                    if (count != 0) {
                        hints[i][col--].setText(new StringBuilder().append(count).toString());
                        count = 0;
                    }
                }
            }
            if (count != 0) {
                hints[i][col--].setText(new StringBuilder().append(count).toString());
                count = 0;
            }
        }
    }

    /**
     * Cleans the hints in the array.
     */
    private void resetHints() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                hints[i][j].setText("");
            }
        }
    }
}
