package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.SocketTimeoutException;

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
                    chat.updateChat("Connection successful\nWelcome to our server!\nEnter'/help' for commands\n");
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
     * 
     * @throws InterruptedException
     * @throws InvocationTargetException
     */
    public synchronized void receiveMessage(ChatFrame chat, Model model)
            throws InvocationTargetException, InterruptedException {
        while (true) {
            try {
                recievedMessage = bf.readLine();
            } catch (SocketTimeoutException se) {
                continue;
            } catch (IOException e) {
                continue;
            }
            if (!recievedMessage.isEmpty()) {
                if (!recievedMessage.startsWith("`")) {
                    SwingUtilities.invokeAndWait(new Runnable() {
                        @Override
                        public void run() {
                            chat.updateChat(recievedMessage + '\n');
                        }
                    });
                } else {
                    boolean[][] board = new boolean[5][5];
                    String command = recievedMessage.substring(1);
                    String args[] = command.split(",");
                    for (int i = 0; i < 5; i++)
                        for (int j = 0; j < 5; j++)
                            if (args[i * 5 + j].equals("true"))
                                board[i][j] = true;
                            else
                                board[i][j] = false;
                    model.setBoard(board);
                }
            }
        }
    }

    public void sendGame(GameObject game) {
        out.println(game.toString());
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
