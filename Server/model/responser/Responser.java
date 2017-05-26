package model.responser;

// All reponsers should implement this interface.

import java.net.Socket;

public interface Responser {
    public byte[] makeResponseContentFor(byte[] message, Socket socket);
}
