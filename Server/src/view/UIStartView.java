package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

public class UIStartView extends UIView {
    
    private JLabel titleLabel;
    private JLabel ipLabel;
    private JFormattedTextField ipTextField1;
    private JFormattedTextField ipTextField2;
    private JFormattedTextField ipTextField3;
    private JFormattedTextField ipTextField4;
    private JLabel portLabel;
    private JFormattedTextField portTextField;
    private JButton startButton;
    private JTextArea historyTextArea;
    private JLabel authorLabel;
    private JScrollPane historyPanel;
    
    public UIStartView() {
        super();
        super.setBackground(Color.WHITE);
        super.setSize(new Dimension(800, 500));
        
        addComponents();
        setConstraints();
    }

    private void addComponents() {
        addTitleLabel();
        addIPTextFields();
        addPortTextFields();
        addStartButton();
        addHistoryTextArea();
    }
    
    private void addTitleLabel() {
        titleLabel = new JLabel("SERVER");
        titleLabel.setFont(new Font("sans-serif", Font.BOLD, 25));
        titleLabel.setForeground(new Color(2, 136, 209));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(titleLabel);
    }
    
    private void addIPTextFields() {
        Dimension ipFieldSize = new Dimension(45, 25);
        Font font = new Font("sans-serif", Font.PLAIN, 20);
        
        ipLabel = new JLabel("IP");
        ipLabel.setFont(font);
        this.add(ipLabel);
        
        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(255);
        formatter.setAllowsInvalid(false);
        ipTextField1 = new JFormattedTextField(formatter);
        ipTextField1.setText("127");
        ipTextField1.setFont(font);
        ipTextField1.setPreferredSize(ipFieldSize);
        this.components.put("IPTextField1", ipTextField1);
        this.add(ipTextField1);
        
        ipTextField2 = new JFormattedTextField(formatter);
        ipTextField2.setText("0");
        ipTextField2.setFont(font);
        ipTextField2.setPreferredSize(ipFieldSize);
        this.components.put("IPTextField2", ipTextField2);
        this.add(ipTextField2);
        
        ipTextField3 = new JFormattedTextField(formatter);
        ipTextField3.setText("0");
        ipTextField3.setFont(font);
        ipTextField3.setPreferredSize(ipFieldSize);
        this.components.put("IPTextField3", ipTextField3);
        this.add(ipTextField3);
        
        ipTextField4 = new JFormattedTextField(formatter);
        ipTextField4.setText("1");
        ipTextField4.setFont(font);
        ipTextField4.setPreferredSize(ipFieldSize);
        this.components.put("IPTextField4", ipTextField4);
        this.add(ipTextField4);
    }
    
    private void addPortTextFields() {
        Font font = new Font("sans-serif", Font.PLAIN, 20);
        
        portLabel = new JLabel("PORT");
        portLabel.setFont(font);
        this.add(portLabel);
        
        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(65535);
        formatter.setAllowsInvalid(false);
        portTextField = new JFormattedTextField(formatter);
        portTextField.setText("3000");
        portTextField.setFont(font);
        portTextField.setPreferredSize(new Dimension(80, 25));
        this.components.put("PortTextField", portTextField);
        this.add(portTextField);
    }

    private void addStartButton() {
        startButton = new JButton("START");
        startButton.setFont(new Font("sans-serif", Font.PLAIN, 20));
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(2, 136, 209));
        startButton.setPreferredSize(new Dimension(0, 40));
        this.components.put("StartButton", startButton);
        this.add(startButton);
        
        authorLabel = new JLabel("1412278 - 1412573 - 1412648");
        authorLabel.setForeground(new Color(2, 136, 209));
        authorLabel.setFont(new Font("sans-serif", Font.PLAIN, 20));
        authorLabel.setVisible(false);
        this.components.put("AuthorLabel", authorLabel);
        this.add(authorLabel);
    }
    
    private void addHistoryTextArea() {
        historyTextArea = new JTextArea();
        historyTextArea.setEditable(false);
        this.components.put("HistoryTextArea", historyTextArea);
        
        historyPanel = new JScrollPane(historyTextArea);
        this.add(historyPanel);
    }
    
    private void setConstraints() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        
        // titleLabel
        layout.putConstraint(
                SpringLayout.NORTH, titleLabel,
                10,
                SpringLayout.NORTH, this
        );
        layout.putConstraint(
                SpringLayout.WEST, titleLabel,
                10,
                SpringLayout.WEST, this
        );
        layout.putConstraint(
                SpringLayout.EAST, titleLabel,
                -10,
                SpringLayout.WEST, historyPanel
        );
        // ipLabel
        layout.putConstraint(
                SpringLayout.NORTH, ipLabel,
                20,
                SpringLayout.SOUTH, titleLabel
        );
        layout.putConstraint(
                SpringLayout.WEST, ipLabel,
                15,
                SpringLayout.WEST, this
        );
        
        // ipTextField1
        layout.putConstraint(
                SpringLayout.SOUTH, ipTextField1,
                0,
                SpringLayout.SOUTH, ipLabel
        );
        layout.putConstraint(
                SpringLayout.WEST, ipTextField1,
                10,
                SpringLayout.EAST, ipLabel
        );
        // ipTextField2
        layout.putConstraint(
                SpringLayout.NORTH, ipTextField2,
                0,
                SpringLayout.NORTH, ipTextField1
        );
        layout.putConstraint(
                SpringLayout.WEST, ipTextField2,
                5,
                SpringLayout.EAST, ipTextField1
        );
        // ipTextField3
        layout.putConstraint(
                SpringLayout.NORTH, ipTextField3,
                0,
                SpringLayout.NORTH, ipTextField2
        );
        layout.putConstraint(
                SpringLayout.WEST, ipTextField3,
                5,
                SpringLayout.EAST, ipTextField2
        );
        // ipTextField4
        layout.putConstraint(
                SpringLayout.NORTH, ipTextField4,
                0,
                SpringLayout.NORTH, ipTextField3
        );
        layout.putConstraint(
                SpringLayout.WEST, ipTextField4,
                5,
                SpringLayout.EAST, ipTextField3
        );
        
        // portLabel
        layout.putConstraint(
                SpringLayout.NORTH, portLabel,
                15,
                SpringLayout.SOUTH, ipTextField1
        );
        layout.putConstraint(
                SpringLayout.WEST, portLabel,
                15,
                SpringLayout.WEST, this
        );
        
        // portTextField
        layout.putConstraint(
                SpringLayout.SOUTH, portTextField,
                0,
                SpringLayout.SOUTH, portLabel
        );
        layout.putConstraint(
                SpringLayout.WEST, portTextField,
                10,
                SpringLayout.EAST, portLabel
        );
        
        // startButton
        layout.putConstraint(
                SpringLayout.WEST, startButton,
                10,
                SpringLayout.WEST, this
        );
        layout.putConstraint(
                SpringLayout.SOUTH, startButton,
                -10,
                SpringLayout.SOUTH, this
        );
        layout.putConstraint(
                SpringLayout.EAST, startButton,
                -10,
                SpringLayout.EAST, this
        );
        
        // authorLabel
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, authorLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, startButton
        );
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER, authorLabel,
                0,
                SpringLayout.VERTICAL_CENTER, startButton
        );
        
        // historyPanel
        layout.putConstraint(
                SpringLayout.NORTH, historyPanel,
                20,
                SpringLayout.NORTH, this
        );
        layout.putConstraint(
                SpringLayout.WEST, historyPanel,
                15,
                SpringLayout.EAST, ipTextField4
        );
        layout.putConstraint(
                SpringLayout.SOUTH, historyPanel,
                -10,
                SpringLayout.NORTH, startButton
        );
        layout.putConstraint(
                SpringLayout.EAST, historyPanel,
                -10,
                SpringLayout.EAST, this
        );
    }
    
}
