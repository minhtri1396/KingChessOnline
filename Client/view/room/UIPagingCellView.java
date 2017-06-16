package view.room;

import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import model.define.ColorDefine;
import model.define.SizeDefine;
import view.UIView;

public class UIPagingCellView extends UIView {
    
    private JPanel pagingPanel;
    private JLabel titleLabel;
    
    public UIPagingCellView() {
        super();
        super.setBackground(Color.BLACK);
        super.setPreferredSize(SizeDefine.PAGING_CELL);
        
        addComponents();
        setConstraints();
    }

    private void addComponents() {
        pagingPanel = new JPanel();
        pagingPanel.setBackground(ColorDefine.PAGING_CELL_UNCHECKED);
        pagingPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.components.put("PagingPanel", pagingPanel);
        this.add(pagingPanel);
        
        titleLabel = new JLabel();
        this.components.put("TitleLabel", titleLabel);
        pagingPanel.add(titleLabel);
    }

    private void setConstraints() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        
        layout.putConstraint(
                SpringLayout.NORTH, pagingPanel,
                1,
                SpringLayout.NORTH, this
        );
        layout.putConstraint(
                SpringLayout.WEST, pagingPanel,
                1,
                SpringLayout.WEST, this
        );
        layout.putConstraint(
                SpringLayout.SOUTH, pagingPanel,
                -1,
                SpringLayout.SOUTH, this
        );
        layout.putConstraint(
                SpringLayout.EAST, pagingPanel,
                -1,
                SpringLayout.EAST, this
        );
        
        setTitleLayout();
    }

    private void setTitleLayout() {
        SpringLayout layout = new SpringLayout();
        pagingPanel.setLayout(layout);
        
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, titleLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, pagingPanel
        );
        
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER, titleLabel,
                0,
                SpringLayout.VERTICAL_CENTER, pagingPanel
        );
    }
    
}
