package model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * 
 */
public class NetworkThread extends Thread {
    /**
     * Socket for the client.
     */
    private final Socket socket;
    private String name;

    /**
     * Constructor for the NetworkThread class.
     * 
     * @param socket the socket that the thread will use to communicate with the
     *               server.
     */
    public NetworkThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    /**
     * Runnable method for the thread.
     */
    public void run() {
        try {
            InputStream inStream = socket.getInputStream();
            OutputStream outStream = socket.getOutputStream();
            Scanner in = new Scanner(inStream);
            PrintWriter out = new PrintWriter(outStream, true);
            
            name = in.nextLine();
            out.println("Hello!" + name + " Enter /bye to exit.");
            boolean done = false;
            while (!done && in.hasNextLine()) {
                String line = in.nextLine();
                out.println("Echo: " + line);
                System.out.println("Client: " + line);
                if (line.trim().charAt(0) == '/')
                    switch (line.trim()) {
                        case "bye":
                            done = true;
                            break;
                        case "help":
                            break;
                        case "name":
                            break;
                        case "who":
                            break;
                        default:
                            break;
                    }
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
