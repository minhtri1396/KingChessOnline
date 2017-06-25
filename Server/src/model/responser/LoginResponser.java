package model.responser;

import helper.Converter;
import helper.Wrapper;
import java.net.Socket;
import main.App;
import model.dao.AccountDAO;
import model.dao.UserDAO;
import model.type.Player;
import model.type.Room;

public class LoginResponser implements IResponser {
    public static final LoginResponser Instance = new LoginResponser();
    
    @Override
    public byte[] makeResponseContentFor(byte[] message, Socket socket) {
        Object[] info = Wrapper.INSTANCE.unwrap(message);
        
        int playerID;
        if ((playerID = AccountDAO.BUILDER.isCorrect(
                Converter.toString((byte[])info[1]),
                Converter.toString((byte[])info[2]))) > -1) { // valid password
            Player player = UserDAO.BUILDER.getInfo(playerID);
            // Player is existed
            if (player != null) {
                // Remove room which player created before
                Room[] rooms = App.INSTANCE.Rooms.getRooms();
                for (Room room : rooms) {
                    if (App.INSTANCE.Rooms.getAdmin(room.getId()).getIp().equals(socket.getInetAddress().getHostAddress())) {
                        App.INSTANCE.Rooms.removeRoom(room.getId());
                    }
                }
                
                return Wrapper.INSTANCE.wrap(new Object[] {
                    Converter.toBytes(playerID),
                    Converter.toBytes(player.getLevel()),
                    Converter.toBytes(player.getName()),
                    Converter.toBytes(socket.getInetAddress().getHostAddress())
                });
            }
        }
        
        // The account is not existed or the password was invalid
        return Wrapper.INSTANCE.wrap(new Object[] {
            Converter.toBytes(-1)
        });
    }
}
