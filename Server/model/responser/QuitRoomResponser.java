package model.responser;

public class QuitRoomResponser implements Responser {
    public static final QuitRoomResponser Instance = new QuitRoomResponser();
    
    @Override
    public byte[] makeResponseContentFor(byte[] message) {
        return null;
    }
}
