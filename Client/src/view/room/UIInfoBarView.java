package view.room;

import helper.PlaceholderTextField;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import model.define.ColorDefine;
import model.define.FontDefine;
import model.define.SizeDefine;
import view.UIView;

public class UIInfoBarView extends UIView {
    
    private JButton logoutButton;
    private JLabel userInfoLabel;
    private JButton refreshButton;
    private JButton createRoomButton;
    private JButton enterRoomButton;
    private PlaceholderTextField roomIDTextField;
    
    public UIInfoBarView() {
        super();
        super.setPreferredSize(new Dimension(0, 50));
        super.setBackground(ColorDefine.ROOM_LIST_BKG);
        
        addComponents();
        setConstraints();
    }

    private void addComponents() {
        Cursor cursor = new Cursor(Cursor.HAND_CURSOR);
        
        logoutButton = new JButton("Đăng xuất");
        logoutButton.setFont(FontDefine.TOP_INFO_BAR);
        logoutButton.setPreferredSize(SizeDefine.LOGOUT_BUTTON);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(ColorDefine.LOGOUT_BUTTON);
        logoutButton.setCursor(cursor);
        this.components.put("LogoutButton", logoutButton);
        this.add(logoutButton);
        
        userInfoLabel = new JLabel("Tri Dao (lv25)");
        userInfoLabel.setFont(FontDefine.TOP_INFO_BAR);
        this.components.put("UserInfoLabel", userInfoLabel);
        this.add(userInfoLabel);
        
        refreshButton = new JButton("Refresh");
        refreshButton.setFont(FontDefine.TOP_INFO_BAR);
        refreshButton.setPreferredSize(SizeDefine.REFRESH_ROOM_LIST_BUTTON);
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setBackground(ColorDefine.REFRESH_ROOM_LIST_BUTTON);
        refreshButton.setCursor(cursor);
        this.components.put("RefreshButton", refreshButton);
        this.add(refreshButton);
        
        createRoomButton = new JButton("Tạo phòng");
        createRoomButton.setFont(FontDefine.TOP_INFO_BAR);
        createRoomButton.setPreferredSize(SizeDefine.CREATE_ROOM_BUTTON);
        createRoomButton.setForeground(Color.WHITE);
        createRoomButton.setBackground(ColorDefine.CREATE_ROOM_BUTTON);
        createRoomButton.setCursor(cursor);
        this.components.put("CreateRoomButton", createRoomButton);
        this.add(createRoomButton);
        
        enterRoomButton = new JButton("Vào phòng");
        enterRoomButton.setFont(FontDefine.TOP_INFO_BAR);
        enterRoomButton.setPreferredSize(SizeDefine.ENTER_ROOM_BUTTON);
        enterRoomButton.setForeground(Color.WHITE);
        enterRoomButton.setBackground(ColorDefine.ENTER_ROOM_BUTTON);
        enterRoomButton.setCursor(cursor);
        this.components.put("EnterRoomButton", enterRoomButton);
        this.add(enterRoomButton);
        
        roomIDTextField = new PlaceholderTextField();
        roomIDTextField.setFont(FontDefine.PLACEHOLDER_TEXTFIELD);
        roomIDTextField.setPreferredSize(SizeDefine.PLACEHOLDER_TEXTFIELD);
        roomIDTextField.setPlaceholder("Nhập mã phòng để vào");
        this.components.put("RoomIDTextField", roomIDTextField);
        this.add(roomIDTextField);
    }

    private void setConstraints() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        
        // logoutButton
        layout.putConstraint(
                SpringLayout.NORTH, logoutButton,
                20,
                SpringLayout.NORTH, this
        );
        layout.putConstraint(
                SpringLayout.SOUTH, logoutButton,
                0,
                SpringLayout.SOUTH, this
        );
        layout.putConstraint(
                SpringLayout.EAST, logoutButton,
                -10,
                SpringLayout.EAST, this
        );
        
        // userInfoLabel
        layout.putConstraint(
                SpringLayout.NORTH, userInfoLabel,
                0,
                SpringLayout.NORTH, logoutButton
        );
        layout.putConstraint(
                SpringLayout.SOUTH, userInfoLabel,
                0,
                SpringLayout.SOUTH, logoutButton
        );
        layout.putConstraint(
                SpringLayout.EAST, userInfoLabel,
                -20,
                SpringLayout.WEST, logoutButton
        );
        
        // refreshButton
        layout.putConstraint(
                SpringLayout.NORTH, refreshButton,
                20,
                SpringLayout.NORTH, this
        );
        layout.putConstraint(
                SpringLayout.WEST, refreshButton,
                20,
                SpringLayout.EAST, createRoomButton
        );
        layout.putConstraint(
                SpringLayout.SOUTH, refreshButton,
                0,
                SpringLayout.SOUTH, this
        );
        
        // createRoomButton
        layout.putConstraint(
                SpringLayout.NORTH, createRoomButton,
                20,
                SpringLayout.NORTH, this
        );
        layout.putConstraint(
                SpringLayout.WEST, createRoomButton,
                20,
                SpringLayout.EAST, enterRoomButton
        );
        layout.putConstraint(
                SpringLayout.SOUTH, createRoomButton,
                0,
                SpringLayout.SOUTH, this
        );
        
        // enterRoomButton
        layout.putConstraint(
                SpringLayout.NORTH, enterRoomButton,
                20,
                SpringLayout.NORTH, this
        );
        layout.putConstraint(
                SpringLayout.WEST, enterRoomButton,
                10,
                SpringLayout.EAST, roomIDTextField
        );
        layout.putConstraint(
                SpringLayout.SOUTH, enterRoomButton,
                0,
                SpringLayout.SOUTH, this
        );
        
        // roomIDTextField
        layout.putConstraint(
                SpringLayout.NORTH, roomIDTextField,
                20,
                SpringLayout.NORTH, this
        );
        layout.putConstraint(
                SpringLayout.WEST, roomIDTextField,
                10,
                SpringLayout.WEST, this
        );
        layout.putConstraint(
                SpringLayout.SOUTH, roomIDTextField,
                0,
                SpringLayout.SOUTH, this
        );
    }
    
}
