package model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class NetworkThread implements Runnable {
    private Client client;

    public NetworkThread(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream inStream = client.getInputStream();
            ObjectOutputStream outStream = client.getOutputStream();

            Scanner in = new Scanner(inStream);
            PrintWriter out = new PrintWriter(outStream, true /* autoFlush */);

            out.println("Hello! Enter BYE to exit.");

            // echo client input
            boolean done = false;
            while (!done && in.hasNextLine()) {
                String line = in.nextLine();
                out.println("Echo: " + line);
                System.out.println("Client: " + line);
                if (line.trim().equals("BYE"))
                    done = true;
            }
        } finally {
            client.close();
        }
    }
}
