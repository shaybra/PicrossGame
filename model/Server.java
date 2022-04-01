package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Server {
    private Vector<Client> clients;
    // initialize socket and input stream
    private ObjectInputStream in;

    // constructor with port
    public Server(int port) {
        // starts server and waits for a connection
        try {
            ServerSocket server = new ServerSocket(port);
            Client client = null;
            while (true) {
                Socket socket = server.accept();
                client = new Client(socket.getInetAddress().getHostAddress(), socket.getPort());
                clients.add(client);
                Runnable r = new NetworkThread(client);
                Thread t = new Thread(r);
                t.start();
            }
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}
