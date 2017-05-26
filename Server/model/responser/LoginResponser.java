package model.responser;

public class LoginResponser implements Responser {
    public static final LoginResponser Instance = new LoginResponser();
    
    @Override
    public byte[] makeResponseContentFor(byte[] message) {
        return null;
    }
}
