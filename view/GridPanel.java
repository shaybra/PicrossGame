package view;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.Controller;

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
     * Add a listener to the grid.
     * @param controller is a Controller object.
     */
    public void addListener(Controller controller){
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                gridButtons[i][j].addActionListener(controller);
    }

    /**
     * Reacts if the user clicks a correct button.
     * @param x is the row index of the button.
     * @param y is the column index of the button.
     */
    public void correct(int x,int y){
        gridButtons[x][y].setEnabled(false);
        gridButtons[x][y].setBackground(Color.GREEN);
    }

    /**
     * Reacts if the user clicks a incorrect button.
     * @param x is the row index of the button.
     * @param y is the column index of the button.
     */
    public void incorrect(int x,int y){
        gridButtons[x][y].setEnabled(false);
        gridButtons[x][y].setBackground(Color.RED);
    }

    public void mark(int x, int y){
        gridButtons[x][y].setBackground(Color.GRAY);
    }

    /**
     * Resets the grid buttons.
     */
    public void reset(){
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                gridButtons[i][j].setEnabled(true);
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                gridButtons[i][j].setBackground(Color.WHITE);
    }

    /**
     * Sets the cursor of all buttons in the grid to the cursor given in the parameter.
     * @param c is the cursor to set.
     */
    @Override
    public void setCursor(Cursor c){
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                gridButtons[i][j].setCursor(c);
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
