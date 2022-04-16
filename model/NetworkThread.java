package model;

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
    public static Vector<NetworkThread> clients = new Vector<NetworkThread>();
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
    public void run() {
        String messageFromClient;
        try {
            while (socket.isConnected() && in.hasNextLine()) {
                messageFromClient = in.nextLine();
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
                        }
                        break;
                    case "/who":
                        listAllUsers();
                        break;
                    case "/help":
                        out.println("/help - displays this message");
                        out.println("/name - change your name");
                        out.println("/who - list all users");
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
