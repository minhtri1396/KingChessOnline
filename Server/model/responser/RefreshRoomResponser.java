package model.responser;

import helper.Converter;
import helper.Wrapper;
import java.net.Socket;
import main.App;
import model.datatype.Player;
import model.datatype.Room;

public class RefreshRoomResponser implements Responser {
    public static final RefreshRoomResponser Instance = new RefreshRoomResponser();
    
    @Override
    public byte[] makeResponseContentFor(byte[] message, Socket socket) {
        Object[] info = Wrapper.INSTANCE.unwrap(message);
        
        Room room = new Room(
                Converter.toInt((byte[])info[1]),
                Converter.toString((byte[])info[2])
        );
        room.setNumberPlayers((byte)1);
        
        Player admin = new Player(
                Converter.toInt((byte[])info[3]),
                socket.getInetAddress().getHostAddress(), 
                Converter.toInt((byte[])info[4])
        );
        
        App.INSTANCE.Rooms.addRoom(room, admin);
        
        return Wrapper.INSTANCE.wrap(new Object[] {
            (byte)1 // added the room to the rooms list
        });
    }
}
