package model.communication.peer.responser;

import controller.communication.IPeerMessageListener;
import controller.communication.MessagesManager;
import helper.Wrapper;
import model.communication.IResponser;

public class JoinResponser implements IResponser {
    
    public static final JoinResponser Instance = new JoinResponser();
    
    private JoinResponser() {}
    
    @Override
    public byte[] makeResponseContentFor(byte[] message) {
        // 0: message type, 1: from position, 2: to position, 3: piece type, 4: enemy's piece type
        Object[] info = Wrapper.INSTANCE.unwrap(message);
        
        IPeerMessageListener peerMessageListener = MessagesManager.getPeerMessageListener();
        
        return Wrapper.INSTANCE.wrap(new Object[] {
            ((boolean)peerMessageListener.hasRequestMessage(info)) ? (byte)1 : (byte)0
        });
    }
    
}
