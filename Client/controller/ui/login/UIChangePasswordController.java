package controller.ui.login;

import controller.ui.UISubWindowController;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import view.login.UIChangePasswordView;

public class UIChangePasswordController extends UISubWindowController{
    
    private final JPasswordField oldPasswordTextField;
    private final JPasswordField newPasswordTextField;
    private final JPasswordField confirmPasswordTextField;
    private final JButton changeButton;
    private final JLabel noticeLabel;
    
    private String userID;
    private volatile boolean isClosed;
    
    private boolean isSuccessed;
    
    public UIChangePasswordController() {
        super(new UIChangePasswordView());
        super.centerWindow();
        super.setTitle("Đổi mật khẩu");
        
        oldPasswordTextField = (JPasswordField)super.findViewById("OldPasswordTextField");
        newPasswordTextField = (JPasswordField)super.findViewById("NewPasswordTextField");
        confirmPasswordTextField = (JPasswordField)super.findViewById("ConfirmPasswordTextField");
        changeButton = (JButton)super.findViewById("ChangeButton");
        noticeLabel = (JLabel)super.findViewById("NoticeLabel");
        
        userID = null;
        isClosed = false;
        isSuccessed = false;
        
        addListenerForViews();
    }
    
    public void setInformation(String userID) {
        this.userID = userID;
    }
    
    private void addListenerForViews() {
        changeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isCompleted()) {
                    if (true) {
                        JOptionPane.showMessageDialog(
                                null,
                                "Mật khẩu đã được đổi thành công",
                                "Đổi thành công",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        isSuccessed = true;
                        close();
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "Mật khẩu cũ không chính xác nên không thể đổi mật khẩu mới",
                                "Đổi thất bại",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                }
            }
        });
        Thread checkingNewPasswordThread = new Thread(() -> {
            String newPasswordAsString = "";
            String confirmPasswordAsString = "";
            char[] password;
            boolean isMatched;
            while (!isClosed) {
                password = newPasswordTextField.getPassword();
                if (password != null) {
                    newPasswordAsString = new String(password);
                }
                password = confirmPasswordTextField.getPassword();
                if (password != null) {
                    confirmPasswordAsString = new String(password);
                }
                isMatched = !newPasswordAsString.equals(confirmPasswordAsString);
                if (noticeLabel.isVisible() != isMatched) {
                    noticeLabel.setVisible(isMatched);
                }
            }
        });
        checkingNewPasswordThread.setDaemon(false);
        checkingNewPasswordThread.start();
    }
    
    private boolean isCompleted() {
        String messageTitle = null;
        String message = null;
        
        if (oldPasswordTextField.getPassword().length == 0) {
            messageTitle = "Mật khẩu cũ trống";
            message = "Bạn chưa nhập mật khẩu cũ";
        } else if (newPasswordTextField.getPassword().length == 0) {
            messageTitle = "Mật khẩu mới trống";
            message = "Bạn chưa nhập mật khẩu mới";
        } else if (confirmPasswordTextField.getPassword().length == 0) {
            messageTitle = "Mật khẩu xác nhận trống";
            message = "Bạn chưa nhập lại mật khẩu mới để xác nhận lại";
        } else {
            String newPassword = new String(newPasswordTextField.getPassword());
            String confirmPassword = new String(confirmPasswordTextField.getPassword());
            
            if (newPassword.equals(confirmPassword)){
                if (newPassword.equals(userID)) {
                    messageTitle = "Mật khẩu trùng tên đăng nhập";
                    message = "Tên đăng nhập và mật khẩu cần phải khác nhau. Bạn hãy chọn mật khẩu khác";
                }
            } else {
                messageTitle = "Hai mật khẩu không khớp";
                message = "Hai lần nhập mật khẩu mới của bạn cần phải giống nhau";
            }
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
    
    @Override
    public void close() {
        super.close();
        isClosed = true;
        if (receiver != null) {
            receiver.receiveResult(isSuccessed);
        }
    }
    
}
