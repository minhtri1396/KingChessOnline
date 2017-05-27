package model.client_server;

import controller.MessagesManager;
import helper.Converter;
import helper.Wrapper;
import model.datatype.Room;

public class RefreshRoomRequester implements Requester {
    public static final RefreshRoomRequester Instance = new RefreshRoomRequester();
    
    @Override
    public byte[] getRequestContent(Object values) {
        if (values instanceof Room) {
            Room room = (Room)values;
            return Wrapper.INSTANCE.wrap(new Object[] {
                Converter.toBytes(MessagesManager.MessageType.REFRESH_ROOM.rawValue()),
                Converter.toBytes(room.getId()),
                Converter.toBytes(room.getName()),
                (byte)room.getNumberPlayers()
            });
        }
        
        return null;
    }

    @Override
    public Object translate(byte[] message) {
        return (byte)Wrapper.INSTANCE.unwrap(message)[0] == 1; // success?
    }
}
