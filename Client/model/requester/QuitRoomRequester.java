package model.requester;

public class QuitRoomRequester implements Requester {
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
