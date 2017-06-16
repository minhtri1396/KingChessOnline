package model.type;

import java.util.Map;
import java.util.TreeMap;

public class RoomList {
    
    private long timestamp;
    private final TreeMap<Integer, Object[]> rooms; // 0: room, 1: room admin (player)
    
    public RoomList() {
        timestamp = System.currentTimeMillis();
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
        boolean isSuccess = false;
        Object[] content = getValues(room.getId());
        
        if (content != null) {
            Room oldRoom = (Room)content[0]; // 0: room, 1: admin
            if (!oldRoom.getName().equals(room.getName())) {
                oldRoom.setName(room.getName());
                timestamp = System.currentTimeMillis();
            }

            if (oldRoom.getNumberPlayers() != room.getNumberPlayers()) {
                oldRoom.setNumberPlayers(room.getNumberPlayers());
                timestamp = System.currentTimeMillis();
            }
            oldRoom.resetRestTime();
            isSuccess = true;
        }
        
        return isSuccess;
    }
    
    public void addRoom(Room room, Player admin) {
        synchronized(rooms) {
            rooms.put(room.getId(), new Object[]{
                room,
                admin,
                null
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
    
    public synchronized Room getRoom(int id) {
        Object[] values = getValues(id);
        if (values != null) {
            Room room = (Room)values[0];
            
            if (room.getNumberPlayers() == 2) {
                return null;
            }
            
            return room;
        }
        
        return null;
    }
    
    public Player getAdmin(int id) {
        Object[] values = getValues(id);
        if (values != null) {
            return (Player)values[1];
        }
        
        return null;
    }
    
    private Object[] getValues(int id) {
        Object[] values;
        
        synchronized(rooms) {
            values = rooms.get(id);
        }
        
        return values;
    }
    
}
