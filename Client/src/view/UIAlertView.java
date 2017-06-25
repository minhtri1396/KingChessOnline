package view;

import java.awt.Color;
import java.awt.Cursor;
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
    
    protected JPanel infoPanel;
    private JTextPane titleLabel;
    private JPanel titlePanel;
    protected JTextPane content;
    private JButton okButton;
    private JButton cancelButton;
    
    public UIAlertView() {
        super();
        super.setBackground(ColorDefine.ALERT_BORDER);
        super.setPreferredSize(SizeDefine.ALERT);
        super.setSize(SizeDefine.ALERT);
        
        infoPanel = new JPanel();
        infoPanel.setBackground(ColorDefine.ALERT_BKG);
        super.add(infoPanel);
        
        setInfo();
    }
    
    private void setInfo() {
        addComponents();
        setConstraints();
    }
    
    protected void addComponents() {
        titlePanel = new JPanel();
        titlePanel.setPreferredSize(new Dimension(0, 40));
        titlePanel.setBackground(ColorDefine.ALERT_TITLE_BKG);
        infoPanel.add(titlePanel);
        
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
        infoPanel.add(content);
        
        okButton = new JButton("XÁC NHẬN");
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        okButton.setPreferredSize(SizeDefine.ALERT_OK_BUTTON);
        okButton.setBackground(ColorDefine.ALERT_OK_BUTTON);
        okButton.setForeground(Color.WHITE);
        this.components.put("OKButton", okButton);
        infoPanel.add(okButton);
        
        cancelButton = new JButton("HỦY");
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.setPreferredSize(SizeDefine.ALERT_CANCEL_BUTTON);
        cancelButton.setBackground(ColorDefine.ALERT_CANCEL_BUTTON);
        cancelButton.setForeground(Color.WHITE);
        this.components.put("CancelButton", cancelButton);
        infoPanel.add(cancelButton);
    }
    
    protected void setConstraints() {
        SpringLayout mainLayout = new SpringLayout();
        this.setLayout(mainLayout);
        
        SpringLayout infoLayout = new SpringLayout();
        infoPanel.setLayout(infoLayout);
        
        SpringLayout titleLayout = new SpringLayout();
        titlePanel.setLayout(titleLayout);
        
        // infoPanel
        mainLayout.putConstraint(
                SpringLayout.NORTH, infoPanel,
                1,
                SpringLayout.NORTH, this
        );
        mainLayout.putConstraint(
                SpringLayout.WEST, infoPanel,
                1,
                SpringLayout.WEST, this
        );
        mainLayout.putConstraint(
                SpringLayout.SOUTH, infoPanel,
                -1,
                SpringLayout.SOUTH, this
        );
        mainLayout.putConstraint(
                SpringLayout.EAST, infoPanel,
                -1,
                SpringLayout.EAST, this
        );
        
        // titlePanel
        infoLayout.putConstraint(
                SpringLayout.NORTH, titlePanel,
                0,
                SpringLayout.NORTH, infoPanel
        );
        infoLayout.putConstraint(
                SpringLayout.WEST, titlePanel,
                0,
                SpringLayout.WEST, infoPanel
        );
        infoLayout.putConstraint(
                SpringLayout.EAST, titlePanel,
                0,
                SpringLayout.EAST, infoPanel
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
        infoLayout.putConstraint(
                SpringLayout.NORTH, content,
                25,
                SpringLayout.SOUTH, titlePanel
        );
        infoLayout.putConstraint(
                SpringLayout.WEST, content,
                8,
                SpringLayout.WEST, infoPanel
        );
        infoLayout.putConstraint(
                SpringLayout.SOUTH, content,
                -10,
                SpringLayout.NORTH, okButton
        );
        infoLayout.putConstraint(
                SpringLayout.EAST, content,
                -8,
                SpringLayout.EAST, infoPanel
        );
        
        // okButton
        infoLayout.putConstraint(
                SpringLayout.SOUTH, okButton,
                -18,
                SpringLayout.SOUTH, infoPanel
        );
        infoLayout.putConstraint(
                SpringLayout.EAST, okButton,
                -20,
                SpringLayout.EAST, infoPanel
        );
        
        // cancelButton
        infoLayout.putConstraint(
                SpringLayout.SOUTH, cancelButton,
                0,
                SpringLayout.SOUTH, okButton
        );
        infoLayout.putConstraint(
                SpringLayout.EAST, cancelButton,
                -15,
                SpringLayout.WEST, okButton
        );
    }
    
}
