package model.responser;

import java.net.Socket;

public class FinishMatchResponser implements IResponser {
    public static final FinishMatchResponser Instance = new FinishMatchResponser();
    
    @Override
    public byte[] makeResponseContentFor(byte[] message, Socket socket) {
        return null;
    }
}
