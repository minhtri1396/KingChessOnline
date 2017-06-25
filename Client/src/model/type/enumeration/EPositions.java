package model.type.enumeration;

// The positions on a chess board

import model.type.Point;

public enum EPositions {
    
//    A8(0), B8(8), C8(16), D8(24), E8(32), F8(40), G8(48), H8(56),
//    A7(1), B7(9), C7(17), D7(25), E7(33), F7(41), G7(49), H7(57),
//    A6(2), B6(10), C6(18), D6(26), E6(34), F6(42), G6(50), H6(58),
//    A5(3), B5(11), C5(19), D5(27), E5(35), F5(43), G5(51), H5(59),
//    A4(4), B4(12), C4(20), D4(28), E4(36), F4(44), G4(52), H4(60),
//    A3(5), B3(13), C3(21), D3(29), E3(37), F3(45), G3(53), H3(61),
//    A2(6), B2(14), C2(22), D2(30), E2(38), F2(46), G2(54), H2(62),
//    A1(7), B1(15), C1(23), D1(31), E1(39), F1(47), G1(55), H1(63);
    
    A8(0), B8(1), C8(2), D8(3), E8(4), F8(5), G8(6), H8(7),
    A7(8), B7(9), C7(10), D7(11), E7(12), F7(13), G7(14), H7(15),
    A6(16), B6(17), C6(18), D6(19), E6(20), F6(21), G6(22), H6(23),
    A5(24), B5(25), C5(26), D5(27), E5(28), F5(29), G5(30), H5(31),
    A4(32), B4(33), C4(34), D4(35), E4(36), F4(37), G4(38), H4(39),
    A3(40), B3(41), C3(42), D3(43), E3(44), F3(45), G3(46), H3(47),
    A2(48), B2(49), C2(50), D2(51), E2(52), F2(53), G2(54), H2(55),
    A1(56), B1(57), C1(58), D1(59), E1(60), F1(61), G1(62), H1(63);
    
//    A8(0), B8(1), C8(2), D8(3), E8(4), F8(5), G8(6), H8(7),
//    A7(10), B7(11), C7(12), D7(13), E7(14), F7(15), G7(16), H7(17),
//    A6(20), B6(21), C6(22), D6(23), E6(24), F6(25), G6(26), H6(27),
//    A5(30), B5(31), C5(32), D5(33), E5(34), F5(35), G5(36), H5(37),
//    A4(40), B4(41), C4(42), D4(43), E4(44), F4(45), G4(46), H4(47),
//    A3(50), B3(51), C3(52), D3(53), E3(54), F3(55), G3(56), H3(57),
//    A2(60), B2(61), C2(62), D2(63), E2(64), F2(65), G2(66), H2(67),
//    A1(70), B1(71), C1(72), D1(73), E1(74), F1(75), G1(76), H1(77);
    
    private static final EPositions[] values = EPositions.values();
    
    private final int id;
    private EPositions(int id) {
        this.id = id;
    }
    
    public int rawValue() {
        return this.id;
    }
    
    public static EPositions getPosition(int row, int col) {
        if (row < 0 || row > 7 || col < 0 || col > 7) {
            return null;
        }
        
        return values[row * 8 + col];
    }
    
    public static EPositions getPosition(int rawValue) {
        if (rawValue < 0 || rawValue > 63) {
            return null;
        }
        
        return values[rawValue];
    }
    
    public static Point convertPosition(EPositions position) {
        return new Point(
                position.rawValue() % 8,
                position.rawValue() / 8
        );
    }
    
}
