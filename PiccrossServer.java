/*
* Name: Mohammed Chabaan and Garrick Weiler
* Due Date: April 17th, 2022
* Class: PiccrossServer.java
* Proffesor: Daniel Cormier
*/
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import server.NetworkThread;

/**
 * PiccrossServer is the main class for the server.
 */
public class PiccrossServer {
    /**
     * The server socket.
     */
    private ServerSocket serverSocket;

    /**
     * PiccrossServer constructor.
     * 
     * @param port the port to host the server on
     */
    public PiccrossServer(int port) {
        try {
            serverSocket = new ServerSocket(port);
            if (port == 61001) //lets server know its using the default port
                System.out.println("Using default port: " + port);
            else //lets server know its using a specified port
                System.out.println("Using port: " + port);
            System.out.println("Now listening to port:" + port);
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    /**
     * Starts the server and listens for connections. When a connection is made it
     * makes a new thread for the client handler.
     */
    public void startServer() {
        if (serverSocket == null) // the server socket could not be opened
            return;
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                NetworkThread networkThread = new NetworkThread(socket);
                Thread thread = new Thread(networkThread);
                thread.start();
            }
        } catch (IOException e) {
        }
    }

    /**
     * The main method for the server.
     *
     * @param args the port to host the server on
     */
    public static void main(String[] args) {
        int port = 61001; // default port
        if (args.length != 0) { //determines the port the server is to connect to and starts the server
            try {
                int requested = Integer.parseInt(args[0]);
                if (requested > 0 && requested < 65536) // will set server to specified
                    port = requested;
                else // will set server to default
                    System.out.println("ERROR: Invalid port number: " + args[0]);
            } catch (NumberFormatException e) { // will set server to default
                System.out.println("ERROR: Invalid port number: " + args[0]);
            }
        }
        PiccrossServer server = new PiccrossServer(port);
        server.startServer();
    }
}
