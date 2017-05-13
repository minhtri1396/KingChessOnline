package model.responser;

public class StartMatchResponser implements Responser {
    public static final StartMatchResponser Instance = new StartMatchResponser();
    
    @Override
    public byte[] makeResponseContentFor(byte[] message) {
        return null;
    }
}
