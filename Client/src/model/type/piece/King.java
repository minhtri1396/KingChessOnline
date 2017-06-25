package model.type.piece;

import model.type.enumeration.EPieceColors;
import model.type.enumeration.EPieces;
import model.type.enumeration.EPositions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.type.Board;
import model.type.Point;

public class King extends Piece {
    
    private static final Point[] aroundCoefficient = new Point[] {
        new Point(0, -1),
        new Point(1, -1),
        new Point(1, 0),
        new Point(1, 1),
        new Point(0, 1),
        new Point(-1, 1),
        new Point(-1, 0),
        new Point(-1, -1)
    };
    
    private King(EPieces type, EPositions position, EPieceColors color, Board board, String imagePath) {
        super(type, position, color, board, imagePath);
    }
    
    public static List<Piece> make(EPieceColors color, Board board) {
        if (color == EPieceColors.BLACK) {
            return Arrays.asList(new Piece[] {
                new King(EPieces.KING, EPositions.E8, color, board, "/pieces/black_king.png")
            });
        }
        
        return Arrays.asList(new Piece[] {
            new King(EPieces.KING, EPositions.E1, color, board, "/pieces/white_king.png")
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
        
        // Check special moving
        if (!super.board.isChecked(super.color) && countMoving == 0) {
            EPositions interestedPosition = EPositions.getPosition(pos.getY(), pos.getX() + 1);
            if (super.board.getPiece(interestedPosition) == null && !super.board.isChecked(interestedPosition, super.color)) {
                interestedPosition = EPositions.getPosition(pos.getY(), pos.getX() + 2);
                if (super.board.getPiece(interestedPosition) == null && !super.board.isChecked(interestedPosition, super.color)) {
                    interestedPosition = EPositions.getPosition(pos.getY(), pos.getX() + 3);
                    Piece piece = super.board.getPiece(interestedPosition);
                    if (piece != null && piece.color == super.color && piece.getType() == EPieces.ROOK) {
                        if (piece.countMoving == 0) {
                            validPositions.add(EPositions.getPosition(pos.getY(), pos.getX() + 2));
                        }
                    }
                }
            }
            
            interestedPosition = EPositions.getPosition(pos.getY(), pos.getX() - 1);
            if (super.board.getPiece(interestedPosition) == null && !super.board.isChecked(interestedPosition, super.color)) {
                interestedPosition = EPositions.getPosition(pos.getY(), pos.getX() - 2);
                if (super.board.getPiece(interestedPosition) == null && !super.board.isChecked(interestedPosition, super.color)) {
                    interestedPosition = EPositions.getPosition(pos.getY(), pos.getX() - 4);
                    Piece piece = super.board.getPiece(interestedPosition);
                    if (piece != null && piece.color == super.color && piece.getType() == EPieces.ROOK) {
                        if (piece.countMoving == 0) {
                            validPositions.add(EPositions.getPosition(pos.getY(), pos.getX() - 2));
                        }
                    }
                }
            }
        }
        
        return validPositions;
    }
    
    @Override
    protected ArrayList<EPositions> suggestWithoutSpecialMoving() {
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
        return 1000;
    }
    
    @Override
    public EPositions[] isSpecialMoving(EPositions position) {
        Point pos = EPositions.convertPosition(super.position);
        
        if (position == EPositions.getPosition(pos.getY(), pos.getX() + 2)) {
            return new EPositions[] {
                EPositions.getPosition(pos.getY(), pos.getX() + 3),
                EPositions.getPosition(pos.getY(), pos.getX() + 1)
            };
        }
        
        if (position == EPositions.getPosition(pos.getY(), pos.getX() - 2)) {
            return new EPositions[] {
                EPositions.getPosition(pos.getY(), pos.getX() - 4),
                EPositions.getPosition(pos.getY(), pos.getX() - 1)
            };
        }
        
        return null;
    }
    
}
