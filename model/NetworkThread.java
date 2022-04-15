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

    private Socket socket;

    private Scanner in;
    private PrintWriter out;

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
                this.in = new Scanner(socket.getInputStream());
                this.out = new PrintWriter(socket.getOutputStream(), true);
                if (in.hasNextLine())
                    this.clientName = in.nextLine();
                clients.add(this);
                broadcastMessage(clientName + " has entered the chat!");
            }
        } catch (IOException e) {
            closeAll();
        }
    }

    @Override
    /**
     * Runnable method for the thread.
     */
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
                        if (in.hasNextLine()) {
                            String oldName = clientName;
                            clientName = in.nextLine();
                            broadcastMessage(oldName + " has changed their name to " + clientName);
                        }
                        break;
                    case "/who":
                        listAllUsers();
                        break;
                    case "/help":
                        out.print("/help - displays this message\n");
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
            return;
        }
    }

    public synchronized void listAllUsers() {
        for (NetworkThread client : clients) {
            out.println(client.clientName);
        }
    }

    public synchronized void broadcastMessage(String messageToSend) throws IOException {
        if (!clients.isEmpty())
            for (NetworkThread networkThread : clients) {
                if (networkThread != null && networkThread.clientName != null)
                    if (!networkThread.clientName.equals(clientName)) {
                        networkThread.out.println(messageToSend);
                        networkThread.out.println();
                        networkThread.out.flush();
                    }
            }
    }

    public void removeNetworkThread() throws IOException {
        broadcastMessage("Server: " + clientName + " has disconnected!");
        closeAll();
        clients.remove(this);
    }

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
