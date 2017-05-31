package enumeration;

// The positions on a chess board
public enum EPositions {
    
    A1(0), A2(1), A3(2), A4(3), A5(4), A6(5), A7(6), A8(7),
    B1(0), B2(1), B3(2), B4(3), B5(4), B6(5), B7(6), B8(7),
    C1(0), C2(1), C3(2), C4(3), C5(4), C6(5), C7(6), C8(7),
    D1(0), D2(1), D3(2), D4(3), D5(4), D6(5), D7(6), D8(7),
    E1(0), E2(1), E3(2), E4(3), E5(4), E6(5), E7(6), E8(7),
    F1(0), F2(1), F3(2), F4(3), F5(4), F6(5), F7(6), F8(7),
    G1(0), G2(1), G3(2), G4(3), G5(4), G6(5), G7(6), G8(7),
    H1(0), H2(1), H3(2), H4(3), H5(4), H6(5), H7(6), H8(7);
    
    private final int id;
    private EPositions(int id) {
        this.id = id;
    }
    
    public int rawValue() {
        return this.id;
    }
    
}
