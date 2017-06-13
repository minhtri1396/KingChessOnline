package model.communication.peer.responser;

import controller.communication.IPeerMessageListener;
import controller.communication.MessagesManager;
import helper.Wrapper;
import model.communication.IResponser;

public class AttackingResponser implements IResponser {
    
    public static final AttackingResponser Instance = new AttackingResponser();
    
    private AttackingResponser() {}
    
    @Override
    public byte[] makeResponseContentFor(byte[] message) {
        // 0: message type, 1: from position, 2: to position, 3: piece type, 4: enemy's piece type
        Object[] info = Wrapper.INSTANCE.unwrap(message);
        
        IPeerMessageListener peerMessageListener = MessagesManager.getPeerMessageListener();
        peerMessageListener.hasRequestMessage(info);
        
        return Wrapper.INSTANCE.wrap(new Object[] {
            (byte)1
        });
    }
    
}
