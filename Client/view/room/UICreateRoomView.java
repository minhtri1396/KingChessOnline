package view.room;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import model.define.ColorDefine;
import model.define.FontDefine;
import model.define.SizeDefine;
import util.JTextFieldLimit;
import view.UIAlertView;

public class UICreateRoomView extends UIAlertView {
    
    private JTextField roomNameTextField;
    private JCheckBox pcCheckBox;
    
    public UICreateRoomView() {
        super();
        super.setPreferredSize(SizeDefine.CREATE_ROOM);
        super.setSize(SizeDefine.CREATE_ROOM);
    }

    @Override
    protected void addComponents() {
        roomNameTextField = new JTextField();
        roomNameTextField.setPreferredSize(SizeDefine.CREATE_ROOM_TEXTFIELD);
        roomNameTextField.setFont(FontDefine.ALERT_CONTENT);
        roomNameTextField.setForeground(ColorDefine.ALERT_CONTENT);
        roomNameTextField.setDocument(new JTextFieldLimit(7));
        this.components.put("RoomNameTextField", roomNameTextField);
        infoPanel.add(roomNameTextField);
        super.addComponents();
        
        pcCheckBox = new JCheckBox("Đánh với máy");
        pcCheckBox.setBackground(infoPanel.getBackground());
        this.components.put("PCCheckBox", pcCheckBox);
        infoPanel.add(pcCheckBox);
    }
    
    @Override
    protected void setConstraints() {
        super.setConstraints();
        SpringLayout layout = (SpringLayout)infoPanel.getLayout();
        
        // roomNameTextField
        layout.putConstraint(
                SpringLayout.NORTH, roomNameTextField,
                100,
                SpringLayout.NORTH, infoPanel
        );
        layout.putConstraint(
                SpringLayout.WEST, roomNameTextField,
                0,
                SpringLayout.WEST, super.content
        );
        layout.putConstraint(
                SpringLayout.EAST, roomNameTextField,
                0,
                SpringLayout.EAST, super.content
        );
        
        // pcCheckBox
        layout.putConstraint(
                SpringLayout.NORTH, pcCheckBox,
                5,
                SpringLayout.SOUTH, roomNameTextField
        );
        layout.putConstraint(
                SpringLayout.WEST, pcCheckBox,
                0,
                SpringLayout.WEST, roomNameTextField
        );
    }
    
}
