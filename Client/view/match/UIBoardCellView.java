package view.match;

import java.awt.Cursor;
import javax.swing.JLabel;
import javax.swing.SpringLayout;
import view.UIView;

public class UIBoardCellView extends UIView {
    
    private final JLabel pieceImageLabel;
    
    public UIBoardCellView() {
        super();
        
        this.pieceImageLabel = new JLabel();
        
        addComponents();
        setConstraints();
    }
    
    private void addComponents() {
        super.components.put("PieceImageLabel", pieceImageLabel);
        super.add(pieceImageLabel);
        
        super.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void setConstraints() {
        SpringLayout layout = new SpringLayout();
        super.setLayout(layout);
        
        // pieceImageLabel
        layout.putConstraint(
                SpringLayout.HORIZONTAL_CENTER, pieceImageLabel,
                0,
                SpringLayout.HORIZONTAL_CENTER, this
        );
        layout.putConstraint(
                SpringLayout.VERTICAL_CENTER, pieceImageLabel,
                0,
                SpringLayout.VERTICAL_CENTER, this
        );
    }
    
}
