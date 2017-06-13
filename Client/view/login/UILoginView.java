package view.login;

import model.define.ColorDefine;
import model.define.FontDefine;
import model.define.SizeDefine;
import model.define.StringDefine;
import helper.ImageManager;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import util.JTextFieldLimit;
import view.UIView;

public class UILoginView extends UIView {
    public UILoginView() {
        super();
        super.setBackground(ColorDefine.BACKGROUND);
        
        createComponents();
        createLayoutConstraints();
    }

    private void createComponents() {
        this.setPreferredSize(new Dimension(500, 500));
        this.setSize(new Dimension(500, 500));
        this.setBackground(ColorDefine.LOGIN_BKG);
        
        addTitleView();
        addLoginPanel();
        addFooter();
    }

    private void createLayoutConstraints() {
        this.setLayout(new SpringLayout());
        
        setTitleConstraints();
        setLoginPanelConstraints();
        setFooterConstraints();
    }
    
    private JLabel programNameLabel;
    private JPanel prrogramNamePanel;
    private void addTitleView() {
        prrogramNamePanel = new JPanel();
        prrogramNamePanel.setPreferredSize(new Dimension(700, 70));
        prrogramNamePanel.setBackground(ColorDefine.PROGRAM_NAME_BKG);
        this.add(prrogramNamePanel);
        
        programNameLabel = new JLabel(StringDefine.PROGRAM_NAME.toUpperCase());
        programNameLabel.setFont(FontDefine.PROGRAM_NAME);
        programNameLabel.setForeground(ColorDefine.BACKGROUND);
        prrogramNamePanel.add(programNameLabel);
    }
    private void setTitleConstraints() {
        SpringLayout layout = (SpringLayout)this.getLayout();
        SpringLayout programNamePanelLayout = new SpringLayout();
        prrogramNamePanel.setLayout(programNamePanelLayout);
        
        // prrogramNamePanel
        layout.putConstraint(
                SpringLayout.NORTH, prrogramNamePanel,
                40,
                SpringLayout.NORTH, this
        );
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, prrogramNamePanel,
                0,
                SpringLayout.HORIZONTAL_CENTER, this
        );
        // programNameLabel
        programNamePanelLayout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, programNameLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, prrogramNamePanel
        );
        programNamePanelLayout.putConstraint(
                SpringLayout.VERTICAL_CENTER, programNameLabel,
                0,
                SpringLayout.VERTICAL_CENTER, prrogramNamePanel
        );
    }
    
    private JPanel loginPanel;
    private JLabel loginImage;
    private void addLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.setBackground(ColorDefine.BACKGROUND);
        loginPanel.setPreferredSize(new Dimension(700, 560));
        this.add(loginPanel);
        
        loginImage = ImageManager.Instance.getImageFrom("login.png", 150, 150);
        loginPanel.add(loginImage);
        
        addTextFields();
        addButtons();
    }
    private void setLoginPanelConstraints() {
        SpringLayout layout = (SpringLayout)this.getLayout();
        
        SpringLayout loginPanelLayout = new SpringLayout();
        loginPanel.setLayout(loginPanelLayout);
        
        // loginPanel
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, loginPanel,
                0,
                SpringLayout.HORIZONTAL_CENTER, this
        );
        layout.putConstraint(
                SpringLayout.NORTH, loginPanel,
                40,
                SpringLayout.SOUTH, prrogramNamePanel
        );
        // loginImage
        loginPanelLayout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, loginImage,
                0,
                SpringLayout.HORIZONTAL_CENTER, loginPanel
        );
        loginPanelLayout.putConstraint(
                SpringLayout.NORTH, loginImage,
                28,
                SpringLayout.NORTH, loginPanel
        );
        
        setTextFieldConstraints();
        setButtonConstraints();
    }
    
    private JLabel userNameImage;
    private JTextField userNameTextField;
    private JLabel passwordImage;
    private JPasswordField passTextField;
    private void addTextFields() {
        userNameImage = ImageManager.Instance.getImageFrom("username.png", 50, 50);
        userNameImage.setToolTipText("Tên đăng nhập");
        loginPanel.add(userNameImage);
        
        userNameTextField = new JTextField();
        userNameTextField.setToolTipText("Tên đăng nhập");
        userNameTextField.setFont(FontDefine.LOGIN_INFO);
        userNameTextField.setForeground(ColorDefine.LOGIN_INFO);
        userNameTextField.setPreferredSize(SizeDefine.LOGIN_TEXT_FIELD);
        userNameTextField.setDocument(new JTextFieldLimit(7));
        components.put("UserNameTextField", userNameTextField);
        loginPanel.add(userNameTextField);
        
        passwordImage = ImageManager.Instance.getImageFrom("password.png", 50, 50);
        passwordImage.setToolTipText("Mật khẩu đăng nhập");
        loginPanel.add(passwordImage);
        
        passTextField = new JPasswordField();
        passTextField.setToolTipText("Mật khẩu đăng nhập");
        passTextField.setFont(FontDefine.LOGIN_INFO);
        passTextField.setForeground(ColorDefine.LOGIN_INFO);
        passTextField.setPreferredSize(SizeDefine.LOGIN_TEXT_FIELD);
        passTextField.setDocument(new JTextFieldLimit(50));
        components.put("PassTextField", passTextField);
        loginPanel.add(passTextField);
    }
    private void setTextFieldConstraints() {
        SpringLayout layout = (SpringLayout)loginPanel.getLayout();
        
        // userNameImage
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER, userNameImage,
                0,
                SpringLayout.VERTICAL_CENTER, userNameTextField
        );
        layout.putConstraint(
                SpringLayout.EAST, userNameImage,
                -10,
                SpringLayout.WEST, userNameTextField
        );
        // userNameTextField
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, userNameTextField,
                25,
                SpringLayout.HORIZONTAL_CENTER, loginPanel
        );
        layout.putConstraint(
                SpringLayout.NORTH, userNameTextField,
                70,
                SpringLayout.SOUTH, loginImage
        );
        layout.putConstraint(
                SpringLayout.HEIGHT, userNameTextField,
                0,
                SpringLayout.HEIGHT, userNameImage
        );
        // passwordImage
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER, passwordImage,
                0,
                SpringLayout.VERTICAL_CENTER, passTextField
        );
        layout.putConstraint(
                SpringLayout.EAST, passwordImage,
                -10,
                SpringLayout.WEST, passTextField
        );
        // passTextField
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, passTextField,
                25,
                SpringLayout.HORIZONTAL_CENTER, loginPanel
        );
        layout.putConstraint(
                SpringLayout.NORTH, passTextField,
                40,
                SpringLayout.SOUTH, userNameTextField
        );
        layout.putConstraint(
                SpringLayout.HEIGHT, passTextField,
                0,
                SpringLayout.HEIGHT, passwordImage
        );
    }
    
    private JButton loginBtn;
    private void addButtons() {
        loginBtn = new JButton(StringDefine.LOGIN_BTN);
        loginBtn.setFont(FontDefine.LOGIN_INFO);
        loginBtn.setForeground(ColorDefine.LOGIN_BTN_TEXT);
        loginBtn.setBackground(ColorDefine.LOGIN_BTN_BKG);
        components.put("LoginButton", loginBtn);
        loginPanel.add(loginBtn);
    }
    private void setButtonConstraints() {
        SpringLayout layout = (SpringLayout)loginPanel.getLayout();
        
        // loginBtn
        layout.putConstraint(
                SpringLayout.NORTH, loginBtn,
                40,
                SpringLayout.SOUTH, passTextField
        );
        layout.putConstraint(
                SpringLayout.WEST, loginBtn,
                0,
                SpringLayout.WEST, passwordImage
        );
        layout.putConstraint(
                SpringLayout.EAST, loginBtn,
                0,
                SpringLayout.EAST, passTextField
        );
    }
    
    private JLabel schoolNameLabel;
    private void addFooter() {
        schoolNameLabel = new JLabel(StringDefine.SCHOOL_NAME);
        schoolNameLabel.setFont(FontDefine.LOGIN_FOOTER);
        schoolNameLabel.setForeground(ColorDefine.LOGIN_FOOTER);
        schoolNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginPanel.add(schoolNameLabel);
    }
    private void setFooterConstraints() {
        SpringLayout layout = (SpringLayout)loginPanel.getLayout();
        
        // schoolNameLabel
        layout.putConstraint(
                SpringLayout.SOUTH, schoolNameLabel,
                -10,
                SpringLayout.SOUTH, loginPanel
        );
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, schoolNameLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, loginPanel
        );
    }
    
}
