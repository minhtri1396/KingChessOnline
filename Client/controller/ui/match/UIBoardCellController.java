package controller.ui.match;

import controller.ui.UIController;
import model.define.ColorDefine;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import model.IResult;
import view.match.UIBoardCellView;

public class UIBoardCellController extends UIController {
    
    private final UIBoardCellController This = this;
    private final JLabel pieceImageLabel;
    private final Color color;
    
    public UIBoardCellController(int id, Color color) {
        super(new UIBoardCellView());
        super.uiView.setBackground(color);
        super.id = id;
        
        this.color = color;
        
        pieceImageLabel = (JLabel)super.findViewById("PieceImageLabel");
    }
    
    public void setPieceImage(ImageIcon image) {
        pieceImageLabel.setIcon(image);
    }
    
    public ImageIcon getPieceImage() {
        return (ImageIcon)pieceImageLabel.getIcon();
    }
    
    public void clearPieceImage() {
        pieceImageLabel.setIcon(null);
    }
    
    public void setSize(Dimension size) {
        super.uiView.setPreferredSize(size);
    }
    
    public void addMouseClickListener(IResult.ResponseReceiver mouseClickCallback) {
        super.uiView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                mouseClickCallback.receiveResult(This);
            }
        });
    }
    
    public void check() {
        this.uiView.setBackground(ColorDefine.MATCH_CELL_CHECKED);
    }
    
    public void uncheck() {
        this.uiView.setBackground(this.color);
    }
    
}
