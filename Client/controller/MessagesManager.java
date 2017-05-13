package controller;

import model.requester.FinishMatchRequester;
import model.requester.JoinRoomRequester;
import model.requester.NewRoomRequester;
import model.requester.QuitRoomRequester;
import model.requester.Requester;
import model.requester.RoomsListRequester;
import model.requester.StartMatchRequester;

// Using pattern: Factory method
public class MessagesManager {
    
    public enum MessageType {
        
        FINISH_MATCH(0),
        JOIN_ROOM(1),
        NEW_ROOM(2),
        QUIT_ROOM(3),
        ROOMS_LIST(4),
        START_MATCH(5);
        
        private final int id;
        private MessageType(int id) {
            this.id = id;
        }
        
        public int rawValue() {
            return this.id;
        }
        
    }
    
    public static Requester sendRequest(MessageType msgType) {
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
            default:
                return null;
        }
    }
    
}
