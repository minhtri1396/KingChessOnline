package main;

import helper.BitMap;
import helper.ThreadPool;
import model.WaitingClient;
import model.type.RoomList;

public class App {
    
    public static final App INSTANCE = new App();
    
    public final RoomList Rooms;
    public final BitMap IDGenerator;
    
    private App() {
        Rooms = new RoomList();
        IDGenerator = new BitMap(1000000);
    }
    
    public void start() {
        ThreadPool.BUILDER.start();
        WaitingClient.asynchronousWaitingOn("127.0.0.1", 3000, 100);
    }
    
}
