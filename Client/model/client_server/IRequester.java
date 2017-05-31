package model.client_server;

// All requesters should implement this interface.
public interface IRequester {
    public byte[] getRequestContent(Object values);
    public Object translate(byte[] message);
}
