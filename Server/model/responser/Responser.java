package model.responser;

// All reponsers should implement this interface.
public interface Responser {
    public byte[] makeResponseContentFor(byte[] message);
}
