package org.wingate.borbot.io;

import org.wingate.borbot.message.OpenMessage;
import org.wingate.borbot.ui.Window;

import java.io.*;
import java.net.*;
import java.util.Collections;

public class Client {
    private final Socket socket;

    private Client(Socket socket) {
        this.socket = socket;
    }

    public static Client create(OpenMessage message) throws IOException {
        // Get XML app source
        String xml = fileToString(message.getPath());
        if(xml == null) return null;

        // Create client with its socket
        Client c = new Client(new Socket());

        // Get local IP (of your machine) and initialize a connection
        String localHost = c.getLocalIP();
        c.getSocket().connect(new InetSocketAddress(localHost, message.getPort()), 1500);

        // Send request to server by a handler
        // Your application will open in the server
        OutputStream out = c.getSocket().getOutputStream();
        DataOutputStream sender = new DataOutputStream(out);
        sender.writeUTF(Protocol.Window.getName());
        sender.writeUTF(xml);

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

    private static String fileToString(String path){
        StringBuilder sb = new StringBuilder();
        try(FileReader fr = new FileReader(path); BufferedReader br = new BufferedReader(fr);){
            String line;
            while((line = br.readLine()) != null){
                sb.append(line).append("\n");
            }
            return sb.toString();
        }catch(Exception _){
            return null;
        }
    }
}
