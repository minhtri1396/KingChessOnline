package model.type;

import java.util.ArrayList;

public class RoomList {
    
    private long timestamp;
    private final ArrayList<Room> rooms;
    
    public RoomList() {
        timestamp = 0;
        rooms = new ArrayList<>();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public Room[] getRooms() {
        return rooms.toArray(new Room[0]);
    }
    
    public void addRoom(Room room) {
        rooms.add(room);
    }
    
}
