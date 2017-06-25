package model.communication;

// All reponsers should implement this interface.
public interface IResponser {
    public byte[] makeResponseContentFor(byte[] message);
}
