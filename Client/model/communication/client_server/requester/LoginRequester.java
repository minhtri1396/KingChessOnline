package model.communication.client_server.requester;

import model.communication.IRequester;
import controller.communication.MessagesManager;
import helper.Converter;
import helper.Wrapper;
import model.type.Player;

public class LoginRequester implements IRequester {
    public static final LoginRequester Instance = new LoginRequester();
    
    // Param: String[2] -> 0: username, 1: password
    @Override
    public byte[] getRequestContent(Object values) {
        if (values instanceof String[]) {
            String[] userInfo = (String[]) values;
            return Wrapper.INSTANCE.wrap(new Object[] {
                Converter.toBytes(MessagesManager.MessageType.LOGIN.rawValue()),
                Converter.toBytes(userInfo[0]), // username
                Converter.toBytes(userInfo[1])  // password
            });
        }
        
        return null;
    }

    @Override
    public Object translate(byte[] message) {
        Object[] info = Wrapper.INSTANCE.unwrap(message);
        
        Player player = null;
        int playerID = Converter.toInt((byte[])info[0]);
        if (playerID > -1) {
            player = new Player(
                    playerID,
                    Converter.toString((byte[])info[3]), // IP address
                    0
            );
            player.setLevel(Converter.toInt((byte[])info[1]));
            player.setName(Converter.toString((byte[])info[2]));
        } else {} // username isn't existed or password is invalid
        
        return player;
    }
}
