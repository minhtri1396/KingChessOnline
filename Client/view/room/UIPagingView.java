package view.room;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import model.define.ColorDefine;
import view.UIView;

public class UIPagingView extends UIView {
    
    private JScrollPane pagingListScrollPane;
    private JPanel pagingListPanel;
    
    public UIPagingView() {
        super();
        super.setPreferredSize(new Dimension(0, 50));
        super.setBackground(ColorDefine.ROOM_LIST_BKG);
        
        addComponents();
        setConstraints();
    }
    
    private void addComponents() {
        pagingListPanel = new JPanel();
        pagingListPanel.setBackground(ColorDefine.ROOM_LIST_BKG);
        pagingListPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.components.put("PagingListPanel", pagingListPanel);
        
        pagingListScrollPane = new JScrollPane(pagingListPanel);
        pagingListScrollPane.setBorder(BorderFactory.createEmptyBorder());
        pagingListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        pagingListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pagingListScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        this.add(pagingListScrollPane);
    }
    
    private void setConstraints() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        
        layout.putConstraint(
                SpringLayout.NORTH, pagingListScrollPane,
                0,
                SpringLayout.NORTH, this
        );
        
        layout.putConstraint(
                SpringLayout.WEST, pagingListScrollPane,
                0,
                SpringLayout.WEST, this
        );
        
        layout.putConstraint(
                SpringLayout.SOUTH, pagingListScrollPane,
                0,
                SpringLayout.SOUTH, this
        );
        
        layout.putConstraint(
                SpringLayout.EAST, pagingListScrollPane,
                0,
                SpringLayout.EAST, this
        );
    }
    
}
