/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.viewer;

import Client.model.game.Board;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author InNovaTioN
 */
public class BoardGUI extends JFrame
{
    private Board board = new Board();
    public BoardGUI()
    {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        InitComponent();
    }
    
    private void InitComponent()
    {
        this.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        
        //init board panel
        jPanelBoard = new JPanel(new GridLayout(8, 8, 2, 2));
        jPanelBoard.setMinimumSize(new Dimension(500, 550));
        board.Draw(jPanelBoard);
        
        //init control group
        jPanelControl = new JPanel();
        jPanelControl.setMinimumSize(new Dimension(580, 550));
        
        //split board and control
        jSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jPanelBoard, jPanelControl);
        this.add(jSplit);
    }
    
    private int WIDTH = 1110;
    private int HEIGHT = 550;
    private JSplitPane jSplit;
    private JPanel jPanelBoard;
    private JPanel jPanelControl;
}
