package model.communication.client_server.requester;

import controller.communication.MessagesManager;
import helper.Converter;
import helper.Wrapper;
import model.communication.IRequester;
import model.type.Room;

public class NewRoomRequester implements IRequester {
    public static final NewRoomRequester Instance = new NewRoomRequester();
    
    @Override
    public byte[] getRequestContent(Object values) {
        Object[] info = (Object[])values;
        return Wrapper.INSTANCE.wrap(new Object[] {
            Converter.toBytes(MessagesManager.MessageType.NEW_ROOM.rawValue()),
            Converter.toBytes((String)info[0]), // room's name
            Converter.toBytes((int)info[1]), // user's id
            Converter.toBytes((String)info[2]), // user's IP
            Converter.toBytes((int)info[3]) // user's PORT
        });
    }

    @Override
    public Object translate(byte[] message) {
        Object[] info = Wrapper.INSTANCE.unwrap(message);
        
        Room room = null;
        
        if ((byte)info[0] == 1) {
            room = new Room(
                    Converter.toInt((byte[])info[1]),
                    Converter.toString((byte[])info[2])
            );

            room.setNumberPlayers((byte)info[3]);
        }
        
        return room;
    }
}
