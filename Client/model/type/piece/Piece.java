package model.type.piece;

import model.type.enumeration.EPieceColors;
import model.type.enumeration.EPieces;
import model.type.enumeration.EPositions;
import helper.ErrorLogger;
import java.util.ArrayList;
import model.type.Board;

public abstract class Piece implements Cloneable
{
    
    protected String imagePath;
    protected EPieces type;
    protected EPositions position;
    protected EPieceColors color;
    protected Board board;
    
    public Piece() {}
    
    protected Piece(EPieces type, EPositions position, EPieceColors color, Board board, String imagePath) {
        this.type = type;
        this.position = position;
        this.color = color;
        this.board = board;
        this.imagePath = imagePath;
    }
    
    public Piece clonePiece() {
        try {
            return (Piece)super.clone();
        } catch (CloneNotSupportedException ex) {
            ErrorLogger.log(Piece.class, ex);
        }
        return null;
    }
    
    public EPieces getType() {
        return type;
    }
    
    public void setPosition(EPositions position) {
        this.position = position;
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
    
    // Position param will be added to validPositions if it is the valid position
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
    
    public abstract ArrayList<EPositions> suggestMoving();
}
