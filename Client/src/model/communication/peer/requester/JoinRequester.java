package model.communication.peer.requester;

import controller.communication.MessagesManager;
import helper.Converter;
import helper.Wrapper;
import model.communication.IRequester;
import model.type.Player;
import model.type.Room;

public class JoinRequester implements IRequester {
    
    public static final JoinRequester Instance = new JoinRequester();
    
    private JoinRequester() {}

    @Override
    public byte[] getRequestContent(Object values) {
        Object[] info = (Object[])values;
        Room room = (Room)info[0];
        Player player = (Player)info[1];
        return Wrapper.INSTANCE.wrap(new Object[] {
            Converter.toBytes(MessagesManager.PeerMessageType.JOIN.rawValue()),
            Converter.toBytes(player.getId()),
            Converter.toBytes(player.getIp()),
            Converter.toBytes(player.getPort()),
            Converter.toBytes(room.getId())
        });
    }

    @Override
    public Object translate(byte[] message) {
        return (byte)Wrapper.INSTANCE.unwrap(message)[0] == 1; // success?
    }
    
}
