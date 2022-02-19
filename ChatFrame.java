import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;

/**
 * ChatFrame is a JFrame that contains a JTextArea to display messages and a
 * JTextField to enter messages.
 */
public class ChatFrame extends JFrame {
    /**
     * JTextField to enter messages.
     */
    private JTextField input = new JTextField();
    /**
     * JButton to send messages.
     */
    private JButton send = new JButton("Send");
    /**
     * The JTextArea to display messages.
     */
    private JTextArea output = new JTextArea("Welcome to the chat room!\n");
    /**
     * JPanel to hold the JTextField and JButton.
     */
    private JPanel textPanel = new JPanel();
    /**
     * JPanel to hold the JTextArea.
     */
    private JPanel outputPanel = new JPanel();

    /**
     * Constructor for ChatFrame.
     */
    ChatFrame() {
        super("Chat");
        setLayout(new BorderLayout());
        input.setColumns(15);
        output.setEditable(false);
        output.setLineWrap(true);
        output.setWrapStyleWord(true);
        output.setColumns(20);
        output.setBackground(Color.BLACK);
        output.setForeground(Color.WHITE);
        textPanel.add(input);
        textPanel.add(send);
        outputPanel.setLayout(new BorderLayout());
        outputPanel.add(new JScrollPane(output), BorderLayout.CENTER);
        outputPanel.setBackground(Color.BLACK);
        add(outputPanel, BorderLayout.CENTER);
        add(textPanel, BorderLayout.SOUTH);
    }

    /**
     * Getter for the input JTextField.
     * @return {@link #input}
     */
    public JTextField getInput() {
        return input;
    }

    /**
     * Getter for the send JButton.
     * @return {@link #send}
     */
    public JButton getSend() {
        return send;
    }

    /**
     * Getter for the output JTextArea.
     * @return {@link #output}
     */
    public JTextArea getOutput() {
        return output;
    }
}
