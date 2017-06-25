package model.type;

import java.util.ArrayList;
import model.type.enumeration.EPieceColors;
import model.type.enumeration.EPositions;
import java.util.List;
import model.type.enumeration.EPieces;
import model.type.piece.Bishop;
import model.type.piece.King;
import model.type.piece.Knight;
import model.type.piece.Pawn;
import model.type.piece.Piece;
import model.type.piece.Queen;
import model.type.piece.Rook;

public class Board {
    
    private Piece[] pieces;
    private ArrayList<ArrayList<Piece>> checkingPieces; // pieces which are checking the enemy's king
    private Piece[] kings; // the black and white kings
    private EPositions[] prevKingPos;
    public final int[] Moving;
    
    public static Board create() {
        Board board = Board.createEmpty();
        board.init();
        return board;
    }
    
    public static Board createEmpty() {
        Board board = new Board();
        return board;
    }
    
    private Board() {
        pieces = new Piece[64];
        Moving = new int[] {1, 1};
    }
    
    private void init() {
        checkingPieces = new ArrayList<>(2);
        checkingPieces.add(new ArrayList<>());
        checkingPieces.add(new ArrayList<>());
        
        kings = new Piece[2];
        prevKingPos = new EPositions[2];
        
        init(EPieceColors.WHITE);
        init(EPieceColors.BLACK);
    }
    
    private void init(EPieceColors color) {
        // Bishop
        List<Piece> initialPieces = Bishop.make(color, this);
        initialPieces.forEach((piece) -> {
            pieces[piece.getPosition().rawValue()] =  piece;
        });
        // King
        initialPieces = King.make(color, this);
        initialPieces.forEach((piece) -> {
            pieces[piece.getPosition().rawValue()] =  piece;
            kings[color.rawValue()] = piece;
            prevKingPos[color.rawValue()] = piece.getPosition();
        });
        // Knight
        initialPieces = Knight.make(color, this);
        initialPieces.forEach((piece) -> {
            pieces[piece.getPosition().rawValue()] =  piece;
        });
        // Pawn
        initialPieces = Pawn.make(color, this);
        initialPieces.forEach((piece) -> {
            pieces[piece.getPosition().rawValue()] =  piece;
        });
        // Queen
        initialPieces = Queen.make(color, this);
        initialPieces.forEach((piece) -> {
            pieces[piece.getPosition().rawValue()] =  piece;
        });
        // Rook
        initialPieces = Rook.make(color, this);
        initialPieces.forEach((piece) -> {
            pieces[piece.getPosition().rawValue()] =  piece;
        });
    }
    
    public Piece getPiece(EPositions position) {
        if (isOutOfBound(position)) {
            return null;
        }
        
        return pieces[position.rawValue()];
    }
    
    public void move(EPositions fromPos, EPositions toPos) {
        Piece piece = pieces[fromPos.rawValue()];
        pieces[fromPos.rawValue()] = null;
        piece.setPosition(toPos);
        
        if (piece.getType() == EPieces.KING) {
            prevKingPos[piece.getColor().rawValue()] = fromPos;
        }
        
        if (pieces[toPos.rawValue()] != null) {
            checkingPieces.get(piece.getColor().rawValue()).remove(pieces[toPos.rawValue()]);
        }
        
        pieces[toPos.rawValue()] = piece;
        
        verifyKing();
    }
    
    public void moveByAI(EPositions fromPos, EPositions toPos) {
        Piece piece = pieces[fromPos.rawValue()];
        pieces[fromPos.rawValue()] = null;
        piece.setPosition(toPos);
        if (null != pieces[toPos.rawValue()])
        {
            if (pieces[toPos.rawValue()].getType() == EPieces.KING)
            {
                kings[pieces[toPos.rawValue()].getColor().rawValue()] = null;
            }
        }
        pieces[toPos.rawValue()] = piece;
    }
    
    public void verifyKing() {
        checkingPieces.get(EPieceColors.WHITE.rawValue()).clear();
        checkingPieces.get(EPieceColors.BLACK.rawValue()).clear();
        for (Piece piece : pieces) {
            if (piece != null) {
                if (piece.getColor() == EPieceColors.WHITE) {
                    if (piece.isCanAttack(kings[EPieceColors.BLACK.rawValue()].getPosition())) {
                        checkingPieces.get(EPieceColors.BLACK.rawValue()).add(piece);
                    }
                } else { // EPieceColors.BLACK
                    if (kings[EPieceColors.WHITE.rawValue()] == null) {
                        System.out.println("NOT HAVE KING");
                    }
                    if (piece.isCanAttack(kings[EPieceColors.WHITE.rawValue()].getPosition())) {
                        checkingPieces.get(EPieceColors.WHITE.rawValue()).add(piece);
                    }
                }
            }
        }
    }
    
    public boolean isOutOfBound(EPositions position) {
        return position == null; // this condition is checked in EPositions enum
    }
    
    public Board cloneForAI() {
        int nPieces = pieces.length;
        Board board = Board.createEmpty();
        Piece[] clonedPieces = new Piece[nPieces];
        
        // Clone kings and pieces
        board.kings = new Piece[2];
        board.pieces = clonedPieces;
        for (int iPiece = 0; iPiece < nPieces; ++iPiece) {
            if (null != pieces[iPiece]) {
                clonedPieces[iPiece] = pieces[iPiece].clonePiece();
                clonedPieces[iPiece].setBoard(board);
                if (pieces[iPiece].getType() == EPieces.KING) {
                    board.kings[pieces[iPiece].getColor().rawValue()] = clonedPieces[iPiece];
                }
            }
        }
        
        // Clone prevKingPos
        board.prevKingPos = this.prevKingPos.clone();
        
        // Clone Moving
        for (int iMoving = 0; iMoving < this.Moving.length; ++iMoving) {
            board.Moving[iMoving] = this.Moving[iMoving];
        }
        
        // Clone checkingPieces
        board.checkingPieces = new ArrayList<>(2);
        board.checkingPieces.add(new ArrayList<>());
        board.checkingPieces.add(new ArrayList<>());
        
        if (board.kings[EPieceColors.WHITE.rawValue()] != null
                && board.kings[EPieceColors.BLACK.rawValue()] != null) {
            board.verifyKing();
        }
        
        return board;
    }
    
    public boolean isChecked(EPieceColors color) {
        return 0 != checkingPieces.get(color.rawValue()).size();
    }
    
    public boolean isChecked(EPositions position, EPieceColors color) {
        boolean isCanCheck = false;
        
        Piece king = kings[color.rawValue()];
        pieces[king.getPosition().rawValue()] = null;
        
        for (Piece piece : pieces) {
            if (piece != null && piece.getColor() != color) {
                if (piece.isCanAttack(position)) {
                    isCanCheck = true;
                    break;
                }
            }
        }
        
        pieces[king.getPosition().rawValue()] = king;
        
        return isCanCheck;
    }
    
    public EPositions getKingPosition(EPieceColors color) {
        return kings[color.rawValue()].getPosition();
    }
    
    public EPositions getPrevKingPosition(EPieceColors color) {
        return prevKingPos[color.rawValue()];
    }
    
    public void replace(EPieces type, EPositions pos, EPieceColors color) {
        Piece piece = pieces[pos.rawValue()];
        
        Piece newPiece;
        switch (type) {
            case BISHOP:
                newPiece = Bishop.make(color, this).get(0);
                break;
            case KING:
                newPiece = King.make(color, this).get(0);
                break;
            case KNIGHT:
                newPiece = Knight.make(color, this).get(0);
                break;
            case PAWN: // shouldn't use this case
                newPiece = Pawn.make(color, this).get(0);
                break;
            case QUEEN:
                newPiece = Queen.make(color, this).get(0);
                break;
            default: // ROOK
                newPiece = Rook.make(color, this).get(0);
        }
        
        newPiece.setPosition(pos);
        newPiece.setCountMoving(piece.getCountMoving());
        newPiece.setnMoving(piece.getnMoving());
        
        pieces[pos.rawValue()] = newPiece;
        
        verifyKing();
    }

    @Override
    public String toString()
    {
        String res = "";
        for (int i = 0; i < 8; ++i)
        {
            for (int j = 0; j < 8; ++j)
            {
                if (null != this.pieces[i * 8 +j])
                {
                    if (this.pieces[i * 8 + j].getType() == EPieces.PAWN)
                    {
                        if (this.pieces[i * 8 + j].getColor() == EPieceColors.WHITE)
                            res += "p ";
                        else
                            res += "P ";
                    } else if (this.pieces[i * 8 + j].getType() == EPieces.ROOK)
                    {
                        if (this.pieces[i * 8 + j].getColor() == EPieceColors.WHITE)
                            res += "r ";
                        else
                            res += "R ";
                    } else if (this.pieces[i * 8 + j].getType() == EPieces.KNIGHT)
                    {
                        if (this.pieces[i * 8 + j].getColor() == EPieceColors.WHITE)
                            res += "k ";
                        else
                            res += "K ";
                    } else if (this.pieces[i * 8 + j].getType() == EPieces.BISHOP)
                    {
                        if (this.pieces[i * 8 + j].getColor() == EPieceColors.WHITE)
                            res += "b ";
                        else
                            res += "B ";
                    } else if (this.pieces[i * 8 + j].getType() == EPieces.QUEEN)
                    {
                        if (this.pieces[i * 8 + j].getColor() == EPieceColors.WHITE)
                            res += "q ";
                        else
                            res += "Q ";
                    } else if (this.pieces[i * 8 + j].getType() == EPieces.KING)
                    {
                        if (this.pieces[i * 8 + j].getColor() == EPieceColors.WHITE)
                            res += "i ";
                        else
                            res += "I ";
                    }
                }
                else
                    res += "- ";
            }
            res += "\n";
        }
        return res;
    }

    @Override
    public int hashCode()
    {
        return this.toString().hashCode();
    }

    public boolean isKingSurvive(EPieceColors color)
    {
        return kings[color.rawValue()] != null;
    }
}
