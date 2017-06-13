package model.communication.peer;

import helper.ErrorLogger;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class WaitingClient {
    
    public static void asynchronousWaitingOn(String ip, int port, int maximumQueue) {
        new Thread(() -> {
            try {
                InetAddress bindAddr = InetAddress.getByName(ip);
                ServerSocket ss = new ServerSocket(port, maximumQueue, bindAddr);
                System.out.println(String.format("Created server on %s:%d", ip, port));
                do {
                    Socket socket = ss.accept(); //synchronous
                    ConnectingPeerClient.connectTo(socket);
                } while (true); 
            } catch (IOException ioe) {
                ErrorLogger.log(WaitingClient.class, ioe);
            }
        }).start();
    }
    
}
