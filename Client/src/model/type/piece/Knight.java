package model.type.piece;

import model.type.enumeration.EPieceColors;
import model.type.enumeration.EPieces;
import model.type.enumeration.EPositions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.type.Board;
import model.type.Point;

public class Knight extends Piece {
    
    private static final Point[] aroundCoefficient = new Point[] {
        new Point(-1, -2),
        new Point(1, -2),
        new Point(2, -1),
        new Point(2, 1),
        new Point(1, 2),
        new Point(-1, 2),
        new Point(-2, 1),
        new Point(-2, -1)
    };
    
    private Knight(EPieces type, EPositions position, EPieceColors color, Board board, String imagePath) {
        super(type, position, color, board, imagePath);
    }
    
    public static List<Piece> make(EPieceColors color, Board board) {
        if (color == EPieceColors.BLACK) {
            return Arrays.asList(new Piece[] {
                new Knight(EPieces.KNIGHT, EPositions.B8, color, board, "/pieces/black_knight.png"),
                new Knight(EPieces.KNIGHT, EPositions.G8, color, board, "/pieces/black_knight.png")
            });
        }
        
        return Arrays.asList(new Piece[] {
            new Knight(EPieces.KNIGHT, EPositions.B1, color, board, "/pieces/white_knight.png"),
            new Knight(EPieces.KNIGHT, EPositions.G1, color, board, "/pieces/white_knight.png")
        });
    }
    
    @Override
    public ArrayList<EPositions> suggestMoving() {
        ArrayList<EPositions> validPositions = new ArrayList<>();
        
        Point pos = EPositions.convertPosition(super.position);
        for (Point coef : aroundCoefficient) {
            super.addAndVerifyPosition(
                    EPositions.getPosition(pos.getY() + coef.getY(), pos.getX() + coef.getX()),
                    validPositions
            );
        }
        
        return validPositions;
    }
    
    @Override
    public int getHeuristicValue() {
        return 6;
    }
    
}
