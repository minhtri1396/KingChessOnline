package model.communication.client_server.requester;

import model.communication.IRequester;
import controller.communication.MessagesManager;
import helper.Converter;
import helper.Wrapper;

public class StartMatchRequester implements IRequester {
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
        return (byte)Wrapper.INSTANCE.unwrap(message)[0] == 1;
    }
}
