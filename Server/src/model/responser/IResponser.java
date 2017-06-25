package model.responser;

// All reponsers should implement this interface.

import java.net.Socket;

public interface IResponser {
    public byte[] makeResponseContentFor(byte[] message, Socket socket);
}
