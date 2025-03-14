package org.wingate.borbot.io;

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

                System.out.println("Hello, it works!");
                socket.close();
            } catch (IOException e) {
                break;
            }
        }
    }
}
