import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;

public class TopPanel extends JPanel {
    JLabel hints[][] = new JLabel[3][5];

    TopPanel() {
        GridBagConstraints c = new GridBagConstraints();
        setLayout(new GridBagLayout());
        JPanel logoPanel = new JPanel();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        JLabel logo = new JLabel("<html><h1>M&G</h1></html>");
        logo.setForeground(new Color(0x2364C7));
        logoPanel.add(logo);
        add(logoPanel, c);
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
        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 5;
        c.gridheight = 5;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        add(hintsPanel, c);
    }
}
