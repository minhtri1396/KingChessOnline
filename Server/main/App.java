package main;

import model.WaitingClient;
import model.datatype.RoomList;

public class App {
    
    public static final App INSTANCE = new App();
    
    public RoomList rooms;
    
    private App() {
        rooms = new RoomList();
    }
    
    public void start() {
        WaitingClient.asynchronousWaitingOn("127.0.0.1", 3000, 100);
    }
    
}
