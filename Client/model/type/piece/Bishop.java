package model.type.piece;

import model.type.enumeration.EPieceColors;
import model.type.enumeration.EPieces;
import model.type.enumeration.EPositions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.type.Board;
import model.type.Point;

public class Bishop extends Piece {
    
    private Bishop(EPieces type, EPositions position, EPieceColors color, Board board, String imagePath) {
        super(type, position, color, board, imagePath);
    }
    
    public static List<Piece> make(EPieceColors color, Board board) {
        if (color == EPieceColors.BLACK) {
            return Arrays.asList(new Piece[] {
                new Bishop(EPieces.BISHOP, EPositions.C8, color, board, "/pieces/black_bishop.png"),
                new Bishop(EPieces.BISHOP, EPositions.F8, color, board, "/pieces/black_bishop.png")
            });
        }
        
        return Arrays.asList(new Piece[] {
            new Bishop(EPieces.BISHOP, EPositions.C1, color, board, "/pieces/white_bishop.png"),
            new Bishop(EPieces.BISHOP, EPositions.F1, color, board, "/pieces/white_bishop.png")
        });
    }

    @Override
    public ArrayList<EPositions> suggestMoving() {
        ArrayList<EPositions> validPositions = new ArrayList<>();
        
        // Check top left direction
        this.setPositions(validPositions, -1, -1);
        // Check bottom right direction
        this.setPositions(validPositions, -1, 1);
        // Check top right direction
        this.setPositions(validPositions, 1, 1);        
        // Check bottom left direction
        this.setPositions(validPositions, 1, -1);
        
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
    
}
