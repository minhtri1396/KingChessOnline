package model.datatype;

import java.util.concurrent.atomic.AtomicInteger;

public class Room {
    
    private static final int MAX_LIVE_TIME = 60; // 60 seconds
    
    private final int id;
    private String name;
    private byte numberPlayers;
    private final AtomicInteger restTime; // if restTime == 0, the room will be detroyed

    public Room(int id, String name) {
        this.id = id;
        this.name = name;
        this.restTime = new AtomicInteger(MAX_LIVE_TIME);
    }
    
    public void resetRestTime() {
        restTime.set(MAX_LIVE_TIME);
    }
    
    public void decreaseRestTime() {
        restTime.decrementAndGet();
    }
    
    public int getRestTime() {
        return restTime.get();
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getNumberPlayers() {
        return numberPlayers;
    }

    public void setNumberPlayers(byte numberPlayers) {
        this.numberPlayers = numberPlayers;
    }
    
}
