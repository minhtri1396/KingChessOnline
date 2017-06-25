package main;

import helper.BitMap;
import helper.ThreadPool;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import model.IResult;
import model.WaitingClient;
import model.type.Room;
import model.type.RoomList;

public class App {
    
    public static final App INSTANCE = new App();
    
    public final RoomList Rooms;
    public final BitMap IDGenerator;
    private final Timer roomsCleaner;
    private final Boolean waitingForTimer;
    
    private App() {
        Rooms = new RoomList();
        IDGenerator = new BitMap(1000000);
        waitingForTimer = true;
        roomsCleaner = new Timer(1000, (ActionEvent e) -> { // scan every 1 second
            synchronized(waitingForTimer) {
                Room[] rooms = Rooms.getRooms();
                for (Room room : rooms) {
                    room.decreaseRestTime();
                    if (room.getRestTime() > 0) {
                    } else {
                        Rooms.removeRoom(room.getId());
                    }
                }
            }
        });
    }
    
    public void start(String ip, int port, IResult.ResponseReceiver callback) {
        ThreadPool.BUILDER.start();
        roomsCleaner.start();
        WaitingClient.asynchronousWaitingOn(ip, port, 100, callback);
    }
    
}
