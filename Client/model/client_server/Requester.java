package model.client_server;

// All requesters should implement this interface.
public interface Requester {
    public byte[] getRequestContent(Object values);
    public Object translate(byte[] message);
}