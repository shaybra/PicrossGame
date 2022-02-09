import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.GridLayout;
import java.awt.Color;

import java.util.Random;
import java.lang.StringBuilder;

public class SidePanel extends JPanel{
    private JLabel hints[][] = new JLabel[5][3];
    private Random rand = new Random();
    
    SidePanel(){
        setLayout(new GridLayout(5,3,5,0));
        for(int i=0;i<5;i++){
            for(int j=0;j<3;j++){
                int number = rand.nextInt(6);
                hints[i][j] = number != 0 ? new JLabel(new StringBuilder().append(number).toString()) : new JLabel();
                hints[i][j].setForeground(new Color(0xFFFFFF));
                hints[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                hints[i][j].setVerticalAlignment(SwingConstants.CENTER);
                add(hints[i][j]);
            }
        }
    }

    public void reset(){
        for(int i=0;i<5;i++)
            for(int j=0;j<3;j++){
                int number = rand.nextInt(6);
                if(number != 0)
                    hints[i][j].setText(new StringBuilder().append(number).toString());
                else
                    hints[i][j].setText("");
            }
    } 
}
