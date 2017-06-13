package model.communication.client_server.requester;

import model.communication.IRequester;

public class QuitRoomRequester implements IRequester {
    public static final QuitRoomRequester Instance = new QuitRoomRequester();
    
    @Override
    public byte[] getRequestContent(Object values) {
        return null;
    }

    @Override
    public Object translate(byte[] message) {
        return null;
    }
}
