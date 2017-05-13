package model.requester;

public class StartMatchRequester implements Requester {
    public static final StartMatchRequester Instance = new StartMatchRequester();

    @Override
    public byte[] getRequestContent(Object values) {
        return null;
    }

    @Override
    public Object translate(byte[] message) {
        return null;
    }
}
