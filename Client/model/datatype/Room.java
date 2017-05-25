package model.datatype;

public class Room {
    
    private final int id;
    private String name;
    private byte numberPlayers;

    public Room(int id, String name) {
        this.id = id;
        this.name = name;
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
