package model.client_server;

import controller.MessagesManager;
import helper.Converter;
import helper.Wrapper;

public class StartMatchRequester implements Requester {
    public static final StartMatchRequester Instance = new StartMatchRequester();
    
    // Param: values should be id of this match (room)
    @Override
    public byte[] getRequestContent(Object values) {
        int roomID = (int)values;
        return Wrapper.INSTANCE.wrap(new Object[] {
            Converter.toBytes(MessagesManager.MessageType.START_MATCH.rawValue()),
            Converter.toBytes(roomID)
        });
    }

    @Override
    public Object translate(byte[] message) {
        
        return null;
    }
}
