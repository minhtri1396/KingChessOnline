package view.match;

import model.define.ColorDefine;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import view.UIView;

public class UIBoardView extends UIView {
    
    private JPanel boardPanel;
    private JPanel leftIndexs;
    private JPanel topIndexs;
    private JPanel rightIndexs;
    private JPanel bottomIndexs;
    
    public UIBoardView() {
        super();
        super.setBackground(ColorDefine.MATCH_BOARD_BORDER);
        
        addComponents();
        setConstraints();
    }
    private void addComponents() {
        boardPanel = new JPanel(new GridLayout(8, 0));
        this.components.put("BoardPanel", boardPanel);
        this.add(boardPanel);
        
        leftIndexs = new JPanel(new GridLayout(8, 0));
        leftIndexs.setPreferredSize(new Dimension(25, 0));
        leftIndexs.setBackground(ColorDefine.MATCH_BOARD_BORDER);
        this.components.put("LeftIndexs", leftIndexs);
        this.add(leftIndexs);
        
        topIndexs = new JPanel(new GridLayout(0, 8));
        topIndexs.setPreferredSize(new Dimension(0, 25));
        topIndexs.setBackground(ColorDefine.MATCH_BOARD_BORDER);
        this.components.put("TopIndexs", topIndexs);
        this.add(topIndexs);
        
        rightIndexs = new JPanel(new GridLayout(8, 0));
        rightIndexs.setPreferredSize(new Dimension(25, 0));
        rightIndexs.setBackground(ColorDefine.MATCH_BOARD_BORDER);
        this.components.put("RightIndexs", rightIndexs);
        this.add(rightIndexs);
        
        bottomIndexs = new JPanel(new GridLayout(0, 8));
        bottomIndexs.setPreferredSize(new Dimension(0, 25));
        bottomIndexs.setBackground(ColorDefine.MATCH_BOARD_BORDER);
        this.components.put("BottomIndexs", bottomIndexs);
        this.add(bottomIndexs);
    }

    private void setConstraints() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        
        // leftIndexs
        layout.putConstraint(
                SpringLayout.NORTH, leftIndexs,
                25,
                SpringLayout.NORTH, this
        );
        layout.putConstraint(
                SpringLayout.WEST, leftIndexs,
                0,
                SpringLayout.WEST, this
        );
        layout.putConstraint(
                SpringLayout.SOUTH, leftIndexs,
                -25,
                SpringLayout.SOUTH, this
        );
        
        // topIndexs
        layout.putConstraint(
                SpringLayout.NORTH, topIndexs,
                0,
                SpringLayout.NORTH, this
        );
        layout.putConstraint(
                SpringLayout.EAST, topIndexs,
                0,
                SpringLayout.WEST, rightIndexs
        );
        layout.putConstraint(
                SpringLayout.WEST, topIndexs,
                0,
                SpringLayout.EAST, leftIndexs
        );
        
        // rightIndexs
        layout.putConstraint(
                SpringLayout.NORTH, rightIndexs,
                25,
                SpringLayout.NORTH, this
        );
        layout.putConstraint(
                SpringLayout.EAST, rightIndexs,
                0,
                SpringLayout.EAST, this
        );
        layout.putConstraint(
                SpringLayout.SOUTH, rightIndexs,
                -25,
                SpringLayout.SOUTH, this
        );
        
        // bottomIndexs
        layout.putConstraint(
                SpringLayout.EAST, bottomIndexs,
                0,
                SpringLayout.WEST, rightIndexs
        );
        layout.putConstraint(
                SpringLayout.WEST, bottomIndexs,
                0,
                SpringLayout.EAST, leftIndexs
        );
        layout.putConstraint(
                SpringLayout.SOUTH, bottomIndexs,
                0,
                SpringLayout.SOUTH, this
        );
        
        // boardPanel
        layout.putConstraint(
                SpringLayout.NORTH, boardPanel,
                0,
                SpringLayout.SOUTH, topIndexs
        );
        layout.putConstraint(
                SpringLayout.EAST, boardPanel,
                0,
                SpringLayout.WEST, rightIndexs
        );
        layout.putConstraint(
                SpringLayout.WEST, boardPanel,
                0,
                SpringLayout.EAST, leftIndexs
        );
        layout.putConstraint(
                SpringLayout.SOUTH, boardPanel,
                0,
                SpringLayout.NORTH, bottomIndexs
        );
    }
    
}
