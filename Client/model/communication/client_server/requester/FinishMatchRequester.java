package model.communication.client_server.requester;

import model.communication.IRequester;

public class FinishMatchRequester implements IRequester {
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
