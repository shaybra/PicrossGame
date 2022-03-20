package view;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import controller.Controller;

/**
 * Menu is the menu bar of the game.
 */
public class Menu extends JMenuBar {
    /**
     * menu is used to add menus to the menu bar.
     */
    private JMenu menu;
    /**
     * subMenu is used to add sub menus.
     */
    private JMenu submenu;
    /**
     * menuItem is used to add menu items.
     */
    private JMenuItem menuItem;

    /**
     * Constructor for the Menu class.
     * @param controller is the controller for the menu items.  
     */
    public Menu(Controller controller) {
        //Game Menu.
        menu = new JMenu("Game");
        menu.getAccessibleContext().setAccessibleDescription("Stuff dealing with the game");
        add(menu);

        //Game Menu Items.
        menuItem = new JMenuItem("New", new ImageIcon("images/New.gif"));
        menuItem.setActionCommand("New");
        menuItem.addActionListener(controller);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("New Game");
        menu.add(menuItem);

        //Debug menu
        submenu = new JMenu("Debug");
        menuItem = new JMenuItem("First senario");
        menuItem.setActionCommand("First senario");
        menuItem.addActionListener(controller);
        submenu.add(menuItem);

        menuItem = new JMenuItem("Second senario");
        menuItem.setActionCommand("Second senario");
        menuItem.addActionListener(controller);
        submenu.add(menuItem);

        menuItem = new JMenuItem("Third senario");
        menuItem.setActionCommand("Third senario");
        menuItem.addActionListener(controller);
        submenu.add(menuItem);

        menu.add(submenu);

        menu.addSeparator();

        // exit menu item
        menuItem = new JMenuItem("Exit", new ImageIcon("images/Exit.gif"));
        menuItem.setActionCommand("Exit");
        menuItem.getAccessibleContext().setAccessibleDescription("Exit the application");
        menuItem.addActionListener(controller);
        menu.add(menuItem);

        //Help menu.
        menu = new JMenu("Help");
        menu.getAccessibleContext().setAccessibleDescription("Help Menu");

        // solution menu item
        menuItem = new JMenuItem("Solution", new ImageIcon("images/Solution.gif"));
        menuItem.setActionCommand("Solution");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription("Show the solution");
        menuItem.addActionListener(controller);
        menu.add(menuItem);

        // about menu item
        menuItem = new JMenuItem("About", new ImageIcon("images/About.gif"));
        menuItem.setActionCommand("About");
        menuItem.getAccessibleContext().setAccessibleDescription("About the application");
        menuItem.addActionListener(controller);
        menu.add(menuItem);

        add(menu);
    }
}
