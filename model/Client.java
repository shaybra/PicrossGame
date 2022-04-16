package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

import javax.swing.SwingUtilities;

import view.ChatFrame;

/**
 * 
 */
public class Client {
    /**
     * 
     */
    private Socket socket;
    /**
     * 
     */
    private BufferedReader bf;
    /**
     * 
     */
    private PrintWriter out;
    /**
     * 
     */
    private String recievedMessage;

    /**
     * 
     * @param socket
     * @param username
     */
    public Client(Socket socket, String username, ChatFrame chat) {
        try {
            this.socket = socket;
            bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    chat.updateChat("Connection successful\n");
                }
            });
            out.println(username);
        } catch (IOException e) {
            closeAll();
        }
    }

    /**
     * 
     * @param messageToSend
     * @param chat
     */
    public void sendMessage(String messageToSend, ChatFrame chat) {
        if (out != null) {
            if ("/bye".equals(messageToSend))
                disconnect(chat);
            else
                out.println(messageToSend);
        }
    }

    /**
     * @throws InterruptedException
     * @throws InvocationTargetException
     * 
     */
    public void receiveMessage(ChatFrame chat) throws InvocationTargetException, InterruptedException {
        while (true) {
            try {
                recievedMessage = bf.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!recievedMessage.isEmpty()) {
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        chat.updateChat(recievedMessage + '\n');
                    }
                });

            }
        }
    }

    /**
     * 
     */
    public void disconnect(ChatFrame chat) {
        out.println("/bye");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                chat.updateChat("You Disconnected\n");
            }
        });
        closeAll();
    }

    /**
     * 
     */
    public void closeAll() {
        try {
            if (bf != null) {
                bf.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
