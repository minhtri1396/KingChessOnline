/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.model.game;

import Client.resource.Resource;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author InNovaTioN
 */
public class Board implements MouseListener
{
    public Board()
    {
        
    }
    
    public void Draw(JPanel jpBoard)
    {
        jpBoard.setBorder(BorderFactory.createMatteBorder(20, 20, 20, 20, MyColor.Border));
        
        //draw cell contain piece
        HashMap<Coordinate, String> pieces = InitPiece();
        for (Map.Entry<Coordinate, String> entry : pieces.entrySet()) 
        {
            int color = GamePlayer.BLACK;
            String PieceName;
            if ('+' == entry.getValue().charAt(0))
            {
                color = GamePlayer.WHITE;
                PieceName = entry.getValue().replace("+", "white_");
            }
            else
            {
                PieceName = entry.getValue().replace("-", "black_");
            }
            String imgSource = resource.getResourceString(PieceName);
            Coordinate coord = entry.getKey();
            String TypePiece = "Client.model.game." + entry.getValue().substring(1);
            Class cArg[] = {String.class, String.class, int.class};
            Piece p = null;
            try
            {
                p = (Piece) Class.forName(TypePiece).getDeclaredConstructor(cArg).newInstance(PieceName, imgSource, color);
                
            }
            catch(Exception ex)
            {
                //do nothing
            }
            Cell c = new Cell(coord, p);
            m_Board[coord.row][coord.col] = c;
        }
        for (int i = 0; i < 8; ++i)
        {
            for (int j = 0; j < 8; ++j)
            {
                if (null == m_Board[i][j])
                    m_Board[i][j] = new Cell(new Coordinate(i, j), null);
                m_Board[i][j].addMouseListener(this);
            }
        }
        for (int i = 0; i < 8; ++i)
        {
            for (int j = 0; j < 8; ++j)
            {
                jpBoard.add(m_Board[i][j]);
            }
        }
        
    }
    private HashMap<Coordinate, String> InitPiece()
    {
        for (int i = 0; i < 8; ++i)
        {
            for (int j = 0; j < 8; ++j)
            {
                m_Board[i][j] = null;
            }
        }
        HashMap<Coordinate, String> res = new HashMap<>();
        //king
        res.put(new Coordinate(0, 4), "-King");
        res.put(new Coordinate(7, 4), "+King");
        
        //queen
        res.put(new Coordinate(0, 3), "-Queen");
        res.put(new Coordinate(7, 3), "+Queen");
        
        //bishop
        res.put(new Coordinate(0, 2), "-Bishop");
        res.put(new Coordinate(0, 5), "-Bishop");
        res.put(new Coordinate(7, 2), "+Bishop");
        res.put(new Coordinate(7, 5), "+Bishop");
        
        //knight
        res.put(new Coordinate(0, 1), "-Knight");
        res.put(new Coordinate(0, 6), "-Knight");
        res.put(new Coordinate(7, 1), "+Knight");
        res.put(new Coordinate(7, 6), "+Knight");
        
        //rook
        res.put(new Coordinate(0, 0), "-Rook");
        res.put(new Coordinate(0, 7), "-Rook");
        res.put(new Coordinate(7, 0), "+Rook");
        res.put(new Coordinate(7, 7), "+Rook");
        
        //pawn
        for (int i = 0; i < 8; ++i)
        {
            res.put(new Coordinate(1, i), "-Pawn");
        }
        for (int i = 0; i < 8; ++i)
        {
            res.put(new Coordinate(6, i), "+Pawn");
        }
        return res;
    }
    
    @Override
    public void mouseClicked(MouseEvent e)
    {
        clicked = (Cell)e.getSource();
        if (HasCellSelected)
        {
            if (clicked.IsSelected()) // unselect cell and all suggest cell 
            {
                clicked.UnSelect();
                HasCellSelected = false;
                //remove suggest
                
            }
            else //Click to another cell => unselect previous cell, select new cell, unmark old suggest cell, mark new suggest cell
            {
                SelectedCell.UnSelect();
                clicked.Select();
                HasCellSelected = true;
                SelectedCell = clicked;
                //remove suggest
                
                //generate new suggest
                
            }
        }
        else
        {
            clicked.Select();
            HasCellSelected = true;
            SelectedCell = clicked;
            //generate suggest
        }
    }

    
    private Resource resource = new Resource();
    private Cell m_Board[][] = new Cell[8][8];
    private Cell clicked; // get source of cell has been clicked recently
    private Cell SelectedCell; // capture cell has been selected previous
    private boolean HasCellSelected = false;
    private ArrayList<Cell> MovableCell = new ArrayList<>();
    
    
     @Override
    public void mousePressed(MouseEvent e)
    {
        
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
       
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        
    }
    
    
}
