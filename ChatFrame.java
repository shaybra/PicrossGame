import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;

public class ChatFrame extends JFrame {
    private JTextField input = new JTextField();
    private JButton send = new JButton("Send");
    private JTextArea output = new JTextArea("Welcome to the chat room!\n");
    private JPanel textPanel = new JPanel();
    private JPanel outputPanel = new JPanel();

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

    public JTextField getInput() {
        return input;
    }

    public JButton getSend() {
        return send;
    }

    public JTextArea getOutput() {
        return output;
    }
}
