package model.responser;

import helper.Converter;
import helper.Wrapper;
import java.net.Socket;
import main.App;
import model.type.Player;
import model.type.Room;

public class JoinRoomResponser implements IResponser {
    public static final JoinRoomResponser Instance = new JoinRoomResponser();
    
    @Override
    public byte[] makeResponseContentFor(byte[] message, Socket socket) {
        Object[] info = Wrapper.INSTANCE.unwrap(message);
        
        int roomID = Converter.toInt((byte[])info[1]);
        
        Room room = (Room)App.INSTANCE.Rooms.getRoom(roomID);
        if (room == null) {
            return Wrapper.INSTANCE.wrap(new Object[] {
                (byte)0
            });
        }
        
        Player admin = App.INSTANCE.Rooms.getAdmin(roomID);
        
        return Wrapper.INSTANCE.wrap(new Object[] {
            (byte)1, // success
            Converter.toBytes(room.getId()),
            Converter.toBytes(room.getName()),
            room.getNumberPlayers(),
            Converter.toBytes(admin.getId()),
            Converter.toBytes(admin.getIp()),
            Converter.toBytes(admin.getPort())
        });
    }
}
