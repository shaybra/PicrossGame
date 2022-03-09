package view;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.GridLayout;

/**
 * GridPanel is a JPanel that contains the grid.
 */
public class GridPanel extends JPanel {
    /**
     * JButton array to store the grid.
     */
    private JButton gridButtons[][] = new JButton[5][5];

    /**
     * Constructor for the GridPanel class.
     */
    public GridPanel() {
        setLayout(new GridLayout(5, 5));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                gridButtons[i][j] = new JButton("");
                add(gridButtons[i][j]);
            }
        }
    }
    
    /**
     * Getter for the gridButtons.
     * @param row the row of the gridButton.
     * @param col the column of the gridButton.
     * @return the gridButton at the specified row and column.
     */
    public JButton getGridButton(int row, int col) {
        return gridButtons[row][col];
    }
}
