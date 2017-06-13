package model.type.enumeration;

public enum EPieces {
    
    KING(0), QUEEN(1), ROOK(2), BISHOP(3), KNIGHT(4), PAWN(5);
    
    private final int id;
    private EPieces(int id) {
        this.id = id;
    }
    
    public int rawValue() {
        return this.id;
    }
    
    public static EPieces getPiece(int rawValue) {
        EPieces[] values = EPieces.values();
        if (rawValue < 0 || rawValue >= values.length) {
            return null;
        }
        return values[rawValue];
    }
    
}
