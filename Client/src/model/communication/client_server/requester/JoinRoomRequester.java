package model.communication.client_server.requester;

import controller.communication.MessagesManager;
import helper.Converter;
import helper.Wrapper;
import model.communication.IRequester;
import model.type.Player;
import model.type.Room;

public class JoinRoomRequester implements IRequester {
    public static final JoinRoomRequester Instance = new JoinRoomRequester();
    
    @Override
    public byte[] getRequestContent(Object values) {
        return Wrapper.INSTANCE.wrap(new Object[] {
            Converter.toBytes(MessagesManager.MessageType.JOIN_ROOM.rawValue()),
            Converter.toBytes((int)values) // room's id
        });
    }

    @Override
    public Object translate(byte[] message) {
        Object[] info = Wrapper.INSTANCE.unwrap(message);
        
        Object[] result = null;
        if ((byte)info[0] == 1) {
            result = new Object[2];  // 0: room, 1: admin
            Room room = new Room(
                    Converter.toInt((byte[])info[1]),
                    Converter.toString((byte[])info[2])
            );
            room.setNumberPlayers((byte)info[3]);
            result[0] = room;
            
            result[1] = new Player(
                    Converter.toInt((byte[])info[4]),
                    Converter.toString((byte[])info[5]),
                    Converter.toInt((byte[])info[6])
            );
        }
        
        return result;
    }
}
