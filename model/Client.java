/*
* Name: Mohammed Chabaan and Garrick Weiler
* Due Date: April 17th, 2022
* Class: Client.java
* Proffesor: Daniel Cormier
*/
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
import view.Menu;

/**
 * Client is the class that handels network traffic for the client.
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
     * Any incoming message is stored in the String to be held and used briefly to
     * send to the chat box
     */
    private String recievedMessage;

    /**
     * Client constructor.
     * 
     * @param socket    Socket to be used for the client
     * @param username  the usernamme of the client
     * @param ChatFrame the chat frame of the user where we update the chat box
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
     * Sends a message to the server.
     * 
     * @param messageToSend the message to be sent to the server
     * @param chat          the chat frame where we update the chat box
     */
    public void sendMessage(String messageToSend, ChatFrame chat, Menu menu) {
        if (out != null) {
            if ("/bye".equals(messageToSend))
                disconnect(chat, menu);
            else
                out.println(messageToSend);
        }
    }

    /**
     * Listens for incoming messages from the server. If the message recived starts
     * with the prefix '`' then it is seen as a game board, else it is seen as a
     * chat message.
     * 
     * @param chat       the chat frame where we update the chat box
     * @param model      the model of the game
     * @param controller the controller of the game
     * 
     * @throws InterruptedException
     * @throws InvocationTargetException
     */
    public synchronized void receiveMessage(ChatFrame chat, Model model, Controller controller)
            throws InvocationTargetException, InterruptedException {
        while (true) { //Constantly checking for messages on the Input stream, reading the bytes of data and the sending that data to the chat UI
            try {
                recievedMessage = bf.readLine();
            } catch (SocketTimeoutException se) {
                continue;
            } catch (IOException e) {
                closeAll();
            }
            if (!recievedMessage.isEmpty()) {
                if (!recievedMessage.startsWith("`")) {
                    SwingUtilities.invokeAndWait(new Runnable() { //blocks each message and sends it before moving to the next byte
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

    /**
     * Sends the gameboard as a string to the server.
     * 
     * @param game the gameboard to be sent
     */
    public void sendGame(GameObject game) {
        out.println(game.toString());
    }

    /**
     * Disconnects the client from the server. And updates the chat box.
     * 
     * @param chat the chat frame where we update the chat box
     */
    public void disconnect(ChatFrame chat, Menu menu) {
        out.println("/bye");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                chat.updateChat("You Disconnected\n");
                menu.connected(false); //set connection menu back to no connection
            }
        });
    }

    /**
     * Closes the BufferedReader, PrintWriter and Socket.
     */
    public void closeAll() {
        try {
            if (bf != null) {
                bf.close(); //closes BufferedReader
            }
            if (out != null) {
                out.close(); //closes PrintWriter
            }
            if (socket != null) {
                socket.close(); //closes socket and associated IO streams
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
