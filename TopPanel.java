import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.Color;
import java.awt.BorderLayout;

public class TopPanel extends JPanel {
    JLabel hints[][] = new JLabel[3][5];

    TopPanel() {
        setLayout(new BorderLayout(65,0));
        JPanel logoPanel = new JPanel();
        JLabel logo = new JLabel("<html><h1>M&G</h1></html>");
        logo.setForeground(new Color(0x2364C7));
        logoPanel.add(logo);
        logoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(logoPanel, BorderLayout.WEST);
        JPanel hintsPanel = new JPanel();
        hintsPanel.setLayout(new GridLayout(3, 5));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                hints[i][j] = new JLabel("Hint" + "(" + (i + 1) + "," + (j + 1) + ")");
                hints[i][j].setBackground(new Color(0xAFB6C1));
                hints[i][j].setForeground(new Color(0x2364C7));
                hintsPanel.add(hints[i][j]);
            }
        }
        add(hintsPanel, BorderLayout.CENTER);
    }
}
