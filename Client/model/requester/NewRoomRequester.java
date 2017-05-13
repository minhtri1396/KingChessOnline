package model.requester;

public class NewRoomRequester implements Requester {
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
