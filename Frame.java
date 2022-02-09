import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Frame extends JFrame {
    private TopPanel topPanel = new TopPanel();
    private FooterPanel footerPanel = new FooterPanel();
    private GridPanel gridPanel = new GridPanel();
    private SidePanel sidePanel = new SidePanel();
    Controller controller = new Controller(this);

    public Frame() {
        super("Picross");
        setSize(new Dimension(920, 920));
        setLayout(new BorderLayout());

        // center frame
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(d.width / 2 - getSize().width / 2, d.height / 2 - getSize().height / 2);

        // add panels to frame
        add(topPanel, BorderLayout.NORTH);
        add(footerPanel, BorderLayout.SOUTH);
        add(gridPanel, BorderLayout.CENTER);
        add(sidePanel, BorderLayout.WEST);

        // set size of each panel
        topPanel.setPreferredSize(new Dimension(920, 120));
        footerPanel.setPreferredSize(new Dimension(920, 90));
        gridPanel.setPreferredSize(new Dimension(680, 680));
        sidePanel.setPreferredSize(new Dimension(120, 680));

        // set background color of each panel
        topPanel.setBackground(new Color(0x2364C7));
        footerPanel.setBackground(new Color(0x030E34));
        gridPanel.setBackground(new Color(0xAFB6C1));
        sidePanel.setBackground(new Color(0x2364C7));

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public TopPanel getTopPanel() {
        return topPanel;
    }

    public FooterPanel getFooterPanel() {
        return footerPanel;
    }

    public GridPanel getGridPanel() {
        return gridPanel;
    }

    public SidePanel getSidePanel() {
        return sidePanel;
    }
}
