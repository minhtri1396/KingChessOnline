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
    
    private Pawn(EPieces type, EPositions position, EPieceColors color, Board board, String imagePath) {
        super(type, position, color, board, imagePath);
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
            if (super.nMoving == 0) { // the first moving
                int jumpCoef = super.color == EPieceColors.WHITE ? -2 : 2;
                if (board.getPiece(EPositions.getPosition(pos.getY() + jumpCoef, pos.getX())) == null) {
                    super.addAndVerifyPosition(
                            EPositions.getPosition(pos.getY() + jumpCoef, pos.getX()),
                            validPositions
                    );
                }
            }
        }
        
        // Check positions where it can attack
        Piece piece = super.board.getPiece(EPositions.getPosition(pos.getY() + coef, pos.getX() + 1));
        if (piece != null && piece.color != super.color) {
            validPositions.add(EPositions.getPosition(pos.getY() + coef, pos.getX() + 1));
        } else if (piece == null) { // check special moving
            piece = super.board.getPiece(EPositions.getPosition(pos.getY(), pos.getX() + 1));
            if (piece != null && piece.color != super.color && piece.getType() == EPieces.PAWN && piece.countMoving == 1) {
                if (super.color == EPieceColors.WHITE && super.board.Moving[super.color.rawValue()] - 1 == piece.nMoving) {
                    validPositions.add(EPositions.getPosition(pos.getY() + coef, pos.getX() + 1));
                } else if (super.color == EPieceColors.BLACK && super.board.Moving[super.color.rawValue()] == piece.nMoving) {
                    validPositions.add(EPositions.getPosition(pos.getY() + coef, pos.getX() + 1));
                }
            }
        }
        
        piece = super.board.getPiece(EPositions.getPosition(pos.getY() + coef, pos.getX() - 1));
        if (piece != null  && piece.color != super.color) {
            validPositions.add(EPositions.getPosition(pos.getY() + coef, pos.getX() - 1));
        } else if (piece == null) { // check special moving
            piece = super.board.getPiece(EPositions.getPosition(pos.getY(), pos.getX() - 1));
            if (piece != null && piece.color != super.color && piece.getType() == EPieces.PAWN && piece.countMoving == 1) {
                if (super.color == EPieceColors.WHITE && super.board.Moving[super.color.rawValue()] - 1 == piece.nMoving) {
                    validPositions.add(EPositions.getPosition(pos.getY() + coef, pos.getX() - 1));
                } else if (super.color == EPieceColors.BLACK && super.board.Moving[super.color.rawValue()] == piece.nMoving) {
                    validPositions.add(EPositions.getPosition(pos.getY() + coef, pos.getX() - 1));
                }
            }
        }
        
        return validPositions;
    }
    
    @Override
    public int getHeuristicValue()
    {
        Point p = EPositions.convertPosition(super.position);
        if (super.color == EPieceColors.BLACK) // computer
        {
            if (4 > p.getX()) return 1;
            else if (6 > p.getX()) return 3;
            else if (6 == p.getX()) return 10;
            else return 30;
        }
        else
        {
            if (3 > p.getX()) return 1;
            else if (1 > p.getX()) return 3;
            else if (1 == p.getX()) return 10;
            else return 30;
        }
    }
    
    @Override
    public boolean isCanAttack(EPositions position) {
        Point pos = EPositions.convertPosition(super.position);
        int coef = super.color == EPieceColors.WHITE ? -1 : 1;
        
        return (position == EPositions.getPosition(pos.getY() + coef, pos.getX() + 1))
                || (position == EPositions.getPosition(pos.getY() + coef, pos.getX() - 1));
    }
    
    @Override
    public EPositions[] isSpecialMoving(EPositions position) {
        Point pos = EPositions.convertPosition(super.position);
        int coef = super.color == EPieceColors.WHITE ? -1 : 1;
        int enemyCoef = 0;
        
        if (position == EPositions.getPosition(pos.getY() + coef, pos.getX() + 1)) {
            enemyCoef = 1;
        }
        if (position == EPositions.getPosition(pos.getY() + coef, pos.getX() - 1)) {
            enemyCoef = -1;
        }
        
        if (enemyCoef != 0) {
            if (super.board.getPiece(position) == null) { // this moving is special
                return new EPositions[] {
                    EPositions.getPosition(pos.getY(), pos.getX() + enemyCoef),
                    EPositions.getPosition(pos.getY() + coef, pos.getX() + enemyCoef)
                };
            }
        }
        
        return null;
    }
    
    public boolean isCanPromote() {
        return isCanPromote(super.position);
    }
    
    public boolean isCanPromote(EPositions pos) {
        if (super.color == EPieceColors.WHITE) {
            return pos == EPositions.A8 || pos == EPositions.B8
                    || pos == EPositions.C8 || pos == EPositions.D8
                    || pos == EPositions.E8 || pos == EPositions.F8
                    || pos == EPositions.G8 || pos == EPositions.H8;
        }
        // EPieceColors.BLACK
        return pos == EPositions.A1 || pos == EPositions.B1
                || pos == EPositions.C1 || pos == EPositions.D1
                || pos == EPositions.E1 || pos == EPositions.F1
                || pos == EPositions.G1 || pos == EPositions.H1;
    }
}
