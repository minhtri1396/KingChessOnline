package model.responser;

public class JoinRoomResponser implements Responser {
    public static final JoinRoomResponser Instance = new JoinRoomResponser();
    
    @Override
    public byte[] makeResponseContentFor(byte[] message) {
        return null;
    }
}
