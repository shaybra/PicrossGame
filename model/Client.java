package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private Socket socket;
    private InputStream input;
    private OutputStream output;

    public Client(String address, int port, String name) {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(InetAddress.getByName(address), port), 10000);
            socket.setSoTimeout(10000);
            input = socket.getInputStream();
            output = socket.getOutputStream();
            try {
                PrintWriter out = new PrintWriter(output, true /* autoFlush */);
                InputStreamReader in = new InputStreamReader(System.in);
                BufferedReader bf = new BufferedReader(in);
                bf.read(name.toCharArray());
                while (true) {
                    String line = bf.readLine();
                    if (line == null)
                        break;
                    out.println(line);
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
