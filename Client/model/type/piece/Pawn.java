package model.type.piece;

import model.type.enumeration.EPieceColors;
import model.type.enumeration.EPieces;
import model.type.enumeration.EPositions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.type.Board;
import model.type.Point;

public class Pawn extends Piece {

    private boolean isFirstMoving;
    
    private Pawn(EPieces type, EPositions position, EPieceColors color, Board board, String imagePath) {
        super(type, position, color, board, imagePath);
        isFirstMoving = true;
    }
    
    public static List<Piece> make(EPieceColors color, Board board) {
        if (color == EPieceColors.BLACK) {
            return Arrays.asList(new Piece[] {
                new Pawn(EPieces.PAWN, EPositions.A7, color, board, "/pieces/black_pawn.png"),
                new Pawn(EPieces.PAWN, EPositions.B7, color, board, "/pieces/black_pawn.png"),
                new Pawn(EPieces.PAWN, EPositions.C7, color, board, "/pieces/black_pawn.png"),
                new Pawn(EPieces.PAWN, EPositions.D7, color, board, "/pieces/black_pawn.png"),
                new Pawn(EPieces.PAWN, EPositions.E7, color, board, "/pieces/black_pawn.png"),
                new Pawn(EPieces.PAWN, EPositions.F7, color, board, "/pieces/black_pawn.png"),
                new Pawn(EPieces.PAWN, EPositions.G7, color, board, "/pieces/black_pawn.png"),
                new Pawn(EPieces.PAWN, EPositions.H7, color, board, "/pieces/black_pawn.png")
            });
        }
        
        return Arrays.asList(new Piece[] {
            new Pawn(EPieces.PAWN, EPositions.A2, color, board, "/pieces/white_pawn.png"),
            new Pawn(EPieces.PAWN, EPositions.B2, color, board, "/pieces/white_pawn.png"),
            new Pawn(EPieces.PAWN, EPositions.C2, color, board, "/pieces/white_pawn.png"),
            new Pawn(EPieces.PAWN, EPositions.D2, color, board, "/pieces/white_pawn.png"),
            new Pawn(EPieces.PAWN, EPositions.E2, color, board, "/pieces/white_pawn.png"),
            new Pawn(EPieces.PAWN, EPositions.F2, color, board, "/pieces/white_pawn.png"),
            new Pawn(EPieces.PAWN, EPositions.G2, color, board, "/pieces/white_pawn.png"),
            new Pawn(EPieces.PAWN, EPositions.H2, color, board, "/pieces/white_pawn.png")
        });
    }
    
    @Override
    public void setPosition(EPositions position) {
        super.setPosition(position);
        isFirstMoving = false;
    }

    @Override
    public ArrayList<EPositions> suggestMoving() {
        ArrayList<EPositions> validPositions = new ArrayList<>();
        
        Point pos = EPositions.convertPosition(super.position);
        
        int coef = super.color == EPieceColors.WHITE ? -1 : 1;
        if (super.addAndVerifyPosition(
                EPositions.getPosition(pos.getY() + coef, pos.getX()),
                validPositions,
                false)) {
            if (isFirstMoving) {
                int jumpCoef = super.color == EPieceColors.WHITE ? -2 : 2;
                super.addAndVerifyPosition(
                        EPositions.getPosition(pos.getY() + jumpCoef, pos.getX()),
                        validPositions
                );
            }
        }
        
        // Check positions where it can attack
        Piece piece = super.board.getPiece(EPositions.getPosition(pos.getY() + coef, pos.getX() + 1));
        if (piece != null && piece.color != super.color) {
            validPositions.add(EPositions.getPosition(pos.getY() + coef, pos.getX() + 1));
        }
        
        piece = super.board.getPiece(EPositions.getPosition(pos.getY() + coef, pos.getX() - 1));
        if (piece != null  && piece.color != super.color) {
            validPositions.add(EPositions.getPosition(pos.getY() + coef, pos.getX() - 1));
        }
        
        return validPositions;
    }
    
}
