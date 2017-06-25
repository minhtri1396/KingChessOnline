package model.communication.peer;

import controller.communication.MessagesManager;
import helper.Converter;
import helper.ErrorLogger;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import model.communication.IResponser;

public class ConnectingPeerClient implements Runnable {
    
    public static void connectTo(Socket socket) {
        Thread thread = new Thread (new ConnectingPeerClient(socket));
        thread.start();
    }
    
    private final Socket socket;
    
    private ConnectingPeerClient(Socket socket) {
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
            byte[] msgSize = new byte[4];
            bi.read(msgSize);
            byte[] message  = new byte[Converter.toInt(msgSize)];
            bi.read(message);
            IResponser responser = MessagesManager.recvPeerRequest(message);
            // Send the client response message.
            byte[] responseMessage = responser.makeResponseContentFor(message);
            bo.write(Converter.toBytes(responseMessage.length));
            bo.write(responseMessage);
            bo.flush();
        } catch (IOException ioe) {
            ErrorLogger.log(ConnectingPeerClient.class, ioe);
        }
    }
    
}