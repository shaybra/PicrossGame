/*
* Name: Mohammed Chabaan and Garrick Weiler
* Due Date: April 17th, 2022
* Class: NetworkThread.java
* Proffesor: Daniel Cormier
*/
package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

/**
 * Network handler for the server to handle a client.
 */
public class NetworkThread implements Runnable {
    /**
     * All the clients that are connected to the server.
     */
    private static Vector<NetworkThread> clients = new Vector<NetworkThread>();
    /**
     * The Current challenege that is being played.
     */
    private static String currentBoard = new String();
    /**
     * The score board of the current challenge.
     */
    private static Vector<String> scoreBoard = new Vector<String>();
    /**
     * The socket of the client.
     */
    private Socket socket;
    /**
     * input from the client.
     */
    private Scanner in;
    /**
     * output to the client.
     */
    private PrintWriter out;
    /**
     * The client's name.
     */
    private String clientName;

    /**
     * Constructor for the NetworkThread class.
     * 
     * @param socket the socket that the thread will use to communicate with the
     *               server.
     */
    public NetworkThread(Socket socket) {
        try {
            if (socket != null) {
                this.socket = socket;
                in = new Scanner(socket.getInputStream());
                out = new PrintWriter(socket.getOutputStream(), true);
                if (in.hasNextLine())
                    this.clientName = in.nextLine();
                clients.add(this);
                if (clientName != null) {
                    broadcastMessage(clientName + " has connected.");
                    System.out.println(clientName + " has connected.");
                }
            }
        } catch (IOException e) {
            closeAll();
        }
    }

    /**
     * Runnable method for the thread.
     */
    @Override
    public synchronized void run() {
        String messageFromClient;
        try {
            while (socket.isConnected() && in.hasNextLine()) {
                messageFromClient = in.nextLine();
                if (messageFromClient.startsWith("`")) {
                    String command = messageFromClient.substring(1);
                    String args[] = command.split(",");
                    String incominBoard = new String();
                    for (int i = 0; i < 25; i++)
                        incominBoard += i != 24 ? args[i] + "," : args[i];
                    if (!incominBoard.equals(currentBoard)) {
                        currentBoard = incominBoard;
                        scoreBoard = new Vector<String>();
                        broadcastMessage("Server: " + clientName + " has sent a board.");
                    }
                    int score = Integer.parseInt(args[25]);
                    int minutes = Integer.parseInt(args[26]);
                    int seconds = Integer.parseInt(args[27]);
                    int timeComp = (minutes * 60) + seconds;
                    String time = minutes + ":" + seconds;
                    boolean isThere = false;
                    for (String s : scoreBoard)
                        if (s.contains(clientName)) {
                            isThere = true;
                            String[] temp = s.split(" ");
                            int oldMinutes = Integer.parseInt(temp[2].split(":")[0]);
                            int oldSeconds = Integer.parseInt(temp[2].split(":")[1]);
                            int oldTimeComp = (oldMinutes * 60) + oldSeconds;
                            int oldScore = Integer.parseInt(temp[1]);
                            if (score > oldScore) {
                                scoreBoard.remove(s);
                                scoreBoard.add(new String(clientName + " " + score + " " + time));
                            } else if (score == oldScore) {
                                if (timeComp < oldTimeComp) {
                                    scoreBoard.remove(s);
                                    scoreBoard.add(new String(clientName + " " + score + " " + time));
                                }
                            }
                        }
                    if (!isThere)
                        scoreBoard.add(clientName + " " + score + " " + time);
                    if (!scoreBoard.isEmpty())
                        for (int i = 0; i < scoreBoard.size() - 1; i++)
                            for (int j = 0; j < scoreBoard.size() - 1 - i; j++) {
                                String[] temp1 = scoreBoard.get(j).split(" ");
                                int score1 = Integer.parseInt(temp1[1]);
                                String[] temp2 = scoreBoard.get(j + 1).split(" ");
                                int score2 = Integer.parseInt(temp2[1]);
                                if (score1 < score2) {
                                    String temp = scoreBoard.get(j);
                                    scoreBoard.set(j, scoreBoard.get(j + 1));
                                    scoreBoard.set(j + 1, temp);
                                } else if (score1 == score2) {
                                    String[] temp1Time = temp1[2].split(":");
                                    int minutes1 = Integer.parseInt(temp1Time[0]);
                                    int seconds1 = Integer.parseInt(temp1Time[1]);
                                    int timeComp1 = (minutes1 * 60) + seconds1;
                                    String[] temp2Time = temp2[2].split(":");
                                    int minutes2 = Integer.parseInt(temp2Time[0]);
                                    int seconds2 = Integer.parseInt(temp2Time[1]);
                                    int timeComp2 = (minutes2 * 60) + seconds2;
                                    if (timeComp1 > timeComp2) {
                                        String temp = scoreBoard.get(j);
                                        scoreBoard.set(j, scoreBoard.get(j + 1));
                                        scoreBoard.set(j + 1, temp);
                                    }
                                }
                            }
                } else
                    switch (messageFromClient) {
                        case "/bye":
                            removeNetworkThread();
                            break;
                        case "/name":
                            out.println("Enter your new name: ");
                            if (in.hasNextLine()) {
                                broadcastMessage(clientName);
                                System.out.print(clientName);
                                clientName = in.nextLine();
                                broadcastMessage(" has changed their name to " + clientName);
                                System.out.println(" has changed their name to " + clientName);
                                out.println("Your name has been changed to " + clientName);
                            }
                            break;
                        case "/who":
                            listAllUsers();
                            break;
                        case "/get":
                            out.println("`" + currentBoard);
                            out.println("PLAYER TIME SCORE\n====================\n");
                            for (String s : scoreBoard)
                                out.println(s);
                            break;
                        case "/help":
                            out.println("/help - displays this message");
                            out.println("/name - change your name");
                            out.println("/who - list all users");
                            out.println("/get - get the current challenge");
                            out.println("/bye - exit the chat");
                            break;
                        default:
                            broadcastMessage(clientName + ": " + messageFromClient);
                            break;
                    }
            }
        } catch (IllegalStateException e) {
            closeAll();
        }
    }

    /**
     * Listst all the users in the chat.
     */
    public synchronized void listAllUsers() {
        for (NetworkThread client : clients) {
            if (client != this)
                out.println(client.clientName);
            else {
                out.println(client.clientName + "(you)");
            }
        }
    }

    /**
     * broadcaststs a message to all the users connected to the chat.
     * 
     * @param message the message to be broadcasted.
     */
    public synchronized void broadcastMessage(String messageToSend) {
        if (!clients.isEmpty())
            for (NetworkThread networkThread : clients) {
                if (!networkThread.clientName.equals(clientName))
                    networkThread.out.println(messageToSend);
            }
    }

    /**
     * Removes the current thread(client) from the list of connected clients. Also
     * closes all the streams and sockets.
     */
    public synchronized void removeNetworkThread() {
        broadcastMessage("Server: " + clientName + " has disconnected!");
        System.out.println(clientName + " has disconnected!");
        closeAll();
        clients.remove(this);
    }

    /**
     * Closes all the streams and sockets.
     */
    public void closeAll() {
        try {
            if (in != null) {
                in.close();
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
