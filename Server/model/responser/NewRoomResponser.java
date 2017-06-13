package model.responser;

import java.net.Socket;

public class NewRoomResponser implements IResponser {
    public static final NewRoomResponser Instance = new NewRoomResponser();
    
    @Override
    public byte[] makeResponseContentFor(byte[] message, Socket socket) {
        return null;
    }
}
