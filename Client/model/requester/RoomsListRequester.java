package model.requester;

public class RoomsListRequester implements Requester {
    public static final RoomsListRequester Instance = new RoomsListRequester();
    
    @Override
    public byte[] getRequestContent(Object values) {
        return null;
    }

    @Override
    public Object translate(byte[] message) {
        return null;
    }
}
