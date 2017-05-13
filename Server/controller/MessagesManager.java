package controller;

import model.responser.JoinRoomResponser;
import model.responser.NewRoomResponser;
import model.responser.QuitRoomResponser;
import model.responser.Responser;
import model.responser.RoomsListResponser;
import model.responser.FinishMatchResponser;
import model.responser.StartMatchResponser;

// Using pattern: Factory method
public class MessagesManager {
    
    public static Responser recvRequest(byte[] message) {
        switch(message[0]) {
            case 0:
                return FinishMatchResponser.Instance;
            case 1:
                return JoinRoomResponser.Instance;
            case 2:
                return NewRoomResponser.Instance;
            case 3:
                return QuitRoomResponser.Instance;
            case 4:
                return RoomsListResponser.Instance;
            case 5:
                return StartMatchResponser.Instance;
            default:
                return null;
        }
    }
    
}
