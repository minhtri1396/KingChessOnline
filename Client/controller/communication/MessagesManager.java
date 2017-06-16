package controller.communication;

import helper.Converter;
import helper.Wrapper;
import model.communication.client_server.requester.*;
import model.communication.peer.requester.*;
import model.communication.peer.responser.*;
import model.communication.IRequester;
import model.communication.IResponser;

// Using pattern: Factory method
public class MessagesManager {
    
    ////////////////////////////////////////////////////////////
    //                    CLIENT SERVER                       //
    ////////////////////////////////////////////////////////////
    
    public enum MessageType {
        FINISH_MATCH(0),
        JOIN_ROOM(1),
        NEW_ROOM(2),
        QUIT_ROOM(3),
        ROOMS_LIST(4),
        START_MATCH(5),
        LOGIN(6),
        REFRESH_ROOM(7);
        
        private final int id;
        private MessageType(int id) {
            this.id = id;
        }
        
        public int rawValue() {
            return this.id;
        }
    }
    
    public static IRequester sendRequest(MessageType msgType) {
        switch(msgType) {
            case FINISH_MATCH:
                return FinishMatchRequester.Instance;
            case JOIN_ROOM:
                return JoinRoomRequester.Instance;
            case NEW_ROOM:
                return NewRoomRequester.Instance;
            case QUIT_ROOM:
                return QuitRoomRequester.Instance;
            case ROOMS_LIST:
                return RoomsListRequester.Instance;
            case START_MATCH:
                return StartMatchRequester.Instance;
            case LOGIN:
                return LoginRequester.Instance;
            case REFRESH_ROOM:
                return RefreshRoomRequester.Instance;
            default:
                return null;
        }
    }
    
    ////////////////////////////////////////////////////////////
    //                    PEER TO PEER                        //
    ////////////////////////////////////////////////////////////
    
    public enum PeerMessageType {
        ATTACKING(0),
        EXIT(1),
        MOVING(2),
        RAW_REPLY(3),
        RAW(4),
        JOIN(5);
        
        private final int id;
        private PeerMessageType(int id) {
            this.id = id;
        }
        
        public int rawValue() {
            return this.id;
        }
        
        public static PeerMessageType getType(int rawValue) {
            PeerMessageType[] values = PeerMessageType.values();
            if (rawValue < 0 || rawValue >= values.length) {
                return null;
            }
            return values[rawValue];
        }
    }
    
    private static IPeerMessageListener peerMessageListener;

    public static IPeerMessageListener getPeerMessageListener() {
        return MessagesManager.peerMessageListener;
    }

    public static void setPeerMessageListener(IPeerMessageListener peerMessageListener) {
        MessagesManager.peerMessageListener = peerMessageListener;
    }
    
    public static IRequester sendPeerRequest(PeerMessageType msgType) {
        switch(msgType) {
            case ATTACKING:
                return AttackingRequester.Instance;
            case EXIT:
                return ExitRequester.Instance;
            case MOVING:
                return MovingRequester.Instance;
            case RAW_REPLY:
                return RawReplyRequester.Instance;
            case RAW:
                return RawRequester.Instance;
            case JOIN:
                return JoinRequester.Instance;
            default:
                return null;
        }
    }
    
    public static IResponser recvPeerRequest(byte[] message) {
        PeerMessageType msgType = PeerMessageType.getType(
                Converter.toInt((byte[])Wrapper.INSTANCE.unwrap(message)[0]));
        switch(msgType) {
            case ATTACKING:
                return AttackingResponser.Instance;
            case EXIT:
                return ExitResponser.Instance;
            case MOVING:
                return MovingResponser.Instance;
            case RAW_REPLY:
                return RawReplyResponser.Instance;
            case RAW:
                return RawResponser.Instance;
            case JOIN:
                return JoinResponser.Instance;
            default:
                return null;
        }
    }
    
}
