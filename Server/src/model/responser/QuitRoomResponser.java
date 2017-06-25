package model.responser;

import helper.Converter;
import helper.Wrapper;
import java.net.Socket;
import main.App;

public class QuitRoomResponser implements IResponser {
    public static final QuitRoomResponser Instance = new QuitRoomResponser();
    
    @Override
    public byte[] makeResponseContentFor(byte[] message, Socket socket) {
        Object[] info = Wrapper.INSTANCE.unwrap(message);
        
        int roomID = Converter.toInt((byte[])info[1]);
        App.INSTANCE.Rooms.removeRoom(roomID);
        
        return Wrapper.INSTANCE.wrap(new Object[] {
            (byte)1
        });
    }
}
