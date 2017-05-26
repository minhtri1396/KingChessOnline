package model.responser;

import java.net.Socket;

public class QuitRoomResponser implements Responser {
    public static final QuitRoomResponser Instance = new QuitRoomResponser();
    
    @Override
    public byte[] makeResponseContentFor(byte[] message, Socket socket) {
        return null;
    }
}
