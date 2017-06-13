package model.communication.peer.requester;

import controller.communication.MessagesManager;
import helper.Converter;
import helper.Wrapper;
import model.communication.IRequester;
import model.type.enumeration.EPieces;
import model.type.enumeration.EPositions;

public class AttackingRequester implements IRequester {
    
    public static final AttackingRequester Instance = new AttackingRequester();
    
    private AttackingRequester() {}

    @Override
    public byte[] getRequestContent(Object values) {
        Object[] objs = (Object[])values;
        return Wrapper.INSTANCE.wrap(new Object[] {
            Converter.toBytes(MessagesManager.PeerMessageType.ATTACKING.rawValue()),
            Converter.toBytes(((EPositions)objs[0]).rawValue()), // from position
            Converter.toBytes(((EPositions)objs[1]).rawValue()), // to position
            Converter.toBytes(((EPieces)objs[2]).rawValue()), // piece type
            Converter.toBytes(((EPieces)objs[3]).rawValue()), // enemy's piece type
            Converter.toBytes((int)objs[4]), // hour
            Converter.toBytes((int)objs[5]), // min
            Converter.toBytes((int)objs[6]) // sec
        });
    }

    @Override
    public Object translate(byte[] message) {
        return (byte)Wrapper.INSTANCE.unwrap(message)[0] == 1; // success?
    }
    
}
