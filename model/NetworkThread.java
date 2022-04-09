package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class NetworkThread implements Runnable {
    private final Socket socket;

    public NetworkThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outStream = new ObjectOutputStream(socket.getOutputStream());
            Scanner in = new Scanner(inStream);
            PrintWriter out = new PrintWriter(outStream, true);
            
            out.println("Hello! Enter /bye to exit.");
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
                    }
            }
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
