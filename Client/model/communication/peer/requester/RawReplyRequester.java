package model.communication.peer.requester;

import controller.communication.MessagesManager;
import helper.Converter;
import helper.Wrapper;
import model.communication.IRequester;

public class RawReplyRequester implements IRequester {
    
    public static final RawReplyRequester Instance = new RawReplyRequester();
    
    private RawReplyRequester() {}
    
    @Override
    public byte[] getRequestContent(Object values) {
        return Wrapper.INSTANCE.wrap(new Object[] {
            Converter.toBytes(MessagesManager.PeerMessageType.RAW_REPLY.rawValue()),
            (byte)values
        });
    }
    
    @Override
    public Object translate(byte[] message) {
        return (byte)Wrapper.INSTANCE.unwrap(message)[0] == 1; // success?
    }
    
}
