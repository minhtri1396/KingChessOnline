package model.responser;

public class RenewRoomResponser implements Responser {
    public static final RenewRoomResponser Instance = new RenewRoomResponser();
    
    @Override
    public byte[] makeResponseContentFor(byte[] message) {
        return null;
    }
}
