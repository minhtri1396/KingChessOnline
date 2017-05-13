package model.requester;

public class FinishMatchRequester implements Requester {
    public static final FinishMatchRequester Instance = new FinishMatchRequester();
    
    @Override
    public byte[] getRequestContent(Object values) {
        return null;
    }

    @Override
    public Object translate(byte[] message) {
        return null;
    }
}
