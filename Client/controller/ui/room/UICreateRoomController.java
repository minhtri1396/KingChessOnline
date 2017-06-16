package controller.ui.room;

import controller.ui.UIAlertController;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import model.IResult;
import view.room.UICreateRoomView;

public class UICreateRoomController extends UIAlertController {
    
    private final JTextField roomNameTextField;
    private final JCheckBox pcCheckBox;
    
    private IResult.ResponseReceiver okButtonListener;
    
    public UICreateRoomController() {
        super(new UICreateRoomView());
        
        roomNameTextField = (JTextField)super.findViewById("RoomNameTextField");
        pcCheckBox = (JCheckBox)super.findViewById("PCCheckBox");
    }
    
    public void setOKButtonListener(IResult.ResponseReceiver okButtonListener) {
        this.okButtonListener = okButtonListener;
        super.setOKButtonListener(() -> {
            this.okButtonListener.receiveResult(new Object[] {
                pcCheckBox.isSelected(),
                roomNameTextField.getText()
            });
            roomNameTextField.setText("");
        });
    }
    
}
