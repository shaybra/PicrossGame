/*###############################################
# PiccrossNetworkModalVC (ViewController)
# by Daniel Cormier, for CST8221 Assignment 4
#
# This will produce a dialog which is modal that
# will force the user to input the required particulars
# for a network connection.
#################################################*/

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * PiccrossNetworkModalVC creates a basic UI to get user input for a network
 * connection.
 * 
 * @author Daniel Cormier
 * @version 1.1
 * @since 1.8.0_291
 * @see PiccrossView
 **/

public class PiccrossNetworkModalVC extends JDialog {
    /** Has the user pressed connect or cancel? */
    private Boolean hasConnected = false;

    /** Internal Controller reference for managing input. */
    private Controller handler = new Controller();

    /** Will be used by the user to select desired port. */
    private JComboBox portInput;

    /** Textfields for inputting desired address and user name. */
    private JTextField addressInput, nameInput;

    /** Labels for feedback regarding name, port and address errors respectively. */
    private JLabel nameError, portError, addrError;

    /**
     * This constructor requires a reference to the JFrame it will "interrupt".
     * 
     * @param mainView the JFrame this basic UI will sit over. Generally your "view"
     *                 class.
     **/
    public PiccrossNetworkModalVC(JFrame mainView) {
        super(mainView, "Enter Network Address", true);

        // If you are changing how this UI looks or operates, I strongly encourage
        // you to set this to "false" or just comment the line out until you are done.
        setUndecorated(true);

        // An "undecorated" modal has no practical means of closing it other than code,
        // so make sure it works right before flipping it back on again.

        // Setting up the UI here.
        Container networkPanel = getContentPane();
        setLayout(new FlowLayout(0, 0, 0));
        JPanel innerPanel = new JPanel();
        JPanel outerPanel = new JPanel();
        innerPanel.setLayout(new GridBagLayout());
        outerPanel.setLayout(new FlowLayout(0));

        // My gridbag contraints object, which will get heavy use shortly.
        GridBagConstraints straints = new GridBagConstraints();

        addressInput = new JTextField(20);
        nameInput = new JTextField(20);

        // Arranged this way, the JComboBox starts with a blank entry
        // and the next line permits the user to enter their own value.
        portInput = new JComboBox<String>(new String[] { "", "32150", "42150", "52150" });
        portInput.setEditable(true);

        // A mnemmonic is a shortcut key that a user can press to quickly access other
        // parts of the UI. This is how you set a shortcut key for something like a text
        // area.

        // You set the mnemonic on the label itself, and you "setLabelFor" the textarea
        // (or other item) you wish to access by keystroke. Now, alt+A will access my
        // "address"
        // textfield!
        JLabel address = new JLabel("Address: ");
        address.setLabelFor(addressInput);
        address.setDisplayedMnemonic('A');

        JLabel port = new JLabel("Port: ");
        port.setDisplayedMnemonic('P');
        port.setLabelFor(portInput);

        JLabel name = new JLabel("Name: ");
        name.setDisplayedMnemonic('N');
        name.setLabelFor(nameInput);

        // I wanted the labels to actually take up some area, so setting them blank
        // like this will do the trick.
        nameError = new JLabel(" ");
        portError = new JLabel(" ");
        addrError = new JLabel(" ");

        // It would be a good practice to set some mnemonics on these buttons.
        // You should do so. I did not. You'll get one extra mark if you do.
        // Are you reading these comments?
        JButton connect = new JButton("Connect");
        JButton cancel = new JButton("Cancel");
        connect.addActionListener(handler);
        cancel.addActionListener(handler);

        connect.setActionCommand("C");
        cancel.setActionCommand("X");

        // Now actually assembling the UI.
        straints.anchor = GridBagConstraints.EAST;
        straints.gridx = 0;
        straints.gridy = 0;
        innerPanel.add(address, straints);

        straints.gridy = 2;
        innerPanel.add(port, straints);

        straints.gridy = 4;
        innerPanel.add(name, straints);

        straints.gridy = 6;
        straints.gridx = 2;
        innerPanel.add(connect, straints);

        straints.gridx = 3;
        innerPanel.add(cancel, straints);

        straints.gridx = 1;
        straints.gridy = 0;
        straints.gridwidth = 3;
        innerPanel.add(addressInput, straints);

        straints.gridy = 2;
        straints.gridwidth = 2;
        innerPanel.add(portInput, straints);

        straints.gridy = 4;
        straints.gridwidth = 3;
        innerPanel.add(nameInput, straints);

        straints.anchor = GridBagConstraints.WEST;
        straints.gridy = 1;
        innerPanel.add(addrError, straints);

        straints.gridy = 3;
        innerPanel.add(portError, straints);

        straints.gridy = 5;
        innerPanel.add(nameError, straints);

        // Spacing! Five pixels between each category with error message.
        // Remember that these parameters are top, left, bottom, right.
        addrError.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        nameError.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        portError.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // Five pixels of padding on the whole thing.
        innerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // error messages should be in red.
        addrError.setForeground(Color.RED);
        nameError.setForeground(Color.RED);
        portError.setForeground(Color.RED);

        // The dialog itself needs a border to help separate it from the normal game UI.
        outerPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLUE));

        outerPanel.add(innerPanel);
        networkPanel.add(outerPanel);

        // When assembling a UI with any layout manager, the last statement should be
        // "pack".
        pack();
    }

    /**
     * Getter method to return the value of the network address.
     * 
     * @return Address as typed by the user. Could be IPV4 or FQDN address.
     */
    public String getAddress() {
        return (addressInput.getText());
    }

    /**
     * Getter method to return the value of the user's name as-typed.
     * 
     * @return User's name. Will be at least three characters long.
     */
    public String getName() {
        return (nameInput.getText());
    }

    /**
     * Getter method to return the value of the port the user selected in the
     * JComboBox.
     * 
     * @return This integer will be between 10,000 and 65535. Negative values
     *         indicate input error.
     */
    public int getPort() {
        // If this method returns negative values, the user can not hit "connect" to
        // continue.
        // You don't strictly have to test for it yourself but being safe isn't a bad
        // policy.
        int portnum = -1;
        if (hasConnected) {
            try {
                portnum = Integer.parseInt((String) portInput.getSelectedItem());
            } catch (NumberFormatException nfe) {
                portnum = -1;
            }
            if (portnum > 9999 && portnum < 65536) {
                return portnum;
            } else
                return -2;
        }
        return -1;
    }

    /** Convenience method to permit hiding the UI internally. */
    private void hideModal() {
        // Add any code that you may want to do after the user input has been processed
        // and you're figuratively closing up the shop.

        // As a suggestion, you may want to reset error messages here so the UI starts
        // up clean
        // the next time the user tries to enter input.
        // I've intentionally not done this, because I want you to. It's worth points to
        // you.

        setVisible(false);

    }

    /**
     * Used to test the modal to see how the user exited: Did they press connect or
     * cancel?
     * 
     * @return hasConnected True if "connect" was pressed and the user wants to try
     *         connecting.
     */
    public boolean pressedConnect() {
        return hasConnected;
    }

    /**
     * Inner class to manage user input and test for errors.
     * 
     * @author Daniel Cormier
     * @version 1.1
     * @since 1.8.0_291
     * @see PiccrossNetworkModalVCView
     */
    private class Controller implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            // Flag remains true if all data input tests are passed, permits leaving the
            // modal.
            boolean flag = true;
            String s = evt.getActionCommand();

            // If/else goldmine.
            if ("C".equals(s)) // Connect option.
            {
                hasConnected = true;
                nameError.setText(" ");
                portError.setText(" ");
                addrError.setText(" ");

                int x = getPort();

                if (x == -1) // non-integer input.
                {
                    portError.setText("The port must be an integer.");
                    flag = false;
                } else if (x == -2)// out-of-range input
                {
                    portError.setText("Port must be >10000 and <65535.");
                    flag = false;
                }

                if (getName().length() < 3)// Insufficient name length
                {
                    nameError.setText("Name too short.");
                    flag = false;
                }

                if (getAddress().length() < 1)// Blank addresses. Not much other testing can be done here.
                {
                    addrError.setText("The address must not be blank.");
                    flag = false;
                }
            } else {
                // My "Cancel" button has an action command of "X" and gets called here.
                // Flag will remain true because we wish to allow an exit.
                hasConnected = false;
            }
            // Hide, but do not dispose the dialog. We may want to use it again.
            if (flag)
                hideModal();
        }// end goldmine
    } // end inner class Controller
} // end PNMVC