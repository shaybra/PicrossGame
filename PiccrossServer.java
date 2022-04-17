
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
            if (port == 61001)
                System.out.println("Using default port: " + port);
            else
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
        if (args.length == 0 || Integer.parseInt(args[0]) <= 0 || Integer.parseInt(args[0]) >= 65536) {
            if (args.length != 0)
                System.out.println("ERROR: Invalid port number: " + args[0]);
            PiccrossServer server = new PiccrossServer(61001);
            server.startServer();
        } else {
            PiccrossServer server = new PiccrossServer(Integer.parseInt(args[0]));
            server.startServer();
        }
    }
}
