import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.GridLayout;

public class GridPanel extends JPanel {
    private JButton gridButtons[][] = new JButton[5][5];

    public GridPanel() {
        setLayout(new GridLayout(5, 5));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                gridButtons[i][j] = new JButton("");
                add(gridButtons[i][j]);
            }
        }
    }
    
    public JButton[][] getGridButtons() {
        return gridButtons;
    }
}
