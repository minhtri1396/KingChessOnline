package model.type;

import model.type.enumeration.EPieceColors;
import model.type.enumeration.EPositions;
import java.util.List;
import model.type.piece.Bishop;
import model.type.piece.King;
import model.type.piece.Knight;
import model.type.piece.Pawn;
import model.type.piece.Piece;
import model.type.piece.Queen;
import model.type.piece.Rook;

public class Board {
    
    private Piece[] pieces;
    
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
    }
    
    private void init() {
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
        pieces[toPos.rawValue()] = piece;
    }
    
    public boolean isOutOfBound(EPositions position) {
        return position == null; // this condition is checked in EPositions enum
    }
    
    public Board cloneBoard() {
        int nPieces = pieces.length;
        Board board = Board.createEmpty();
        Piece[] clonedPieces = new Piece[nPieces];
        board.pieces = clonedPieces;
        
        for (int iPiece = 0; iPiece < nPieces; ++iPiece) {
            if (null != pieces[iPiece]) {
                clonedPieces[iPiece] = pieces[iPiece].clonePiece();
                clonedPieces[iPiece].setBoard(board);
            }
        }
        
        return board;
    }
    
}
