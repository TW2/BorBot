package org.wingate.borbot.io;

import org.wingate.borbot.ui.Window;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Handler implements Runnable {

    private final ServerSocket serverSocket;
    private Socket socket;

    public Handler(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while(true){
            try {
                socket = serverSocket.accept();
                InputStream in = socket.getInputStream();
                DataInputStream receiver = new DataInputStream(in);

                switch (receiver.readUTF()){
                    case "Window" -> {
                        String xml = receiver.readUTF();
                        XML.Reader reader = new XML.Reader(xml);
                        Window window = reader.getWindowhandler().getWindow();
                        window.setVisible(true);
                    }
                }

                System.out.println("Hello, it works!");
                socket.close();
            } catch (IOException e) {
                break;
            }
        }
    }
}
