package model.responser;

public class FinishMatchResponser implements Responser {
    public static final FinishMatchResponser Instance = new FinishMatchResponser();
    
    @Override
    public byte[] makeResponseContentFor(byte[] message) {
        return null;
    }
}
