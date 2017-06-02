package model.datatype.piece;

import java.util.ArrayList;
import model.game.Cell;
import model.game.Coordinate;

public class Rook extends Piece
{
    public Rook(String name, String img, int color)
    {
        super(name, img, color);
    }
    
    @Override
    public ArrayList<Cell> suggestMove(Cell[][] board, Coordinate coord)
    {
        ArrayList<Cell> res = new ArrayList<>();
        
        return res;
    }
}
