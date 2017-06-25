package view.room;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import model.define.ColorDefine;
import model.define.FontDefine;
import view.UIView;

public class UIRoomListView extends UIView {
    
    private UIInfoBarView infoBarView;
    private JPanel roomListPanel;
    private JLabel emptyLabel;
    private JPanel emptyPanel;
    private UIPagingView pagingView;
    
    public UIRoomListView(){
        super();
        super.setPreferredSize(new Dimension(100,100));
        
        super.setLayout(new SpringLayout());
        createCompoments();
        setConstraints();
    }
    
    private void createCompoments() {
        infoBarView = new UIInfoBarView();
        this.components.put("InfoBarView", infoBarView);
        this.add(infoBarView);
        
        roomListPanel = new JPanel();
        roomListPanel.setBackground(ColorDefine.ROOM_LIST_BKG);
        this.components.put("RoomListPanel", roomListPanel);
        this.add(roomListPanel);
        
        pagingView = new UIPagingView();
        this.components.put("PagingView", pagingView);
        this.add(pagingView);
        
        emptyPanel = new JPanel();
        emptyPanel.setBackground(ColorDefine.ROOM_LIST_BKG);
        this.components.put("EmptyPanel", emptyPanel);
        this.add(emptyPanel);
        
        emptyLabel = new JLabel("DANH SÁCH PHÒNG RỖNG");
        emptyLabel.setFont(FontDefine.EMPTY_LIST_LABEL);
        emptyLabel.setForeground(ColorDefine.EMPTY_LIST_LABEL);
        emptyPanel.add(emptyLabel);
    }
    
    private void setConstraints(){
        setInfoBarViewConstraints();
        setRoomListPanelConstraints();
        setPagingViewConstraints();
        setEmptyRoomPanelConstraints();
    }
    
    private void setInfoBarViewConstraints() {
        SpringLayout layout = (SpringLayout)this.getLayout();
        
        layout.putConstraint(
                SpringLayout.NORTH, infoBarView,
                0,
                SpringLayout.NORTH, this
        );
        
        layout.putConstraint(
                SpringLayout.WEST, infoBarView,
                0,
                SpringLayout.WEST, this
        );
        
        layout.putConstraint(
                SpringLayout.EAST, infoBarView,
                0,
                SpringLayout.EAST, this
        );
    }
    
    private void setRoomListPanelConstraints() {
        roomListPanel.setLayout(new GridLayout(0, 5));
        SpringLayout layout = (SpringLayout)this.getLayout();
        
        layout.putConstraint(
                SpringLayout.NORTH, roomListPanel,
                0,
                SpringLayout.SOUTH, infoBarView
        );
        
        layout.putConstraint(
                SpringLayout.WEST, roomListPanel,
                0,
                SpringLayout.WEST, this
        );
        
        layout.putConstraint(
                SpringLayout.EAST, roomListPanel,
                0,
                SpringLayout.EAST, this
        );
        
        layout.putConstraint(
                SpringLayout.SOUTH, roomListPanel,
                0,
                SpringLayout.NORTH, pagingView
        );
    }
    
    private void setPagingViewConstraints() {
        SpringLayout layout = (SpringLayout)this.getLayout();
        
        layout.putConstraint(
                SpringLayout.SOUTH, pagingView,
                0,
                SpringLayout.SOUTH, this
        );
        
        layout.putConstraint(
                SpringLayout.WEST, pagingView,
                0,
                SpringLayout.WEST, this
        );
        
        layout.putConstraint(
                SpringLayout.EAST, pagingView,
                0,
                SpringLayout.EAST, this
        );
    }

    private void setEmptyRoomPanelConstraints() {
        SpringLayout mainLayout = (SpringLayout)this.getLayout();
        
        // emptyPanel
        mainLayout.putConstraint(
                SpringLayout.NORTH, emptyPanel,
                0,
                SpringLayout.SOUTH, infoBarView
        );
        mainLayout.putConstraint(
                SpringLayout.WEST, emptyPanel,
                0,
                SpringLayout.WEST, this
        );
        mainLayout.putConstraint(
                SpringLayout.SOUTH, emptyPanel,
                0,
                SpringLayout.SOUTH, this
        );
        mainLayout.putConstraint(
                SpringLayout.EAST, emptyPanel,
                0,
                SpringLayout.EAST, this
        );
        
        SpringLayout emptyLayout = new SpringLayout();
        emptyPanel.setLayout(emptyLayout);
        
        // emptyLabel
        emptyLayout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, emptyLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, emptyPanel
        );
        emptyLayout.putConstraint(
                SpringLayout.VERTICAL_CENTER, emptyLabel,
                -100,
                SpringLayout.VERTICAL_CENTER, emptyPanel
        );
    }
    
}
