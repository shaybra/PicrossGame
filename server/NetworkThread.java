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
                    int minutes = Integer.parseInt(args[26]);
                    int seconds = Integer.parseInt(args[27]);
                    time = minutes + ":" + seconds;
                    boolean isThere = false;
                    for (String s : scoreBoard)
                        if (s.contains(clientName)) {
                            isThere = true;
                            String[] temp = s.split(" ");
                            int oldMinutes = Integer.parseInt(temp[2].split(":")[0]);
                            int oldSeconds = Integer.parseInt(temp[2].split(":")[1]);
                            int oldScore = Integer.parseInt(temp[1]);
                            if (score > oldScore) {
                                scoreBoard.remove(s);
                                scoreBoard.add(new String(clientName + " " + score + " " + time));
                            } else if (score == oldScore) {
                                if (minutes < oldMinutes) {
                                    scoreBoard.remove(s);
                                    scoreBoard.add(new String(clientName + " " + score + " " + time));
                                } else if (minutes == oldMinutes) {
                                    if (seconds < oldSeconds) {
                                        scoreBoard.remove(s);
                                        scoreBoard.add(new String(clientName + " " + score + " " + time));
                                    }
                                }
                            }
                        }
                    if (!isThere)
                        scoreBoard.add(clientName + " " + score + " " + time);

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
    public synchronized void broadcastMessage(String messageToSend) {
        try{
        if (!clients.isEmpty())
            for (NetworkThread networkThread : clients) {
                if (!networkThread.clientName.equals(clientName))
                    networkThread.out.println(messageToSend);
            }
        } catch (IOException e) {
            closeAll();
        }
    }

    /**
     * 
     */
    public synchronized void removeNetworkThread() {
        try{
            broadcastMessage("Server: " + clientName + " has disconnected!");
        } catch(IOException e){
            closeAll();
        }
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
