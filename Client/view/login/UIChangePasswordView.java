package view.login;

import model.define.ColorDefine;
import model.define.FontDefine;
import model.define.SizeDefine;
import helper.ImageManager;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.SpringLayout;
import util.JTextFieldLimit;
import view.UIView;

public class UIChangePasswordView extends UIView {
    
    public UIChangePasswordView() {
        super();
        super.setBackground(ColorDefine.BACKGROUND);
        
        createComponents();
        createLayoutConstraints();
    }

    private JLabel changePasswordImage;
    private JLabel oldPasswordLabel;
    private JPasswordField oldPasswordTextField;
    private JLabel newPasswordLabel;
    private JPasswordField newPasswordTextField;
    private JLabel confirmPasswordLabel;
    private JPasswordField confirmPasswordTextField;
    private JButton changeButton;
    private JLabel noticeLabel;
    private void createComponents() {
        this.setSize(new Dimension(400, 550));
        
        changePasswordImage = ImageManager.Instance.getImageFrom("change_password.png", 100, 100);
        this.add(changePasswordImage);
        
        oldPasswordLabel = new JLabel("Mật khẩu cũ");
        oldPasswordLabel.setFont(FontDefine.CHANGE_PASSWORD_INFO);
        oldPasswordLabel.setForeground(ColorDefine.CHANGE_PASSWORD_INFO);
        this.add(oldPasswordLabel);
        
        oldPasswordTextField = new JPasswordField();
        oldPasswordTextField.setFont(FontDefine.LOGIN_INFO);
        oldPasswordTextField.setForeground(ColorDefine.LOGIN_INFO);
        oldPasswordTextField.setPreferredSize(SizeDefine.LOGIN_TEXT_FIELD);
        oldPasswordTextField.setDocument(new JTextFieldLimit(50));
        components.put("OldPasswordTextField", oldPasswordTextField);
        this.add(oldPasswordTextField);
        
        
        newPasswordLabel = new JLabel("Mật khẩu mới");
        newPasswordLabel.setFont(FontDefine.CHANGE_PASSWORD_INFO);
        newPasswordLabel.setForeground(ColorDefine.CHANGE_PASSWORD_INFO);
        this.add(newPasswordLabel);
        
        newPasswordTextField = new JPasswordField();
        newPasswordTextField.setFont(FontDefine.LOGIN_INFO);
        newPasswordTextField.setForeground(ColorDefine.LOGIN_INFO);
        newPasswordTextField.setPreferredSize(SizeDefine.LOGIN_TEXT_FIELD);
        newPasswordTextField.setDocument(new JTextFieldLimit(50));
        components.put("NewPasswordTextField", newPasswordTextField);
        this.add(newPasswordTextField);
        
        confirmPasswordLabel = new JLabel("Xác nhận lại mật khẩu mới");
        confirmPasswordLabel.setFont(FontDefine.CHANGE_PASSWORD_INFO);
        confirmPasswordLabel.setForeground(ColorDefine.CHANGE_PASSWORD_INFO);
        this.add(confirmPasswordLabel);
        
        confirmPasswordTextField = new JPasswordField();
        confirmPasswordTextField.setFont(FontDefine.LOGIN_INFO);
        confirmPasswordTextField.setForeground(ColorDefine.LOGIN_INFO);
        confirmPasswordTextField.setPreferredSize(SizeDefine.LOGIN_TEXT_FIELD);
        confirmPasswordTextField.setDocument(new JTextFieldLimit(50));
        components.put("ConfirmPasswordTextField", confirmPasswordTextField);
        this.add(confirmPasswordTextField);
        
        changeButton = new JButton("Xác nhận");
        changeButton.setFont(FontDefine.ACCEPTED_BUTTON);
        changeButton.setForeground(ColorDefine.CHANGING_PASS_BTN_TEXT);
        changeButton.setBackground(ColorDefine.CHANGING_PASS_BTN_BKG);
        components.put("ChangeButton", changeButton);
        this.add(changeButton);
        
        noticeLabel = new JLabel("Hai lần nhập mật khẩu mới cần phải giống nhau");
        noticeLabel.setForeground(ColorDefine.CHANGE_PASSWORD_NOTICE);
        components.put("NoticeLabel", noticeLabel);
        noticeLabel.setVisible(false);
        this.add(noticeLabel);
        
    }

    private void createLayoutConstraints() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        
        // changePasswordImage
        layout.putConstraint(
                SpringLayout.NORTH, changePasswordImage,
                25, 
                SpringLayout.NORTH, this
        );
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, changePasswordImage,
                0, 
                SpringLayout.HORIZONTAL_CENTER, this
        );
        // oldPasswordLabel
        layout.putConstraint(
                SpringLayout.NORTH, oldPasswordLabel,
                40, 
                SpringLayout.SOUTH, changePasswordImage
        );
        layout.putConstraint(
                SpringLayout.WEST, oldPasswordLabel,
                20, 
                SpringLayout.WEST, this
        );
        // oldPasswordTextField
        layout.putConstraint(
                SpringLayout.NORTH, oldPasswordTextField,
                5, 
                SpringLayout.SOUTH, oldPasswordLabel
        );
        layout.putConstraint(
                SpringLayout.WEST, oldPasswordTextField,
                20, 
                SpringLayout.WEST, this
        );
        layout.putConstraint(
                SpringLayout.EAST, oldPasswordTextField,
                -20, 
                SpringLayout.EAST, this
        );
        // newPasswordLabel
        layout.putConstraint(
                SpringLayout.NORTH, newPasswordLabel,
                20, 
                SpringLayout.SOUTH, oldPasswordTextField
        );
        layout.putConstraint(
                SpringLayout.WEST, newPasswordLabel,
                20, 
                SpringLayout.WEST, this
        );
        // newPasswordTextField;
        layout.putConstraint(
                SpringLayout.NORTH, newPasswordTextField,
                5, 
                SpringLayout.SOUTH, newPasswordLabel
        );
        layout.putConstraint(
                SpringLayout.WEST, newPasswordTextField,
                20, 
                SpringLayout.WEST, this
        );
        layout.putConstraint(
                SpringLayout.EAST, newPasswordTextField,
                -20, 
                SpringLayout.EAST, this
        );
        // confirmPasswordLabel;
        layout.putConstraint(
                SpringLayout.NORTH, confirmPasswordLabel,
                20, 
                SpringLayout.SOUTH, newPasswordTextField
        );
        layout.putConstraint(
                SpringLayout.WEST, confirmPasswordLabel,
                20, 
                SpringLayout.WEST, this
        );
        // confirmPasswordTextField;
        layout.putConstraint(
                SpringLayout.NORTH, confirmPasswordTextField,
                5, 
                SpringLayout.SOUTH, confirmPasswordLabel
        );
        layout.putConstraint(
                SpringLayout.WEST, confirmPasswordTextField,
                20, 
                SpringLayout.WEST, this
        );
        layout.putConstraint(
                SpringLayout.EAST, confirmPasswordTextField,
                -20, 
                SpringLayout.EAST, this
        );
        // changeButton
        layout.putConstraint(
                SpringLayout.NORTH, changeButton,
                28, 
                SpringLayout.SOUTH, confirmPasswordTextField
        );
        layout.putConstraint(
                SpringLayout.WEST, changeButton,
                20, 
                SpringLayout.WEST, this
        );
        layout.putConstraint(
                SpringLayout.EAST, changeButton,
                -20, 
                SpringLayout.EAST, this
        );
        layout.putConstraint(
                SpringLayout.SOUTH, changeButton,
                -40, 
                SpringLayout.SOUTH, this
        );
        // noticeLabel
        layout.putConstraint(
                SpringLayout.SOUTH, noticeLabel,
                -5, 
                SpringLayout.SOUTH, this
        );
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, noticeLabel,
                0, 
                SpringLayout.HORIZONTAL_CENTER, this
        );
    }
    
}
