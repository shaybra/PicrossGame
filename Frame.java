import javax.swing.JFrame;

import java.awt.*;

public class Frame extends JFrame{
    private TopPanel topPanel = new TopPanel();
    private FooterPanel footerPanel = new FooterPanel();
    private GridPanel gridPanel = new GridPanel();
    private SidePanel sidePanel = new SidePanel();

    // private JPanel topPanel = new JPanel();
    // private JPanel footerPanel = new JPanel();
    // private JPanel gridPanel = new JPanel();
    // private JPanel sidePanel = new JPanel();

    public Frame(){
        super("Picross");
        setSize(920, 920);
        setLayout(new BorderLayout());
        
        add(topPanel, BorderLayout.NORTH);
        add(footerPanel, BorderLayout.SOUTH);
        add(gridPanel, BorderLayout.CENTER);
        add(sidePanel, BorderLayout.WEST);

        topPanel.setSize(920, 240);
        footerPanel.setSize(920, 180);
        gridPanel.setSize(680, 680);
        sidePanel.setSize(240, 680);

        topPanel.setBackground(new Color(0x2364C7));
        footerPanel.setBackground(new Color(0x030E34));
        gridPanel.setBackground(new Color(0xAFB6C1));
        sidePanel.setBackground(new Color(0x2364C7));
        
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
