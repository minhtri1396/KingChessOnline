package view.match;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import model.define.ColorDefine;
import model.define.FontDefine;
import view.UIView;

public class UIPawnPromotionView extends UIView {
    
    private JLabel titleLabel;
    private JButton bishopButton;
    private JButton knightButton;
    private JButton queenButton;
    private JButton rookButton;
    
    public UIPawnPromotionView() {
        super();
        super.setBackground(ColorDefine.MATCH_BKG);
        super.setSize(new Dimension(300, 400));
        
        addComponents();
        setConstraints();
    }

    private void addComponents() {
        titleLabel = new JLabel("THĂNG CẤP CHỐT");
        titleLabel.setFont(FontDefine.PROMOTION_TITLE);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(titleLabel);
        
        Dimension buttonSize = new Dimension(0, 50);
        Color buttonColor = new Color(202, 137, 62);
        
        bishopButton = new JButton("BISHOP (TƯỢNG)");
        bishopButton.setPreferredSize(buttonSize);
        bishopButton.setFont(FontDefine.PROMOTION_BUTTON);
        bishopButton.setBackground(buttonColor);
        bishopButton.setForeground(Color.WHITE);
        bishopButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.components.put("BishopButton", bishopButton);
        this.add(bishopButton);
        
        knightButton = new JButton("KNIGHT (MÃ)");
        knightButton.setPreferredSize(buttonSize);
        knightButton.setFont(FontDefine.PROMOTION_BUTTON);
        knightButton.setBackground(buttonColor);
        knightButton.setForeground(Color.WHITE);
        knightButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.components.put("KnightButton", knightButton);
        this.add(knightButton);
        
        queenButton = new JButton("QUEEN (HẬU)");
        queenButton.setPreferredSize(buttonSize);
        queenButton.setFont(FontDefine.PROMOTION_BUTTON);
        queenButton.setBackground(buttonColor);
        queenButton.setForeground(Color.WHITE);
        queenButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.components.put("QueenButton", queenButton);
        this.add(queenButton);
        
        rookButton = new JButton("ROOK (XE)");
        rookButton.setPreferredSize(buttonSize);
        rookButton.setFont(FontDefine.PROMOTION_BUTTON);
        rookButton.setBackground(buttonColor);
        rookButton.setForeground(Color.WHITE);
        rookButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.components.put("RookButton", rookButton);
        this.add(rookButton);
    }

    private void setConstraints() {
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        
        // titleLabel
        layout.putConstraint(
                SpringLayout.NORTH, titleLabel,
                25,
                SpringLayout.NORTH, this
        );
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, titleLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, this
        );
        
        // bishopButton
        layout.putConstraint(
                SpringLayout.NORTH, bishopButton,
                25,
                SpringLayout.SOUTH, titleLabel
        );
        layout.putConstraint(
                SpringLayout.WEST, bishopButton,
                10,
                SpringLayout.WEST, this
        );
        layout.putConstraint(
                SpringLayout.EAST, bishopButton,
                -10,
                SpringLayout.EAST, this
        );
        
        // knightButton
        layout.putConstraint(
                SpringLayout.NORTH, knightButton,
                15,
                SpringLayout.SOUTH, bishopButton
        );
        layout.putConstraint(
                SpringLayout.WEST, knightButton,
                10,
                SpringLayout.WEST, this
        );
        layout.putConstraint(
                SpringLayout.EAST, knightButton,
                -10,
                SpringLayout.EAST, this
        );
        
        // queenButton
        layout.putConstraint(
                SpringLayout.NORTH, queenButton,
                15,
                SpringLayout.SOUTH, knightButton
        );
        layout.putConstraint(
                SpringLayout.WEST, queenButton,
                10,
                SpringLayout.WEST, this
        );
        layout.putConstraint(
                SpringLayout.EAST, queenButton,
                -10,
                SpringLayout.EAST, this
        );
        
        // rookButton
        layout.putConstraint(
                SpringLayout.NORTH, rookButton,
                15,
                SpringLayout.SOUTH, queenButton
        );
        layout.putConstraint(
                SpringLayout.WEST, rookButton,
                10,
                SpringLayout.WEST, this
        );
        layout.putConstraint(
                SpringLayout.EAST, rookButton,
                -10,
                SpringLayout.EAST, this
        );
    }
    
}
