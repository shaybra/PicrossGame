package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import javax.swing.SwingUtilities;

import view.ChatFrame;

public class Client {
    private static Socket socket;
    private BufferedReader bf;
    private PrintWriter out;
    private String username;
    private String recievedMessage;

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.username = username;
            this.bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
            out.println(username);
        } catch (IOException e) {
            closeAll();
        }

    }

    public void sendMessage(String messageToSend) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (out != null)
                    out.println(messageToSend);
            }
        }).start();
    }

    public void receiveMessage(ChatFrame chat) throws InvocationTargetException, InterruptedException {
        while (true) {
            try {
                recievedMessage = bf.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!recievedMessage.isEmpty()) {
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        chat.updateChat(recievedMessage + '\n');
                    }
                });
            }
        }
    }

    public void closeAll() {
        try {
            if (bf != null) {
                bf.close();
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
