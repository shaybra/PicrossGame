
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import model.NetworkThread;

public class PiccrossServer {

    private ServerSocket serverSocket;

    public PiccrossServer(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }
    

    public void startServer(){
        try {
            
            while (!serverSocket.isClosed()){
                Socket socket = serverSocket.accept();
                System.out.println("New client Connected!");
                NetworkThread networkThread = new NetworkThread(socket);
                Thread thread = new Thread(networkThread);
                thread.start();
            }
        } catch (IOException e) {

        }
    }

    public void closeServerSocket(){
        try {
            if (serverSocket != null){
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkPort(int port){
        if (port == 61001)
            System.out.println("Using default port: " + port);
        else
            System.out.println("Using port: " + port);
    }

    public static ServerSocket connectSocketPort (int port){
        try {
            ServerSocket server = new ServerSocket(port);
            if (port == 61001)
                System.out.println("Using default port: " + port);
            else
                System.out.println("Using port: " + port);
            System.out.println("Now listening to port:" + port);
           return server;
        } catch (IOException i) {
            System.out.println(i);
            return null;
        }
    }
    public static void main(String[] args) {
        if (args.length == 0 || Integer.parseInt(args[0]) <= 0 || Integer.parseInt(args[0]) >= 65536){
            if (args.length != 0)
                System.out.println("ERROR: Invalid port number: " + args[0]);

            PiccrossServer server = new PiccrossServer(connectSocketPort(61001));
            server.startServer();
        } else {
            PiccrossServer server = new PiccrossServer(connectSocketPort(Integer.parseInt(args[0])));
            server.startServer();
        }
        
    }
}
