package model.responser;

import helper.Converter;
import helper.Wrapper;
import java.net.Socket;
import main.App;
import model.type.Room;

public class RefreshRoomResponser implements IResponser {
    public static final RefreshRoomResponser Instance = new RefreshRoomResponser();
    
    @Override
    public byte[] makeResponseContentFor(byte[] message, Socket socket) {
        Object[] info = Wrapper.INSTANCE.unwrap(message);
        
        Room room = new Room(
                Converter.toInt((byte[])info[1]),
                Converter.toString((byte[])info[2])
        );
        room.setNumberPlayers((byte)info[3]);
        
        return Wrapper.INSTANCE.wrap(new Object[] {
            App.INSTANCE.Rooms.updateRoom(room) ? (byte)1 : (byte)0
        });
    }
}
