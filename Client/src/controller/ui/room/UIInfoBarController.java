package controller.ui.room;

import controller.ui.UIAlertController;
import controller.ui.UIController;
import helper.PlaceholderTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import model.IResult;
import view.room.UIInfoBarView;

public class UIInfoBarController extends UIController {
    
    private final JButton logoutButton;
    private final JLabel userInfoLabel;
    private final JButton refreshButton;
    private final JButton createRoomButton;
    private final JButton enterRoomButton;
    private final PlaceholderTextField roomIDTextField;
    
    private final UIAlertController alertController;
    
    private IResult.Responser logoutButtonListener;
    private IResult.Responser refreshButtonListener;
    private IResult.Responser createRoomButtonListener;
    private IResult.ResponseReceiver enterRoomButtonListener;
    
    public UIInfoBarController(UIInfoBarView uiInfoBarView) {
        super(uiInfoBarView);
        
        logoutButton = (JButton)super.findViewById("LogoutButton");
        userInfoLabel = (JLabel)super.findViewById("UserInfoLabel");
        refreshButton = (JButton)super.findViewById("RefreshButton");
        createRoomButton = (JButton)super.findViewById("CreateRoomButton");
        enterRoomButton = (JButton)super.findViewById("EnterRoomButton");
        roomIDTextField = (PlaceholderTextField)super.findViewById("RoomIDTextField");
        
        alertController = new UIAlertController();
        alertController.setHideCancelButton(true);
        alertController.centerWindow();
        
        setEventListeners();
    }
    
    private void setEventListeners() {
        logoutButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (logoutButtonListener != null) {
                    logoutButtonListener.response();
                }
            }
        });
        
        refreshButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (refreshButtonListener != null) {
                    refreshButtonListener.response();
                }
            }
        });
        
        createRoomButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (createRoomButtonListener != null) {
                    createRoomButtonListener.response();
                }
            }
        });
        
        enterRoomButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (enterRoomButtonListener != null) {
                    String roomID = roomIDTextField.getText();
                    if (roomID.length() > 0) {
                        try {
                             enterRoomButtonListener.receiveResult(Integer.parseInt(roomID));
                        } catch (NumberFormatException ex) {
                            alertController.setTitle("Mã phòng không hợp lệ");
                            alertController.setContent("Mã phòng bạn nhập cần phải là một số nguyên");
                            alertController.show();
                        }
                    } else {
                        alertController.setTitle("Chưa nhập mã phòng");
                        alertController.setContent("Bạn cần nhập mã phòng cần vào trước khi nhấn nút 'Vào phòng'");
                        alertController.show();
                    }
                }
            }
        });
    }
    
    public void setLogoutListener(IResult.Responser listener) {
        logoutButtonListener = listener;
    }
    
    public void setRefreshListener(IResult.Responser listener) {
        refreshButtonListener = listener;
    }
    
    public void setCreateRoomListener(IResult.Responser listener) {
        createRoomButtonListener = listener;
    }
    
    public void setEnterRoomListener(IResult.ResponseReceiver listener) {
        enterRoomButtonListener = listener;
    }
    
    public void setPlayerInfo(String name, int level) {
        userInfoLabel.setText(String.format("%s (lv%d)", name, level));
    }
    
}
