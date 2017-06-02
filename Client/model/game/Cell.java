/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.model.game;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author InNovaTioN
 */

public class Cell extends JPanel implements Cloneable
{
    public Cell()
    {
        
    }
    
    public Cell(Cell cell)
    {
        
    }
    
    public Cell (Coordinate coord, Piece piece)
    {
        if (coord.IsBlack())
            this.setBackground(MyColor.CellBlack);
        else
            this.setBackground(MyColor.CellWhite);
        
        SetPiece(piece);
        m_Coordinate = coord;
    }
    
    public void SetPiece(Piece piece) // add image of piece
    {
        if (null != piece)
        {
            m_Piece = piece;
            ImageIcon img = new ImageIcon(this.getClass().getResource(piece.getPathImg()));
            m_Img = new JLabel(img);
            this.add(m_Img);
        }
    }
    
    public void RemovePiece() // remove image of piece
    {
        m_Piece = null;
        this.remove(m_Img);
    }
    
    public boolean IsSelected()
    {
        return m_IsSelected;
    }
    
    public void Select()
    {
        this.setBorder(BorderFactory.createLoweredBevelBorder());
        this.m_IsSelected = true;
    }
    
    public void UnSelect()
    {
        this.setBorder(null);
        this.m_IsSelected = false;
    }

    public void MarkSuggest()
    {
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.m_IsMarked = true;
    }
    
    public void UnmarkSuggest()
    {
        this.setBorder(null);
        this.m_IsMarked = false;
    }
    
    
    public Piece getPiece()
    {
        return m_Piece;
    }

    public void setPiece(Piece m_Piece)
    {
        this.m_Piece = m_Piece;
    }

    public Coordinate getCoordinate()
    {
        return m_Coordinate;
    }

    public void setCoordinate(Coordinate m_Coordinate)
    {
        this.m_Coordinate = m_Coordinate;
    }
    
    
    private static final long serialVersionUID = 1L;
    private JLabel m_Img;
    private Piece m_Piece;
    public Coordinate m_Coordinate;
    private boolean m_IsSelected; // for cell has been clicked
    private boolean m_IsMarked; // for movable cell
    
}
