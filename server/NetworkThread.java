package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private ObjectInputStream in;
    /**
     * 
     */
    private ObjectOutputStream out;
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
                in = new ObjectInputStream(socket.getInputStream());
                out = new ObjectOutputStream(socket.getOutputStream());
                clientName = (String) in.readObject();
                clients.add(this);
                if (clientName != null) {
                    broadcastMessage(clientName + " has connected.");
                    System.out.println(clientName + " has connected.");
                }
            }
        } catch (IOException e) {
            closeAll();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Runnable method for the thread.
     */
    @Override
    public void run() {
        String messageFromClient = new String();
        try {
            while (socket.isConnected()) {
                if (in.available() != 0)
                    try {
                        messageFromClient = (String) in.readObject();
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    }
                switch (messageFromClient) {
                    case "/bye":
                        removeNetworkThread();
                        break;
                    case "/name":
                        out.writeObject("Enter your new name: ");
                        String oldName = clientName;
                        try {
                            clientName = (String) in.readObject();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        broadcastMessage(oldName + " has changed their name to " + clientName);
                        System.out.println(oldName + " has changed their name to " + clientName);
                        out.writeObject("Your name has been changed to " + clientName);
                        break;
                    case "/who":
                        listAllUsers();
                        break;
                    case "/help":
                        out.writeObject("/help - displays this message");
                        out.writeObject("/name - change your name");
                        out.writeObject("/who - list all users");
                        out.writeObject("/bye - exit the chat");
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
                try {
                    out.writeObject(client.clientName);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            else {
                try {
                    out.writeObject(client.clientName + "(you)");
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
                    networkThread.out.writeObject(messageToSend);
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
