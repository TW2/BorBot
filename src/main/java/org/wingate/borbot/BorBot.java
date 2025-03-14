package org.wingate.borbot;

import org.wingate.borbot.io.Client;
import org.wingate.borbot.io.Server;
import org.wingate.borbot.message.OpenMessage;

import java.awt.*;

public class BorBot {

    private static Server server = null;
    private static Client client = null;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            if(!launcher(args)){
                System.out.println("""
                        You are trying to execute a BorBot system program without arguments.
                        Please choose one argument:
                        -server <p>     This command initialize a new server with a port <p> which is waiting for app signal.
                        -client <p>     This command starts a client which try to bind server port <p>.
                        If you choose to start a client, you have to write one more argument:
                        -name <name>    This is the name of the application you try to open in server.
                        """);
                System.exit(0);
            }else{
                System.out.println("BorBot starts...");
            }
        });
    }

    public static Server getServer() {
        return server;
    }

    public static Client getClient() {
        return client;
    }

    private static boolean launcher(String[] args){
        if(args.length < 2) return false;
        if(args[0].equals("-server")){
            try{
                int port = Integer.parseInt(args[1]);
                server = Server.start(port);
                return true;
            }catch(Exception _){
                return false;
            }
        }
        if(args.length < 4) return false;
        if(args[0].equals("-client") && args[2].equals("-name")){
            try{
                int port = Integer.parseInt(args[1]);
                OpenMessage op = new OpenMessage(args[3]);
                op.setPort(port);
                client = Client.create(op);
                return true;
            }catch(Exception _){
                return false;
            }
        }
        return false;
    }
}
