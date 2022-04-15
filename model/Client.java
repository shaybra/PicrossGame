package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private Scanner in;
    private PrintWriter out;
    private String username;

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.username = username;
            this.in = new Scanner(socket.getInputStream());
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            closeAll(socket, in, out);
        }

    }

    public void sendMessage(String messageToSend) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                out.println(messageToSend);
                while (socket.isConnected())
                    out.write(username + ": " + messageToSend);
            }
        }).start();
    }

    public String receiveMessage() {
        String msgFromGroupChat = new String();
        if (in.hasNextLine())
            msgFromGroupChat = in.nextLine();
        return msgFromGroupChat;
    }

    public void closeAll(Socket socket, Scanner in, PrintWriter out) {
        try {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
