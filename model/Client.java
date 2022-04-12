package model;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private InputStream input;
    private OutputStream out;

    public Client(String address, int port, String name) {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(InetAddress.getByName(address), port), 10000);
            socket.setSoTimeout(10000);
            input = socket.getInputStream();
            out = socket.getOutputStream();
            try {
                out.write(name.getBytes());
                Scanner in = new Scanner(input);
                while (in.hasNextLine()) {
                    String line = in.nextLine();
                    out.write(line.getBytes());
                }
                in.close();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        }
    }
}
