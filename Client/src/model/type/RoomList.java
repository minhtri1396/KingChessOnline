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

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Room[] getRooms() {
        return rooms.toArray(new Room[0]);
    }
    
    public void addRoom(Room room) {
        rooms.add(room);
    }
    
    public int size() {
        return rooms.size();
    }
    
    public Room[] getRoomOnPage(int page, int nElementPerPage) {
        int nRooms = rooms.size();
        int nPages = (int) Math.ceil(nRooms * 1.0 / nElementPerPage);
        
        if (page < 1 || page > nPages) {
            return null;
        }
        
        int iFrom = (page - 1) * nElementPerPage;
        int iTo = iFrom + nElementPerPage > nRooms ? nRooms : iFrom + nElementPerPage;
        
        Room[] roomsOnPage = new Room[iTo - iFrom];
        
        int k = 0;
        for (int i = iFrom; i < iTo; ++i) {
            roomsOnPage[k++] = this.rooms.get(i);
        }
        
        return roomsOnPage;
    }
    
    public int getNumberPages(int nElementPerPage) {
        return (int)Math.ceil(rooms.size() * 1.0 / nElementPerPage);
    }
    
}
