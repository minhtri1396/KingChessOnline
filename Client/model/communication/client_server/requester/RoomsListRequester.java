package model.communication.client_server.requester;

import model.communication.IRequester;
import controller.communication.MessagesManager;
import helper.Converter;
import helper.Wrapper;
import java.util.ArrayList;
import model.type.Room;

public class RoomsListRequester implements IRequester {
    public static final RoomsListRequester Instance = new RoomsListRequester();
    
    private static final byte NUMBER_ROOM = 20; // the number of rooms we should request
    
    @Override
    public byte[] getRequestContent(Object values) {
        return Wrapper.INSTANCE.wrap(new Object[] {
            Converter.toBytes(MessagesManager.MessageType.ROOMS_LIST.rawValue()),
            Converter.toBytes((long)values), // current timestamp of rooms list
            NUMBER_ROOM
        });
    }

    @Override
    public Object translate(byte[] message) {
        Object[] info = Wrapper.INSTANCE.unwrap(message);
        
        ArrayList<Room> rooms = new ArrayList<>();
        if ((byte)info[0] == 1) {
            Room room;
            int nInfo = Converter.toInt((byte[])info[1]) + 2;
            int iInfo = 2;
            while (iInfo < nInfo) {
                room = new Room(
                        Converter.toInt((byte[])info[iInfo++]),
                        Converter.toString((byte[])info[iInfo++])
                );
                room.setNumberPlayers((byte)info[iInfo++]);
                rooms.add(room);
            }
        }
        
        return rooms;
    }
}
