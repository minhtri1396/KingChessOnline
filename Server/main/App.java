package main;

import helper.ThreadPool;
import model.WaitingClient;
import model.type.RoomList;

public class App {
    
    public static final App INSTANCE = new App();
    
    public RoomList Rooms;
    
    private App() {
        Rooms = new RoomList();
    }
    
    public void start() {
        ThreadPool.BUILDER.start();
        WaitingClient.asynchronousWaitingOn("127.0.0.1", 3000, 100);
    }
    
}
