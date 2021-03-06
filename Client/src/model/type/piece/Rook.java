package model.type.piece;

import model.type.enumeration.EPieceColors;
import model.type.enumeration.EPieces;
import model.type.enumeration.EPositions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.type.Board;
import model.type.Point;

public class Rook extends Piece
{
    private Rook(EPieces type, EPositions position, EPieceColors color, Board board, String imagePath) {
        super(type, position, color, board, imagePath);
    }
    
    public static List<Piece> make(EPieceColors color, Board board) {
        if (color == EPieceColors.BLACK) {
            return Arrays.asList(new Piece[] {
                new Rook(EPieces.ROOK, EPositions.A8, color, board, "/pieces/black_rook.png"),
                new Rook(EPieces.ROOK, EPositions.H8, color, board, "/pieces/black_rook.png")
            });
        }
        
        return Arrays.asList(new Piece[] {
            new Rook(EPieces.ROOK, EPositions.A1, color, board, "/pieces/white_rook.png"),
            new Rook(EPieces.ROOK, EPositions.H1, color, board, "/pieces/white_rook.png")
        });
    }
    
    @Override
    public ArrayList<EPositions> suggestMoving() {
        ArrayList<EPositions> validPositions = new ArrayList<>();
        
        // Check top direction
        this.setPositions(validPositions, -1, 0);
        // Check bottom direction
        this.setPositions(validPositions, 1, 0);
        // Check right direction
        this.setPositions(validPositions, 0, -1);        
        // Check left direction
        this.setPositions(validPositions, 0, 1);
        
        return validPositions;
    }
    
    private void setPositions(ArrayList<EPositions> validPositions, int coefRow, int coefCol) {
        Point pos = EPositions.convertPosition(super.position);
        while (super.addAndVerifyPosition(
                EPositions.getPosition(pos.getY() + coefRow, pos.getX() + coefCol),
                validPositions)) {
            pos.setY(pos.getY() + coefRow);
            pos.setX(pos.getX() + coefCol);
        }
    }
    
    @Override
    public int getHeuristicValue() {
        return 10;
    }
    
}
