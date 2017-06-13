package model.communication.client_server.requester;

import model.communication.IRequester;

public class NewRoomRequester implements IRequester {
    public static final NewRoomRequester Instance = new NewRoomRequester();
    
    @Override
    public byte[] getRequestContent(Object values) {
        return null;
    }

    @Override
    public Object translate(byte[] message) {
        return null;
    }
}
