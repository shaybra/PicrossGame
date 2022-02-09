import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.GridLayout;
import java.awt.Color;
import java.awt.BorderLayout;

import java.util.Random;
import java.lang.StringBuilder;

public class TopPanel extends JPanel {
    private JLabel hints[][] = new JLabel[3][5];
    private Random rand = new Random();

    TopPanel() {
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

    public void reset(){
        for(int i=0;i<3;i++)
            for(int j=0;j<5;j++){
                int number = rand.nextInt(6);
                if(number != 0)
                    hints[i][j].setText(new StringBuilder().append(number).toString());
                else
                    hints[i][j].setText("");
            }
    } 
}
