package model.requester;

import controller.MessagesManager;
import helper.Converter;
import helper.Wrapper;

public class LoginRequester implements Requester {
    public static final NewRoomRequester Instance = new NewRoomRequester();
    
    @Override
    public byte[] getRequestContent(Object values) {
        return Wrapper.INSTANCE.wrap(new Object[] {
            Converter.toBytes(MessagesManager.MessageType.LOGIN.rawValue())
        });
    }

    @Override
    public Object translate(byte[] message) {
        return null;
    }
}
