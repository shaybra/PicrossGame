package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream out;

    // constructor to put ip address and port
    public Client(String address, int port) {
        // establish a connection
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(InetAddress.getByName(address), port), 10000);
            socket.setSoTimeout(10000);
            input = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            try {
                Scanner in = new Scanner(input);
                while (in.hasNextLine()) {
                    String line = input.readObject().toString();
                    out.writeObject(line);
                }
            } catch (ClassNotFoundException e) {
                System.out.println(e);
            } finally {
                socket.close();
            }
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }
    }

    /**
     * Input stream getter.
     * @return the {@link #input} stream
     */
    public ObjectInputStream getInputStream() {
        return input;
    }

    /**
     * Output stream getter.
     * @return the {@link #out} stream
     */
    public ObjectOutputStream getOutputStream() {
        return out;
    }

    /**
     * Closes the connection
     */
    public void close() {
        try {
            input.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
