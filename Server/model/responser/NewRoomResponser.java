package model.responser;

import helper.Converter;
import helper.Wrapper;
import java.net.Socket;
import main.App;
import model.dao.UserDAO;
import model.type.Player;
import model.type.Room;

public class NewRoomResponser implements IResponser {
    public static final NewRoomResponser Instance = new NewRoomResponser();
    
    @Override
    public byte[] makeResponseContentFor(byte[] message, Socket socket) {
        Object[] info = Wrapper.INSTANCE.unwrap(message);
        
        int roomID = App.INSTANCE.IDGenerator.find();
        
        if (roomID > -1) {
            Room room = new Room(roomID, Converter.toString((byte[])info[1]));
            room.setNumberPlayers((byte)1);

            Player admin = UserDAO.BUILDER.getInfo(Converter.toInt((byte[])info[2]));
            admin.setIp(Converter.toString((byte[])info[3]));
            admin.setPort(Converter.toInt((byte[])info[4]));

            App.INSTANCE.Rooms.addRoom(room, admin);

            return Wrapper.INSTANCE.wrap(new Object[] {
                (byte)1, // success
                Converter.toBytes(room.getId()),
                Converter.toBytes(room.getName()),
                room.getNumberPlayers()
            });
        }
        
        return Wrapper.INSTANCE.wrap(new Object[] {
            (byte)0
        });
    }
}
