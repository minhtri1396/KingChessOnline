package model.responser;

public class NewRoomResponser implements Responser {
    public static final NewRoomResponser Instance = new NewRoomResponser();
    
    @Override
    public byte[] makeResponseContentFor(byte[] message) {
        return null;
    }
}
