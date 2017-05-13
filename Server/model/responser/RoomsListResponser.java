package model.responser;

public class RoomsListResponser implements Responser {
    public static final RoomsListResponser Instance = new RoomsListResponser();
    
    @Override
    public byte[] makeResponseContentFor(byte[] message) {
        return null;
    }
}
