package model.type.enumeration;

public enum EPieceColors {
    
    WHITE(0), BLACK(1);
    
    private final int id;
    private EPieceColors(int id) {
        this.id = id;
    }
    
    public int rawValue() {
        return this.id;
    }
    
}
