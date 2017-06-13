package model.type;

import java.util.Map;
import java.util.TreeMap;

public class RoomList {
    
    private long timestamp;
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
    
    public boolean updateRoom(Room room) {
        Object[] content;
        boolean isSuccess = false;
        synchronized(rooms) {
            if ((content = rooms.get(room.getId())) != null) {
                Room oldRoom = (Room)content[0]; // 0: room, 1: admin
                if (!oldRoom.getName().equals(room.getName())) {
                    oldRoom.setName(room.getName());
                    timestamp = System.currentTimeMillis();
                }
                
                if (oldRoom.getNumberPlayers() != room.getNumberPlayers()) {
                    oldRoom.setNumberPlayers(room.getNumberPlayers());
                    timestamp = System.currentTimeMillis();
                }
                
                isSuccess = true;
            }
        }
        return isSuccess;
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
    
    public void removeRoom(int id) {
        synchronized(rooms) {
            rooms.remove(id);
            timestamp = System.currentTimeMillis();
        }
    }
    
}
