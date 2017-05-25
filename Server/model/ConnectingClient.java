package model;

import controller.MessagesManager;
import helper.ErrorLogger;
import helper.ThreadPool;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import model.responser.Responser;

public class ConnectingClient implements Runnable {
    
    public static void connectTo(Socket socket) {
        ThreadPool.BUILDER.execute(new ConnectingClient(socket));
    }
    
    private final Socket socket;
    
    private ConnectingClient(Socket socket) {
        this.socket = socket;
    }

    // Message format:
    // first: #bytes
    // remained bytes: content of message
    @Override
    public void run() {
        try (
            BufferedInputStream bi = new BufferedInputStream(socket.getInputStream());
            BufferedOutputStream bo = new BufferedOutputStream(socket.getOutputStream());
        ) {
            // Receive request message from client.
            byte[] message  = new byte[bi.read()];
            bi.read(message);
            Responser responser = MessagesManager.recvRequest(message);
            // Send the client response message.
            byte[] responseMessage = responser.makeResponseContentFor(message);
            bo.write(responseMessage.length);
            bo.write(responseMessage);
            bo.flush();
        } catch (IOException ioe) {
            ErrorLogger.log(ConnectingClient.class, ioe);
        } finally {
            try {
                socket.close();
            } catch (IOException ioe) {
                ErrorLogger.log(ConnectingClient.class, ioe);
            }
        }
    }
    
}
