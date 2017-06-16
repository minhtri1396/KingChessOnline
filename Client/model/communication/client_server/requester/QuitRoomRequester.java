package model.communication.client_server.requester;

import controller.communication.MessagesManager;
import helper.Converter;
import helper.Wrapper;
import model.communication.IRequester;

public class QuitRoomRequester implements IRequester {
    public static final QuitRoomRequester Instance = new QuitRoomRequester();
    
    @Override
    public byte[] getRequestContent(Object values) {
        return Wrapper.INSTANCE.wrap(new Object[] {
            Converter.toBytes(MessagesManager.MessageType.QUIT_ROOM.rawValue()),
            Converter.toBytes((int)values) // room's id
        });
    }

    @Override
    public Object translate(byte[] message) {
        return (byte)Wrapper.INSTANCE.unwrap(message)[0] == 1;
    }
}
