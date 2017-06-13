package model.communication.peer.requester;

import controller.communication.MessagesManager;
import helper.Converter;
import helper.Wrapper;
import model.communication.IRequester;

public class ExitRequester implements IRequester {
    
    public static final ExitRequester Instance = new ExitRequester();
    
    private ExitRequester() {}

    @Override
    public byte[] getRequestContent(Object values) {
        return Wrapper.INSTANCE.wrap(new Object[] {
            Converter.toBytes(MessagesManager.PeerMessageType.EXIT.rawValue())
        });
    }

    @Override
    public Object translate(byte[] message) {
        return (byte)Wrapper.INSTANCE.unwrap(message)[0] == 1; // success?
    }
    
}
