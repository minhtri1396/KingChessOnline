package controller.ui.room;

import controller.ui.UIController;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import model.IResult;
import view.room.*;

public class UIRoomCellController extends UIController {
    
    private final JLabel roomNameLabel;
    private final JLabel roomDetailLabel;
    private final JButton joinRoomButton;
    
    IResult.ResponseReceiver joinRoomBtnMouseClickCallback;
     
    public UIRoomCellController() {
       super(new UIRoomCellView());
       
       roomNameLabel = (JLabel)super.findViewById("RoomNameLabel");
       roomDetailLabel = (JLabel)super.findViewById("RoomDetailLabel");
       joinRoomButton = (JButton)super.findViewById("JoinRoomButton");
       
       setEventListener();
    }
    
    private void setEventListener() {
        joinRoomButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (joinRoomBtnMouseClickCallback != null) {
                    joinRoomBtnMouseClickCallback.receiveResult(id);
                }
            }
        });
    }
    
    public void setRoomName(String roomName) {
        roomNameLabel.setText(roomName);
    }
    
    public void setRoomDetail(String detail) {
        roomDetailLabel.setText(detail);
    }
    
    public void setJoinRoomListener(IResult.ResponseReceiver listener) {
        joinRoomBtnMouseClickCallback = listener;
    }
    
    public void hide() {
        this.uiView.setVisible(false);
    }
    
    public void show() {
        this.uiView.setVisible(true);
    }
    
}
