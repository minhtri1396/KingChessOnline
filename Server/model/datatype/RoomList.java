package model.datatype;

import java.util.Map;
import java.util.TreeMap;

public class RoomList {
    
    private Long timestamp;
    private final TreeMap<Integer, Object[]> rooms; // 0: room, 1: room admin (player)
    
    public RoomList() {
        timestamp = new Long(0);
        rooms = new TreeMap<>();
    }

    public long getTimestamp() {
        return timestamp;
    }

    // Shoulde get timestamp before invoke this method
    // to get rooms list and response to client
    public Room[] getRooms() {
        Room[] roomAsList = new Room[rooms.size()];
        
        int iRoom = 0;
        for (Map.Entry<Integer, Object[]> room : rooms.entrySet()) {
            roomAsList[iRoom++] = (Room)room.getValue()[0];
        }
        
        return roomAsList;
    }
    
    public void addRoom(Room room, Player admin) {
        synchronized(rooms) {
            rooms.put(room.getId(), new Object[]{
                room,
                admin
            });
            timestamp = System.currentTimeMillis();
        }
    }
    
    public synchronized void removeRoom(int id) {
        synchronized(rooms) {
            rooms.remove(id);
            timestamp = System.currentTimeMillis();
        }
    }
    
}
