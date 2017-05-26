package model.responser;

import java.net.Socket;

public class StartMatchResponser implements Responser {
    public static final StartMatchResponser Instance = new StartMatchResponser();
    
    @Override
    public byte[] makeResponseContentFor(byte[] message, Socket socket) {
        String reqMsg = new String(message);
        return null;
    }
}
