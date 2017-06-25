package model;

import controller.MessagesManager;
import helper.Converter;
import helper.ErrorLogger;
import helper.ThreadPool;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import model.responser.IResponser;

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
            byte[] msgSize = new byte[4];
            bi.read(msgSize);
            byte[] message  = new byte[Converter.toInt(msgSize)];
            bi.read(message);
            IResponser responser = MessagesManager.recvRequest(message);
            // Send the client response message.
            byte[] responseMessage = responser.makeResponseContentFor(message, this.socket);
            bo.write(Converter.toBytes(responseMessage.length));
            bo.write(responseMessage);
            bo.flush();
        } catch (IOException ioe) {
            ErrorLogger.log(ConnectingClient.class, ioe);
        }
    }
    
}
