package model.responser;

import java.net.Socket;

public class JoinRoomResponser implements IResponser {
    public static final JoinRoomResponser Instance = new JoinRoomResponser();
    
    @Override
    public byte[] makeResponseContentFor(byte[] message, Socket socket) {
        return null;
    }
}
