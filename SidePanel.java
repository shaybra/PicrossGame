import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Color;

public class SidePanel extends JPanel{
    JLabel hints[][] = new JLabel[5][3];
    
    SidePanel(){
        setLayout(new GridLayout(5,3,5,0));
        for(int i=0;i<5;i++){
            for(int j=0;j<3;j++){
                hints[i][j] = new JLabel("Hint"+"("+(i+1)+","+(j+1)+")");
                hints[i][j].setForeground(new Color(0xFFFFFF));
                add(hints[i][j]);
            }
        }
    }
}
