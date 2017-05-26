package model.responser;

import java.net.Socket;

public class RenewRoomResponser implements Responser {
    public static final RenewRoomResponser Instance = new RenewRoomResponser();
    
    @Override
    public byte[] makeResponseContentFor(byte[] message, Socket socket) {
        return null;
    }
}
