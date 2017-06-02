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

public abstract class Piece implements Cloneable
{
    public Piece(String name, String img, int color)
    {
        m_Color = color;
        m_Name = name;
        m_PathImg = img;
    }
    
    public Piece Clone() throws CloneNotSupportedException
    {
        return (Piece)this.clone();
    }
    
    private int m_Color;
    private String m_Name;
    private String m_PathImg;
    protected ArrayList<Cell> Movable = new ArrayList<>();

    public int getColor()
    {
        return m_Color;
    }

    public void setColor(int m_Color)
    {
        this.m_Color = m_Color;
    }

    public String getName()
    {
        return m_Name;
    }

    public void setName(String m_Name)
    {
        this.m_Name = m_Name;
    }

    public String getPathImg()
    {
        return m_PathImg;
    }

    public void setPathImg(String m_PathImg)
    {
        this.m_PathImg = m_PathImg;
    }
    
    protected abstract ArrayList<Cell> SuggestMove(Cell[][] board, Coordinate coord);
}
