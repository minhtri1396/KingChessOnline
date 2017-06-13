package model.communication.client_server.requester;

import model.communication.IRequester;

public class JoinRoomRequester implements IRequester {
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
