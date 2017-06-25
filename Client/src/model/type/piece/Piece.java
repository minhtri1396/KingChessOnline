package model.type.piece;

import helper.ErrorLogger;
import model.type.enumeration.EPieceColors;
import model.type.enumeration.EPieces;
import model.type.enumeration.EPositions;
import java.util.ArrayList;
import model.type.Board;

public abstract class Piece implements Cloneable
{
    protected String imagePath;
    protected EPieces type;
    protected EPositions position;
    protected EPieceColors color;
    protected Board board;
    
    protected int nMoving;
    protected int countMoving;
    
    public Piece() {}
    
    protected Piece(EPieces type, EPositions position, EPieceColors color, Board board, String imagePath) {
        this.type = type;
        this.position = position;
        this.color = color;
        this.board = board;
        this.imagePath = imagePath;
        this.nMoving = 0;
        this.countMoving = 0;
    }
    
    public Piece clonePiece() {
        try {
            return (Piece)this.clone();
        } catch (CloneNotSupportedException ex) {
            ErrorLogger.log(this.getClass(), ex);
        }
        
        return null;
    }
    
    public void setBoard(Board board) {
        this.board = board;
    }
    
    public EPieces getType() {
        return type;
    }
    
    public void setPosition(EPositions position) {
        this.position = position;
        this.nMoving = this.board.Moving[this.color.rawValue()]++;
        ++this.countMoving;
    }
    
    public EPositions getPosition() {
        return position;
    }
    
    public EPieceColors getColor() {
        return color;
    }
    
    public String getImagePath() {
        return imagePath;
    }
    
    public int getnMoving() {
        return nMoving;
    }

    public void setnMoving(int nMoving) {
        this.nMoving = nMoving;
    }

    public int getCountMoving() {
        return countMoving;
    }

    public void setCountMoving(int countMoving) {
        this.countMoving = countMoving;
    }
    
    // Position param will be added to validPositions if it is the valid position
    protected boolean addAndVerifyPosition(EPositions position, ArrayList<EPositions> validPositions) {
        return addAndVerifyPosition(position, validPositions, true);
    }
    protected boolean addAndVerifyPosition(EPositions position, ArrayList<EPositions> validPositions, boolean isCanAtack) {
        if (!board.isOutOfBound(position)) {
            Piece piece = board.getPiece(position);
            if (piece == null) {
                validPositions.add(position);
                return true;
            }
            
            if (isCanAtack && this.color != piece.color) {
                validPositions.add(position);
            }
        }
        
        return false;
    }
    
    public boolean isCanAttack(EPositions position) {
        ArrayList<EPositions> validPositions = suggestWithoutSpecialMoving();
        return validPositions.contains(position);
    }
    
    // The pieces which have some special moving should implement this method
    // Return positions which need to move on board (0: fromPos, 1: toPos)
    // Return null if the position param is not a special moving
    public EPositions[] isSpecialMoving(EPositions position) {
        return null;
    }
    
    protected ArrayList<EPositions> suggestWithoutSpecialMoving() {
        return suggestMoving();
    }
    
    public abstract ArrayList<EPositions> suggestMoving();
    public abstract int getHeuristicValue();
}
