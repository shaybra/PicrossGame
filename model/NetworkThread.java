package model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * 
 */
public class NetworkThread implements Runnable {
    /**
     * Socket for the client.
     */
    public static ArrayList<NetworkThread> clients = new ArrayList<>();

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
            this.socket = socket;
            this.in = new Scanner(socket.getInputStream());
            this.out = new PrintWriter(socket.getOutputStream(), true);
            if (in.hasNextLine())
                this.clientName = in.nextLine();
            clients.add(this);
            broadcastMessage(clientName + "has entered the chat!");
        } catch (IOException e) {
            closeAll(socket, in, out);
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
                // if (messageFromClient.trim().charAt(0) == '/')
                // switch (messageFromClient.trim()) {
                // case "bye":
                // closeAll(socket, in, out);
                // break;
                // case "help":
                // break;
                // case "name":
                // break;
                // case "who":
                // break;
                // default:
                // break;
                // }
                // else
                System.out.println("Message: " + messageFromClient);
                out.println("Hey there!" + messageFromClient);
                broadcastMessage(messageFromClient);
            }
        } catch (IOException e) {
            closeAll(socket, in, out);
        }
    }

    public synchronized void broadcastMessage(String messageToSend) throws IOException {
        for (NetworkThread networkThread : clients) {
            if (!networkThread.clientName.equals(clientName)) {
                networkThread.out.write(messageToSend);
                networkThread.out.println();
                networkThread.out.flush();
            }
        }
    }

    public synchronized void removeNetworkThread() throws IOException {
        clients.remove(this);
        broadcastMessage("Server: " + clientName + " has disconnected!");
    }

    public void closeAll(Socket socket, Scanner in, PrintWriter out) {
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
