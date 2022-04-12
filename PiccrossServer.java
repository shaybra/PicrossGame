
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

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

class NetworkThread extends Thread {
    private Socket socket;

    public NetworkThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            try {
                InputStream inStream = socket.getInputStream();
                OutputStream outStream = socket.getOutputStream();
                InputStreamReader in = new InputStreamReader(inStream);
                BufferedReader bf = new BufferedReader(in);
                PrintWriter out = new PrintWriter(outStream, true /* autoFlush */);

                String name = bf.readLine();
                System.out.printf("Hello! %s Enter BYE to exit.", name);
                while (true) {
                    String line = bf.readLine();
                    if (line == null)
                        break;
                    System.out.println("Client: " + line);
                    if (line.trim().equals("BYE"))
                        break;
                }
            } finally {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
