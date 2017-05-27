package model.client_server;

public class JoinRoomRequester implements Requester {
    public static final JoinRoomRequester Instance = new JoinRoomRequester();
    
    @Override
    public byte[] getRequestContent(Object values) {
        return null;
    }

    @Override
    public Object translate(byte[] message) {
        return null;
    }
}
