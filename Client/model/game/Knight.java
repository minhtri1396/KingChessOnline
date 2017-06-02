/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.model.game;

import java.util.ArrayList;

/**
 *
 * @author InNovaTioN
 */
public class Knight extends Piece
{
    public Knight(String name, String img, int color)
    {
        super(name, img, color);
    }
    
    public ArrayList<Cell> SuggestMove(Cell[][] board, Coordinate coord)
    {
        ArrayList<Cell> res = new ArrayList<>();
        
        return res;
    }
}
