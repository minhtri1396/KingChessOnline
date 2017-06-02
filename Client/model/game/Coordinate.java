/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.model.game;

/**
 *
 * @author InNovaTioN
 */
public class Coordinate implements Cloneable
{
    public Coordinate (int row, int col)
    {
        this.row = row;
        this.col = col;
    }
    
    public Coordinate(Coordinate coord)
    {
        this.row = coord.row;
        this.col = coord.col;
    }
    
    public boolean IsBlack()
    {
        return ((row + col) % 2) == 0;
    }
    

    public int row, col;
}
