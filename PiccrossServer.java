
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import model.NetworkThread;

public class PiccrossServer {
    private Vector<NetworkThread> clients = new Vector<NetworkThread>();

    public static void main(String[] args) {
        if (args.length == 0 || Integer.parseInt(args[0]) <= 0 || Integer.parseInt(args[0]) >= 65536) {
            if (args.length != 0)
                System.out.println("ERROR: Invalid port number: " + args[0]);
            new PiccrossServer(61001);
        } else {
            new PiccrossServer(Integer.parseInt(args[0]));
        }
    }

    // constructor with port
    public PiccrossServer(int port) {
        // starts server and waits for a connection
        try {
            ServerSocket server = new ServerSocket(port);
            if (port == 61001)
                System.out.println("Using default port: " + port);
            else
                System.out.println("Using port: " + port);
            System.out.println("Now listening to port:" + port);
            while (true) {
                Socket socket = server.accept();
                NetworkThread thread = new NetworkThread(socket);
                thread.start();
                clients.add(thread);
            }
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}
