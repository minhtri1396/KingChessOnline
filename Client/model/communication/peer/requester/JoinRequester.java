package model.communication.peer.requester;

import controller.communication.MessagesManager;
import helper.Converter;
import helper.Wrapper;
import model.communication.IRequester;
import model.type.Player;

public class JoinRequester implements IRequester {
    
    public static final JoinRequester Instance = new JoinRequester();
    
    private JoinRequester() {}

    @Override
    public byte[] getRequestContent(Object values) {
        Player player = (Player)values;
        return Wrapper.INSTANCE.wrap(new Object[] {
            Converter.toBytes(MessagesManager.PeerMessageType.JOIN.rawValue()),
            Converter.toBytes(player.getId()),
            Converter.toBytes(player.getIp()),
            Converter.toBytes(player.getPort())
        });
    }

    @Override
    public Object translate(byte[] message) {
        return (byte)Wrapper.INSTANCE.unwrap(message)[0] == 1; // success?
    }
    
}
