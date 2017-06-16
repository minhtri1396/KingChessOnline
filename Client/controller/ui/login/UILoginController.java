package controller.ui.login;

import controller.communication.MessagesManager;
import controller.ui.UIController;
import model.define.StringDefine;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import main.App;
import model.IResult;
import model.communication.client_server.ConnectingServer;
import model.type.Player;
import view.login.UILoginView;

public class UILoginController extends UIController {
    
    private final JTextField userNameTextField;
    private final JPasswordField passTextField;
    private final JButton loginBtn;
    
    private IResult.ResponseReceiver loginSuccessListener;
    
    private boolean isLoading;
    
    public UILoginController() {
        super(new UILoginView());
        
        userNameTextField = (JTextField)super.findViewById("UserNameTextField");
        passTextField = (JPasswordField)super.findViewById("PassTextField");
        loginBtn = (JButton)super.findViewById("LoginButton");
        
        isLoading = false;
        
        addListenerForViews();
    }

    private boolean isCanConnectToServer;
    private void addListenerForViews() {
        loginBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isLoading && isCompleted()) {
                    String username = userNameTextField.getText();
                    String password = new String(passTextField.getPassword());
                    Player player = getPlayer(username, password);
                    if (player != null) {
                        if (username.equals(password)) {
                            UIChangePasswordController uiChangePasswordController =
                                    new UIChangePasswordController();
                            uiChangePasswordController.setInformation(username);
                            uiChangePasswordController.setResponseReceiver((Object obj) -> {
                                App.getInstance().setVisible(true);
                                App.getInstance().requestFocusInWindow();
                                App.getInstance().setEnabled(true);
                                boolean isSuccessed = (boolean)obj;
                                if (isSuccessed) {
                                    login(player);
                                }
                            });
                            App.getInstance().setEnabled(false);
                            uiChangePasswordController.show();
                        } else {
                            login(player);
                        }
                    } else if (isCanConnectToServer) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Tên đăng nhập không tồn tại hoặc mật khẩu không chính xác",
                                "Không thể đăng nhập",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                }
            }
        });
    }
    
    private boolean isCompleted() {
        String messageTitle = null;
        String message = null;
        if (userNameTextField.getText().trim().length() == 0) {
            messageTitle = "Chưa điền tên đăng nhập";
            message = "Bạn cần điền tên đăng nhập";
        } else if (passTextField.getPassword().length == 0) {
            messageTitle = "Chưa điền mật khẩu";
            message = "Bạn cần điền mật khẩu để đăng nhập";
        }

        if (message == null) {
            return true;
        }

        JOptionPane.showMessageDialog(
                null,
                message,
                messageTitle,
                JOptionPane.INFORMATION_MESSAGE
        );

        return false;
    }
    
    private Player getPlayer(String username, String password) {
        isCanConnectToServer = true;
        
        if (ConnectingServer.CONNECTOR.create(App.SERVER_IP, App.SERVER_PORT)) {
            return (Player)ConnectingServer.CONNECTOR.sendMessage(
                    MessagesManager.MessageType.LOGIN,
                    new String[] {
                        username,
                        password
                    }
            );
        }
        
        isCanConnectToServer = false;
        App.getInstance().alertDisconnection();
        return null;
    }
    
    private void login(Player player) {
        if (isLoading) {
        } else {
            isLoading = true;
            loginBtn.setText("Loading...");
            setEnableViews(false);
            
            new Thread(() -> {
                if (loginSuccessListener != null) {
                    loginSuccessListener.receiveResult(player);
                }
                passTextField.setText("");
                isLoading = false;
                loginBtn.setText(StringDefine.LOGIN_BTN);
                setEnableViews(true);
            }).start();
        }
    }
    
    private void setEnableViews(boolean enable) {
        loginBtn.setEnabled(enable);
        userNameTextField.setEnabled(enable);
        passTextField.setEnabled(enable);
    }
    
    public void setLoginSuccessListener(IResult.ResponseReceiver listener) {
        loginSuccessListener = listener;
    }
    
}
