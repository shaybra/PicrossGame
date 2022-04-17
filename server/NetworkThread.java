package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

/**
 * 
 */
public class NetworkThread implements Runnable {
    /**
     * Socket for the client.
     */
    private static Vector<NetworkThread> clients = new Vector<NetworkThread>();
    private static String currentBoard = new String();
    private static Vector<String> scoreBoard = new Vector<String>();
    /**
     * 
     */
    private Socket socket;
    /**
     * 
     */
    private Scanner in;
    /**
     * 
     */
    private PrintWriter out;
    /**
     * 
     */
    private String clientName;
    private int score;
    private String time;

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
                        scoreBoard.add(new String("PLAYER TIME SCORE\n====================\n"));
                        broadcastMessage("Server: " + clientName + " has sent a board.");
                    }
                    score = Integer.parseInt(args[25]);
                    time = args[26];
                    boolean isThere = false;
                    for (String s : scoreBoard)
                        if (s.contains(clientName)) {
                            isThere = true;
                            int oldScore = Integer.parseInt(scoreBoard.get(scoreBoard.size() - 1).split(" ")[2]);
                            String oldTime = scoreBoard.get(scoreBoard.size() - 1).split(" ")[3];
                            if (score > oldScore || time.compareTo(oldTime) < 0) {
                                scoreBoard.remove(scoreBoard.size() - 1);
                                scoreBoard.add(new String(clientName + " " + time + " " + score));
                            }
                        }
                    if (isThere)
                        scoreBoard.add(clientName + " " + score + " " + time + "\n");

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
                            out.println(scoreBoard);
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
        } catch (IOException e) {
            closeAll();
        } catch (IllegalStateException e) {
            closeAll();
        }
    }

    /**
     * 
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
     * 
     */
    public synchronized void broadcastMessage(String messageToSend) throws IOException {
        if (!clients.isEmpty())
            for (NetworkThread networkThread : clients) {
                if (!networkThread.clientName.equals(clientName))
                    networkThread.out.println(messageToSend);
            }
    }

    /**
     * 
     */
    public synchronized void removeNetworkThread() throws IOException {
        broadcastMessage("Server: " + clientName + " has disconnected!");
        System.out.println(clientName + " has disconnected!");
        closeAll();
        clients.remove(this);
    }

    /**
     * 
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
