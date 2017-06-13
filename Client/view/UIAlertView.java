package view;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import model.define.ColorDefine;
import model.define.FontDefine;
import model.define.SizeDefine;
import util.WrapEditorKit;

public class UIAlertView extends UIView {
    
    private JTextPane titleLabel;
    private JPanel titlePanel;
    private JTextPane content;
    private JButton okButton;
    private JButton cancelButton;
    
    public UIAlertView() {
        super();
        super.setBackground(ColorDefine.ALERT_BKG);
        super.setPreferredSize(SizeDefine.ALERT);
        super.setSize(SizeDefine.ALERT);
        
        addComponents();
        setConstraints();
    }
    
    private void addComponents() {
        titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(0, 40));
        titlePanel.setBackground(ColorDefine.ALERT_TITLE_BKG);
        this.add(titlePanel);
        
        titleLabel = new JTextPane();
        titleLabel.setFont(FontDefine.ALERT_TITLE);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setEditorKit(new WrapEditorKit());
        titleLabel.setOpaque(false);
        titleLabel.setEditable(false);
        components.put("TitleLabel", titleLabel);
        titlePanel.add(titleLabel);
        
        content = new JTextPane();
        content.setFont(FontDefine.ALERT_CONTENT);
        content.setForeground(ColorDefine.ALERT_CONTENT);
        content.setEditorKit(new WrapEditorKit());
        content.setOpaque(false);
        content.setEditable(false);
        components.put("Content", content);
        this.add(content);
        
        okButton = new JButton("XÁC NHẬN");
        okButton.setPreferredSize(SizeDefine.ALERT_OK_BUTTON);
        okButton.setBackground(ColorDefine.ALERT_OK_BUTTON);
        okButton.setForeground(Color.WHITE);
        this.components.put("OKButton", okButton);
        this.add(okButton);
        
        cancelButton = new JButton("HỦY");
        cancelButton.setPreferredSize(SizeDefine.ALERT_CANCEL_BUTTON);
        cancelButton.setBackground(ColorDefine.ALERT_CANCEL_BUTTON);
        cancelButton.setForeground(Color.WHITE);
        this.components.put("CancelButton", cancelButton);
        this.add(cancelButton);
    }
    
    private void setConstraints() {
        SpringLayout mainLayout = new SpringLayout();
        this.setLayout(mainLayout);
        
        SpringLayout titleLayout = new SpringLayout();
        titlePanel.setLayout(titleLayout);
        
        // titlePanel
        mainLayout.putConstraint(
                SpringLayout.NORTH, titlePanel,
                0,
                SpringLayout.NORTH, this
        );
        mainLayout.putConstraint(
                SpringLayout.WEST, titlePanel,
                0,
                SpringLayout.WEST, this
        );
        mainLayout.putConstraint(
                SpringLayout.EAST, titlePanel,
                0,
                SpringLayout.EAST, this
        );
        
        // titleLabel
        titleLayout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, titleLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, titlePanel
        );
        titleLayout.putConstraint(
                SpringLayout.VERTICAL_CENTER, titleLabel,
                0,
                SpringLayout.VERTICAL_CENTER, titlePanel
        );
        
        // content
        mainLayout.putConstraint(
                SpringLayout.NORTH, content,
                25,
                SpringLayout.SOUTH, titlePanel
        );
        mainLayout.putConstraint(
                SpringLayout.WEST, content,
                8,
                SpringLayout.WEST, this
        );
        mainLayout.putConstraint(
                SpringLayout.SOUTH, content,
                -10,
                SpringLayout.NORTH, okButton
        );
        mainLayout.putConstraint(
                SpringLayout.EAST, content,
                -8,
                SpringLayout.EAST, this
        );
        
        // okButton
        mainLayout.putConstraint(
                SpringLayout.SOUTH, okButton,
                -18,
                SpringLayout.SOUTH, this
        );
        mainLayout.putConstraint(
                SpringLayout.EAST, okButton,
                -20,
                SpringLayout.EAST, this
        );
        
        // cancelButton
        mainLayout.putConstraint(
                SpringLayout.SOUTH, cancelButton,
                0,
                SpringLayout.SOUTH, okButton
        );
        mainLayout.putConstraint(
                SpringLayout.EAST, cancelButton,
                -15,
                SpringLayout.WEST, okButton
        );
    }
    
}
