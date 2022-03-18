package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import model.Model;

/**
 * TopPanel is a JPanel that contains the top hints and the logo.
 */
public class TopPanel extends JPanel {
    /**
     * JLabel array that contains the hints.
     */
    private JLabel hints[][] = new JLabel[3][5];
    /**
     * Random object used to generate random hints.
     */
    private Random rand = new Random();

    /**
     * Constructor for TopPanel.
     */
    public TopPanel() {
        setLayout(new BorderLayout());
        JPanel logoPanel = new JPanel();
        JLabel logo = new JLabel("<html><h1>M&G</h1></html>");
        logo.setForeground(new Color(0x2364C7));
        logoPanel.setBorder(BorderFactory.createEmptyBorder(27, 27, 27, 27));
        logoPanel.add(logo);
        add(logoPanel, BorderLayout.WEST);
        JPanel hintsPanel = new JPanel();
        hintsPanel.setLayout(new GridLayout(3, 5));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                int number = rand.nextInt(6);
                hints[i][j] = number != 0 ? new JLabel(new StringBuilder().append(number).toString()) : new JLabel();
                hints[i][j].setForeground(new Color(0xFFFFFF));
                hints[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                hints[i][j].setVerticalAlignment(SwingConstants.CENTER);
                hintsPanel.add(hints[i][j]);
            }
        }
        hintsPanel.setBackground(new Color(0x2364C7));
        add(hintsPanel, BorderLayout.CENTER);
    }

    /**
     * Regenerates the hints in the array.
     */
    public void generateHints(Model model) {
        int count, row;
        resetHints();
        for (int j = 4; j >= 0; j--) {
            count = 0;
            row = 2;
            for (int i = 4; i >= 0; i--) {
                if (model.getGrid(i, j)) {
                    count++;
                    if (count == 5) {
                        hints[row--][j].setText(new StringBuilder().append(count).toString());
                        count = 0;
                    }
                } else {
                    if (count != 0) {
                        hints[row--][j].setText(new StringBuilder().append(count).toString());
                        count = 0;
                    }
                }
            }
            if (count != 0) {
                hints[row--][j].setText(new StringBuilder().append(count).toString());
                count = 0;
            }
        }
    }

    /**
     * Cleans the hints in the array.
     */
    private void resetHints() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 5; j++)
                hints[i][j].setText("");
    }
}
