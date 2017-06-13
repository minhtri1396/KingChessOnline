package model.communication.peer;

import controller.communication.MessagesManager;
import helper.Converter;
import helper.ErrorLogger;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import model.communication.IRequester;
import model.communication.client_server.ConnectingServer;

public class ConnectingPeerServer {
    
    public static final ConnectingPeerServer CONNECTOR = new ConnectingPeerServer();
    
    private Socket socket = null;
    
    private ConnectingPeerServer() {}
    
    // We should invoke this method before sending anything to server
    public boolean create(String serverIP, int serverPort) {
        try {
            InetAddress serverAddr = InetAddress.getByName(serverIP);
            socket = new Socket(serverAddr, serverPort);
            return true;
        } catch (IOException ioe) {
            ErrorLogger.log(ConnectingServer.class, ioe);
            return false;
        }
    }

    // Message format:
    // first: #bytes
    // remained bytes: content of message
    public Object sendMessage(MessagesManager.PeerMessageType msgType, Object values) {
        try (
            BufferedInputStream bi = new BufferedInputStream(socket.getInputStream());
            BufferedOutputStream bo = new BufferedOutputStream(socket.getOutputStream());
        ) {
            // Send server request message
            IRequester requester = MessagesManager.sendPeerRequest(msgType);
            byte[] requestContent = requester.getRequestContent(values);
            bo.write(Converter.toBytes(requestContent.length));
            bo.write(requestContent);
            bo.flush();
            // Receive response message from the server
            byte[] msgSize = new byte[4];
            bi.read(msgSize);
            byte[] responseContent  = new byte[Converter.toInt(msgSize)];
            bi.read(responseContent);
            
            return requester.translate(responseContent);
        } catch (IOException ioe) {
            ErrorLogger.log(ConnectingServer.class, ioe);
            return null;
        }
    }
    
}
