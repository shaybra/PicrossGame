/*
* Name: Mohammed Chabaan and Garrick Weiler
* Due Date: March 20th, 2022
* Class: Frame.java
* Proffesor: Daniel Cormier
*/
package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controller.Controller;

/**
 * Frame is a JFrame that contains the content of the game.
 */
public class Frame extends JFrame {
    /**
     * topPanel is a JPanel that contains the contents of the top panel.
     * @see TopPanel
     */
    private TopPanel topPanel = new TopPanel();
    /**
     * footerPanel is a JPanel that contains the contents of the footer panel.
     * @see FooterPanel
     */
    private FooterPanel footerPanel = new FooterPanel();
    /**
     * gridPanel is a JPanel that contains the contents of the grid panel.
     * @see GridPanel
     */
    private GridPanel gridPanel = new GridPanel();
    /**
     * sidePanel is a JPanel that contains the contents of the side panel.
     * @see SidePanel
     */
    private SidePanel sidePanel = new SidePanel();
    /**
     * controller is a Controller object.
     * @see Controller
     */
    private Controller controller = new Controller(this);
    /**
     * Menu is the menu bar of the game.
     * @see Menu
     */
    private Menu menu = new Menu(controller);
    /**
     * Chat of the game.
     */
    private ChatFrame chat = new ChatFrame(controller);
    

    /**
     * Constructor for the Frame class.
     */
    public Frame() {
        super("Picross");
        setSize(new Dimension(920, 920));
        setLayout(new BorderLayout());

        setJMenuBar(menu);
        
        // center frame
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(d.width / 2 - getSize().width / 2, d.height / 2 - getSize().height / 2);

        // add action listeners
        footerPanel.addListener(controller);
        gridPanel.addListener(controller);

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
        pack();
    }

    /**
     * Shows the about Dialog.
     */
    public void showAboutDialog(){
        JOptionPane.showMessageDialog(this, "Picross\n\n" +
                "By: \n" +
                "Mohamad Chaaban\n"+
                "Garrick Weiler\n\n"+
                "Winter term 2022", "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Menu getter.
     * @return {@link #menu}
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     * Shows the game over, perfect dialog.
     * @return 0 if the yes button was clicked -1 if the close or no button was clicked.
     */
    public int perfectGame(){
       return JOptionPane.showConfirmDialog(this, new ImageIcon("images/PerfectGame.png"), "Winner!",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Shows the game over.
     * @return 0 if the yes button was clicked -1 if the close or no button was clicked.
     */
    public int gameOver(){ 
        return JOptionPane.showConfirmDialog(this, new ImageIcon("images/GameOver.png"), "Game Over",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Getter for the topPanel.
     * @return {@link #topPanel}
     */
    public TopPanel getTopPanel() {
        return topPanel;
    }

    /**
     * Getter for the chat.
     * @return {@link #chat}
     */
    public ChatFrame getChat() {
        return chat;
    }

    /**
     * Getter for the footerPanel.
     * @return {@link #footerPanel}
     */
    public FooterPanel getFooterPanel() {
        return footerPanel;
    }

    /**
     * Getter for the gridPanel.
     * @return {@link #gridPanel}
     */
    public GridPanel getGridPanel() {
        return gridPanel;
    }

    /**
     * Getter for the sidePanel.
     * @return {@link #sidePanel}
     */
    public SidePanel getSidePanel() {
        return sidePanel;
    }
}
