package view.room;
import helper.ImageManager;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import model.define.ColorDefine;
import model.define.FontDefine;
import view.UIView;


public class UIRoomCellView  extends UIView{
    public UIRoomCellView(){
        super();
        super.setBackground(ColorDefine.ROOM_LIST_BKG);
        createCompoments();
        setCompomentsConStraint();
    }
   
    private void createCompoments(){
        addDetailPanel();
        addButtonPanel();
    }
    
    private JPanel borderInfoPanel;
    private JPanel infoPanel ;
    private JLabel roomNameLabel;
    private JLabel roomImageLabel;
    private JLabel roomNPlayerLabel;
  
    public void addDetailPanel(){
        borderInfoPanel = new JPanel();
        borderInfoPanel.setBackground(Color.BLACK);
        this.add(borderInfoPanel);
        
        infoPanel = new JPanel();
        infoPanel.setBackground(Color.WHITE);
        borderInfoPanel.add(infoPanel);
        //Add RoomNameLabel 
        addRoomNameLabel();
        //Add Image label
        addImageLabel();
        //add room detail
        addRoomDetailLabel();       
    }
    
    private void addRoomNameLabel() {
        roomNameLabel = new JLabel();
        roomNameLabel.setFont(FontDefine.ROOM_DETAIL);
        roomNameLabel.setForeground(ColorDefine.ROOM_INFO);
        this.components.put("RoomNameLabel", roomNameLabel);
        infoPanel.add(roomNameLabel);
    }
     
    private void addRoomDetailLabel() {
        roomNPlayerLabel = new JLabel("Nguoi choi : 1/2");
        roomNPlayerLabel.setFont(FontDefine.ROOM_DETAIL);
        roomNPlayerLabel.setForeground(ColorDefine.ROOM_INFO);
        this.components.put("RoomDetailLabel", roomNPlayerLabel);
        infoPanel.add(roomNPlayerLabel);
    }
    
    private void addImageLabel() {
        roomImageLabel = ImageManager.Instance.getImageFrom("room_icon.png", 120, 120);
        infoPanel.add(roomImageLabel);    
    }
    
    private void setCompomentsConStraint(){
        this.setLayout(new SpringLayout());
        setContraintsDetailPanel();
        setContraintsButton();
        
    }

    private void setContraintsDetailPanel() {
        SpringLayout mainLayout = (SpringLayout) this.getLayout();
        
        // borderPanel
        mainLayout.putConstraint(
                SpringLayout.NORTH, borderInfoPanel, 
                20, 
                SpringLayout.NORTH, this
        );
        mainLayout.putConstraint(
                SpringLayout.WEST, borderInfoPanel, 
                10, 
                SpringLayout.WEST, this
        );
        mainLayout.putConstraint(
                SpringLayout.EAST, borderInfoPanel, 
                -10, 
                SpringLayout.EAST, this
        );
        mainLayout.putConstraint(
                SpringLayout.SOUTH, borderInfoPanel, 
                -70, 
                SpringLayout.SOUTH, this
        );
        
        SpringLayout borderLayout = new SpringLayout();
        borderInfoPanel.setLayout(borderLayout);
        borderLayout.putConstraint(
                SpringLayout.NORTH, infoPanel, 
                1, 
                SpringLayout.NORTH, borderInfoPanel
        );
        borderLayout.putConstraint(
                SpringLayout.WEST, infoPanel, 
                1, 
                SpringLayout.WEST, borderInfoPanel
        );
        borderLayout.putConstraint(
                SpringLayout.EAST, infoPanel, 
                -1, 
                SpringLayout.EAST, borderInfoPanel
        );
        borderLayout.putConstraint(
                SpringLayout.SOUTH, infoPanel, 
                0, 
                SpringLayout.SOUTH, borderInfoPanel
        );
        
        infoPanel.setLayout(new SpringLayout());
        
        setImageConstraint();    
        setRoomNameConstraint();
        setRoomDetailConstraint();
    }

    private void setRoomDetailConstraint() {
       SpringLayout detailPanelLayout = (SpringLayout) infoPanel.getLayout();
       
       detailPanelLayout.putConstraint(
            SpringLayout.NORTH, roomNPlayerLabel, 
            -40, 
            SpringLayout.SOUTH, infoPanel
       );
       
       detailPanelLayout.putConstraint(
            SpringLayout.HORIZONTAL_CENTER, roomNPlayerLabel , 
            0, 
            SpringLayout.HORIZONTAL_CENTER, infoPanel
       );
    }

    private void setRoomNameConstraint() {
       SpringLayout detailPanelLayout = (SpringLayout) infoPanel.getLayout();
       detailPanelLayout.putConstraint(
               SpringLayout.SOUTH, roomNameLabel , 
               -5, 
               SpringLayout.NORTH, roomNPlayerLabel
       );
       detailPanelLayout.putConstraint(
               SpringLayout.HORIZONTAL_CENTER, roomNameLabel , 
               0, 
               SpringLayout.HORIZONTAL_CENTER, infoPanel
       );
    }

    private void setImageConstraint() {
        SpringLayout detailPanelLayout = (SpringLayout) infoPanel.getLayout();
        detailPanelLayout.putConstraint(
            SpringLayout.NORTH, roomImageLabel, 
            -25, 
            SpringLayout.NORTH, infoPanel
        );
        detailPanelLayout.putConstraint(
            SpringLayout.EAST, roomImageLabel, 
            0, 
            SpringLayout.EAST, infoPanel
        );
        detailPanelLayout.putConstraint(
            SpringLayout.SOUTH, roomImageLabel, 
            40, 
            SpringLayout.NORTH, roomNameLabel
        );
        detailPanelLayout.putConstraint(
            SpringLayout.WEST, roomImageLabel, 
            0, 
            SpringLayout.WEST, infoPanel
        );
        detailPanelLayout.putConstraint(
            SpringLayout.WIDTH, roomImageLabel , 
            0, 
            SpringLayout.HEIGHT, roomImageLabel
        );
        detailPanelLayout.putConstraint(
            SpringLayout.HORIZONTAL_CENTER, roomImageLabel , 
            0, 
            SpringLayout.HORIZONTAL_CENTER, infoPanel
        );
        
    }
    
    private JPanel buttonPanel;
    private void addButtonPanel(){
        buttonPanel = new JPanel();
        buttonPanel.setBackground(ColorDefine.JOIN_ROOM_BUTTON);
        buttonPanel.setPreferredSize(new Dimension(100, 0));
        this.add(buttonPanel);
        addButton();
    }
    
    private JButton joinRoomButton;
    private void addButton(){
        joinRoomButton = new JButton("Chơi ván này");
        joinRoomButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        joinRoomButton.setFont(FontDefine.JOIN_ROOM_BUTTON);
        joinRoomButton.setBackground(ColorDefine.JOIN_ROOM_BUTTON);
        joinRoomButton.setForeground(Color.WHITE);
        this.components.put("JoinRoomButton", joinRoomButton);
        buttonPanel.add(joinRoomButton);
    }
    
    private void setContraintsButton() {
        SpringLayout mainLayout = (SpringLayout) this.getLayout();
        mainLayout.putConstraint(
                SpringLayout.NORTH, buttonPanel,
                0, 
                SpringLayout.SOUTH, borderInfoPanel
        );
        
        mainLayout.putConstraint(
                SpringLayout.WEST, buttonPanel,
                0, 
                SpringLayout.WEST, borderInfoPanel
        );
        
        mainLayout.putConstraint(
                SpringLayout.SOUTH, buttonPanel,
                -20, 
                SpringLayout.SOUTH, this
        );
        
        mainLayout.putConstraint(
                SpringLayout.EAST, buttonPanel,
                0, 
                SpringLayout.EAST, borderInfoPanel
        );
        
        SpringLayout layout = new SpringLayout();
        buttonPanel.setLayout(layout);
        
        // joinRoomButton
        layout.putConstraint(
                SpringLayout.NORTH, joinRoomButton,
                0, 
                SpringLayout.NORTH, buttonPanel
        );
        
        layout.putConstraint(
                SpringLayout.EAST, joinRoomButton,
                0, 
                SpringLayout.EAST, buttonPanel
        );
        
        layout.putConstraint(
                SpringLayout.SOUTH, joinRoomButton,
                0, 
                SpringLayout.SOUTH, buttonPanel
        );
        
        layout.putConstraint(
                SpringLayout.WEST, joinRoomButton,
                0, 
                SpringLayout.WEST, buttonPanel
        );
    }
}
