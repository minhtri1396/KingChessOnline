package model.communication.peer.requester;

import controller.communication.MessagesManager;
import helper.Converter;
import helper.Wrapper;
import model.communication.IRequester;
import model.type.enumeration.EPieces;
import model.type.enumeration.EPositions;

public class MovingRequester implements IRequester {
    
    public static final MovingRequester Instance = new MovingRequester();
    
    private MovingRequester() {}
    
    @Override
    public byte[] getRequestContent(Object values) {
        Object[] objs = (Object[])values;
        
        if (objs[6] == null) {
            return Wrapper.INSTANCE.wrap(new Object[] {
                Converter.toBytes(MessagesManager.PeerMessageType.MOVING.rawValue()),
                Converter.toBytes(((EPositions)objs[0]).rawValue()), // from position
                Converter.toBytes(((EPositions)objs[1]).rawValue()), // to position
                Converter.toBytes(((EPieces)objs[2]).rawValue()), // piece type
                Converter.toBytes((int)objs[3]), // hour
                Converter.toBytes((int)objs[4]), // min
                Converter.toBytes((int)objs[5]) // sec
            });
        } else {
            return Wrapper.INSTANCE.wrap(new Object[] {
                Converter.toBytes(MessagesManager.PeerMessageType.MOVING.rawValue()),
                Converter.toBytes(((EPositions)objs[0]).rawValue()), // from position
                Converter.toBytes(((EPositions)objs[1]).rawValue()), // to position
                Converter.toBytes(((EPieces)objs[2]).rawValue()), // piece type
                Converter.toBytes((int)objs[3]), // hour
                Converter.toBytes((int)objs[4]), // min
                Converter.toBytes((int)objs[5]), // sec
                Converter.toBytes(((EPieces)objs[6]).rawValue()) // promotion type
            });
        }
    }

    @Override
    public Object translate(byte[] message) {
        return (byte)Wrapper.INSTANCE.unwrap(message)[0] == 1; // success?
    }
    
}
