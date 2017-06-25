package model;

public class IResult {
    public interface ResponseReceiver {
        void receiveResult(Object obj);
    }
}
