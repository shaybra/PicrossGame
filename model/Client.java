package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.SocketTimeoutException;

import javax.swing.SwingUtilities;

import controller.Controller;
import view.ChatFrame;

/**
 * 
 */
public class Client {
    /**
     * Holds Socket Information for the Client
     */
    private Socket socket;
    /**
     * Holds BufferedReader Information for the Client (Input)
     */
    private BufferedReader bf;
    /**
     * Holds PrintWriter Information for the Client (Output)
     */
    private PrintWriter out;
    /**
     * Any incoming message is stored in the String to be held and used briefly to send to the chat box
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
        try{
        if (out != null) {
            if ("/bye".equals(messageToSend))
                disconnect(chat);
            else
                out.println(messageToSend);
        }
        }catch (IOException e){
            closeAll();
        }
    }

    /**
     * 
     * @throws InterruptedException
     * @throws InvocationTargetException
     */
    public synchronized void receiveMessage(ChatFrame chat, Model model, Controller controller)
            throws InvocationTargetException, InterruptedException {
        while (true) {
            try {
                recievedMessage = bf.readLine();
            } catch (SocketTimeoutException se) {
                continue;
            } catch (IOException e) {
                closeAll();
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
                    controller.reset(true);
                }
            }
        }
    }

    public void sendGame(GameObject game) {
        try{
        out.println(game.toString());
        } catch (IOException e) {
            closeAll();
        }
    }

    /**
     * 
     */
    public void disconnect(ChatFrame chat) {
        try{
        out.println("/bye");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                chat.updateChat("You Disconnected\n");
            }
        });
        } catch (IOException e) {
        closeAll();
        }
    }

    /**
     * Closes all IO 
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
