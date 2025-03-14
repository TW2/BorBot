package org.wingate.borbot.io;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    private Thread thread;

    private Server(int port) throws IOException {
        thread = new Thread(new Handler(new ServerSocket(port)));
    }

    public static Server start(int port) throws IOException {
        Server server = new Server(port);
        server.thread.start();
        return server;
    }

    public void shutdown(){
        if(!thread.isInterrupted() || thread.isAlive()){
            thread.interrupt();
            thread = null;
        }
    }
}
