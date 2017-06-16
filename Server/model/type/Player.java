package model.type;

public class Player {
    
    private final int id;
    private int level;
    private String name;
    private String ip;
    private int port;

    public Player(int id, String ip, int port) {
        this.id = id;
        this.ip = ip;
        this.port = port;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    public int getId() {
        return id;
    }
    
    public int getLevel() {
        return level;
    }
    
    public String getName() {
        return name;
    }
    
    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

}
