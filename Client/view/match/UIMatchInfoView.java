package view.match;

import model.define.ColorDefine;
import model.define.FontDefine;
import model.define.SizeDefine;
import model.define.StringDefine;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import view.UIView;

public class UIMatchInfoView extends UIView {
    
    private JPanel infoPanel;
    private JLabel roomNameLabel;
    private JPanel whiteLabel;
    private UITimerView whiteTimer;
    private JPanel blackLabel;
    private UITimerView blackTimer;
    
    private JPanel buttonPanel;
    private JButton drawButton;
    private JButton exitButton;
    
    private JTextArea historyTextArea;
    private JScrollPane historyPanel;
    
    public UIMatchInfoView() {
        super();
        super.setBackground(ColorDefine.MATCH_INFO_MENU);

        addComponents();
        setConstraints();
    }

    private void addComponents() {
        addInfoArea();
        addButtonArea();
        addHistoryArea();
    }
    
    private void addInfoArea() {
        infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(0, 160));
        infoPanel.setBackground(ColorDefine.MATCH_INFO_MENU);
        this.add(infoPanel);
        
        roomNameLabel = new JLabel();
        roomNameLabel.setFont(FontDefine.ROOM_NAME);
        roomNameLabel.setForeground(ColorDefine.ROOM_NAME);
        this.components.put("RoomNameLabel", roomNameLabel);
        this.add(roomNameLabel);
        
        whiteLabel = new JPanel();
        whiteLabel.setPreferredSize(new Dimension(25, 25));
        whiteLabel.setBackground(Color.WHITE);
        infoPanel.add(whiteLabel);
        
        whiteTimer = new UITimerView();
        this.components.put("WhiteTimer", whiteTimer);
        infoPanel.add(whiteTimer);
        
        blackLabel = new JPanel();
        blackLabel.setPreferredSize(new Dimension(25, 25));
        blackLabel.setBackground(Color.BLACK);
        infoPanel.add(blackLabel);
        
        blackTimer = new UITimerView();
        this.components.put("BlackTimer", blackTimer);
        infoPanel.add(blackTimer);
    }
    
    private void addButtonArea() {
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(0, 70));
        buttonPanel.setBackground(ColorDefine.MATCH_INFO_MENU);
        this.components.put("ButtonPanel", buttonPanel);
        this.add(buttonPanel);
        
        drawButton = new JButton(StringDefine.DRAW_BUTTON);
        drawButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        drawButton.setFont(FontDefine.DRAW_BUTTON);
        drawButton.setPreferredSize(SizeDefine.DRAW_BUTTON);
        drawButton.setBackground(ColorDefine.MATCH_DRAW_BUTTON);
        drawButton.setForeground(Color.WHITE);
        this.components.put("DrawButton", drawButton);
        buttonPanel.add(drawButton);
        
        exitButton = new JButton(StringDefine.EXIT_BUTTON);
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        exitButton.setFont(FontDefine.EXIT_BUTTON);
        exitButton.setPreferredSize(SizeDefine.EXIT_BUTTON);
        exitButton.setBackground(ColorDefine.MATCH_EXIT_BUTTON);
        exitButton.setForeground(Color.WHITE);
        this.components.put("ExitButton", exitButton);
        buttonPanel.add(exitButton);
    }
    
    private void addHistoryArea() {
        historyTextArea = new JTextArea();
        historyTextArea.setEditable(false);
        this.components.put("HistoryTextArea", historyTextArea);
        
        historyPanel = new JScrollPane(historyTextArea);
        this.add(historyPanel);
    }

    private void setConstraints() {
        this.setLayout(new SpringLayout());
        
        setInfoAreaConstraints();
        setButtonAreaConstraints();
        setHistoryAreaConstraints();
    }

    private void setInfoAreaConstraints() {
        SpringLayout layout = new SpringLayout();
        infoPanel.setLayout(layout);
        
        // whiteLabel
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER, whiteLabel,
                40,
                SpringLayout.VERTICAL_CENTER, infoPanel
        );
        layout.putConstraint(
                SpringLayout.WEST, whiteLabel,
                25,
                SpringLayout.WEST, infoPanel
        );
        
        // whiteTimer
        layout.putConstraint(
                SpringLayout.WEST, whiteTimer,
                25,
                SpringLayout.EAST, whiteLabel
        );
        layout.putConstraint(
                SpringLayout.SOUTH, whiteTimer,
                0,
                SpringLayout.SOUTH, whiteLabel
        );
        layout.putConstraint(
                SpringLayout.EAST, whiteTimer,
                -25,
                SpringLayout.EAST, infoPanel
        );
        
        
        // blackLabel
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER, blackLabel,
                -25,
                SpringLayout.VERTICAL_CENTER, infoPanel
        );
        layout.putConstraint(
                SpringLayout.WEST, blackLabel,
                25,
                SpringLayout.WEST, infoPanel
        );
        
        // blackTimer
        layout.putConstraint(
                SpringLayout.WEST, blackTimer,
                25,
                SpringLayout.EAST, blackLabel
        );
        layout.putConstraint(
                SpringLayout.SOUTH, blackTimer,
                0,
                SpringLayout.SOUTH, blackLabel
        );
        layout.putConstraint(
                SpringLayout.EAST, blackTimer,
                -25,
                SpringLayout.EAST, infoPanel
        );
        
        // infoPanel
        SpringLayout mainLayout = (SpringLayout) this.getLayout();
        mainLayout.putConstraint(
                SpringLayout.NORTH, infoPanel,
                0,
                SpringLayout.SOUTH, roomNameLabel
        );
        mainLayout.putConstraint(
                SpringLayout.WEST, infoPanel,
                10,
                SpringLayout.WEST, this
        );
        mainLayout.putConstraint(
                SpringLayout.EAST, infoPanel,
                -10,
                SpringLayout.EAST, this
        );
        
        // roomName
        mainLayout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, roomNameLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, this
        );
        mainLayout.putConstraint(
                SpringLayout.NORTH, roomNameLabel,
                5,
                SpringLayout.NORTH, this
        );
    }
    
    private void setButtonAreaConstraints() {
        SpringLayout layout = new SpringLayout();
        buttonPanel.setLayout(layout);
        
        // drawButton
        layout.putConstraint(
                SpringLayout.NORTH, drawButton,
                2,
                SpringLayout.NORTH, buttonPanel
        );
        layout.putConstraint(
                SpringLayout.WEST, drawButton,
                0,
                SpringLayout.WEST, buttonPanel
        );
        layout.putConstraint(
                SpringLayout.EAST, drawButton,
                0,
                SpringLayout.EAST, buttonPanel
        );
        
        // exitButton
        layout.putConstraint(
                SpringLayout.WEST, exitButton,
                0,
                SpringLayout.WEST, buttonPanel
        );
        layout.putConstraint(
                SpringLayout.SOUTH, exitButton,
                -2,
                SpringLayout.SOUTH, buttonPanel
        );
        layout.putConstraint(
                SpringLayout.EAST, exitButton,
                0,
                SpringLayout.EAST, buttonPanel
        );
        
        // buttonPanel
        SpringLayout mainLayout = (SpringLayout) this.getLayout();
        mainLayout.putConstraint(
                SpringLayout.NORTH, buttonPanel,
                10,
                SpringLayout.SOUTH, infoPanel
        );
        mainLayout.putConstraint(
                SpringLayout.WEST, buttonPanel,
                10,
                SpringLayout.WEST, this
        );
        mainLayout.putConstraint(
                SpringLayout.EAST, buttonPanel,
                -10,
                SpringLayout.EAST, this
        );
        
    }

    private void setHistoryAreaConstraints() {
        SpringLayout layout = (SpringLayout) this.getLayout();
        
        // historyPanel
        layout.putConstraint(
                SpringLayout.NORTH, historyPanel,
                10,
                SpringLayout.SOUTH, buttonPanel
        );
        layout.putConstraint(
                SpringLayout.WEST, historyPanel,
                10,
                SpringLayout.WEST, this
        );
        layout.putConstraint(
                SpringLayout.SOUTH, historyPanel,
                -10,
                SpringLayout.SOUTH, this
        );
        layout.putConstraint(
                SpringLayout.EAST, historyPanel,
                -10,
                SpringLayout.EAST, this
        );
        
    }
}
