package org.wingate.borbot.io;

import org.wingate.borbot.message.OpenMessage;

import java.io.IOException;
import java.net.*;
import java.util.Collections;

public class Client {
    private final Socket socket;

    private Client(Socket socket) {
        this.socket = socket;
    }

    public static Client create(OpenMessage message) throws IOException {
        Client c = new Client(new Socket());

        String localHost = c.getLocalIP();
        c.getSocket().connect(new InetSocketAddress(localHost, message.getPort()), 1500);


        return c;
    }

    public Socket getSocket() {
        return socket;
    }

    private String getLocalIP() throws SocketException {
        return Collections.list(NetworkInterface.getNetworkInterfaces()).stream()
                .flatMap(i -> Collections.list(i.getInetAddresses()).stream())
                .filter(ip -> ip instanceof Inet4Address && ip.isSiteLocalAddress())
                .findFirst().orElseThrow(RuntimeException::new)
                .getHostAddress();
    }

    public void closeSocket(Socket socket) throws IOException {
        socket.close();
    }
}
