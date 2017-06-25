package model.communication.peer.responser;

import controller.communication.IPeerMessageListener;
import controller.communication.MessagesManager;
import helper.Wrapper;
import model.communication.IResponser;

public class ExitResponser implements IResponser {
    
    public static final ExitResponser Instance = new ExitResponser();
    
    private ExitResponser() {}
    
    @Override
    public byte[] makeResponseContentFor(byte[] message) {
        Object[] info = Wrapper.INSTANCE.unwrap(message);
        
        IPeerMessageListener peerMessageListener = MessagesManager.getPeerMessageListener();
        
        if (peerMessageListener == null) {
            return Wrapper.INSTANCE.wrap(new Object[] {
                (byte)0
            });
        }
        
        peerMessageListener.hasRequestMessage(info);
        
        return Wrapper.INSTANCE.wrap(new Object[] {
            (byte)1
        });
    }
    
}
