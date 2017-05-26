package model.responser;

import helper.Converter;
import helper.Wrapper;
import java.util.ArrayList;
import main.App;
import model.datatype.Room;

public class RoomsListResponser implements Responser {
    public static final RoomsListResponser Instance = new RoomsListResponser();
    
    @Override
    public byte[] makeResponseContentFor(byte[] message) {
        Object[] info = Wrapper.INSTANCE.unwrap(message);
        long clientTimestamp = Converter.toLong((byte[])info[1]);
        
        if (clientTimestamp < App.INSTANCE.Rooms.getTimestamp()) {
            Room[] rooms = App.INSTANCE.Rooms.getRooms();
            byte nRooms = (byte)info[2]; // the number of rooms server need to response
            nRooms = nRooms < rooms.length ? nRooms : (byte)rooms.length;
            
            ArrayList<Object> bytes = new ArrayList<>();
            bytes.add((byte)1); // server will response rooms list
            // Amount of rooms information, each room has 3 information so we multiply 3
            bytes.add(Converter.toBytes((int)(nRooms * 3)));
            for (int iRoom = 0; iRoom < nRooms; ++iRoom) {
                bytes.add(Converter.toBytes(rooms[iRoom].getId()));
                bytes.add(Converter.toBytes(rooms[iRoom].getName()));
                bytes.add(rooms[iRoom].getNumberPlayers());
            }
            
            return Wrapper.INSTANCE.wrap(bytes.toArray());
        }
        
        return Wrapper.INSTANCE.wrap(new Object[] {
            (byte)0 // server won't response rooms list
        });
    }
    
}
