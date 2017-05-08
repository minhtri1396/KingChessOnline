package model;

import helper.ErrorLogger;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

// TODO: should implement stop method for this class.
// We can do that by creating a fake client and then exit the waiting loop.
public class WaitingClient {
    
    public static void asynchronousWaitingOn(String ip, int port, int maximumQueue) {
        new Thread(() -> {
            try {
                InetAddress bindAddr = InetAddress.getByName(ip);
                ServerSocket ss = new ServerSocket(port, maximumQueue, bindAddr);
                System.out.println(String.format("Created server on %s:%d", ip, port));
                do {
                    System.out.println("Waiting for a Client");
                    Socket socket = ss.accept(); //synchronous
                    ConnectingClient.connectTo(socket);
                } while (true); 
            } catch (IOException ioe) {
                ErrorLogger.log(WaitingClient.class, ioe);
            }
        }).start();
    }
    
}
