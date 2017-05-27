package model.requester;

import controller.MessagesManager;
import helper.Converter;
import helper.Wrapper;
import model.datatype.Player;
import model.datatype.Room;

public class RefreshRoomRequester implements Requester {
    public static final RefreshRoomRequester Instance = new RefreshRoomRequester();
    
    // Param: Object[3] -> 0: room, 1: player, 2: expected port
    // Expected port: which another client will connect to
    @Override
    public byte[] getRequestContent(Object values) {
        if (values instanceof Object[]) {
            Object[] content = (Object[]) values;
            Room room = (Room)content[0];
            Player admin = (Player)content[1];
            int port = (int)content[2];
            return Wrapper.INSTANCE.wrap(new Object[] {
                Converter.toBytes(MessagesManager.MessageType.REFRESH_ROOM.rawValue()),
                Converter.toBytes(room.getId()),
                Converter.toBytes(room.getName()),
                Converter.toBytes(admin.getId()),
                port
            });
        }
        
        return null;
    }

    @Override
    public Object translate(byte[] message) {
        return (byte)Wrapper.INSTANCE.unwrap(message)[0] == 1; // success?
    }
}
