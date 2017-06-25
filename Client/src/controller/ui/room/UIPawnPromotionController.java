package controller.ui.room;

import controller.ui.UISubWindowController;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import main.App;
import model.IResult;
import model.type.enumeration.EPieces;
import view.match.UIPawnPromotionView;

public class UIPawnPromotionController extends UISubWindowController {
    
    private final JButton bishopButton;
    private final JButton knightButton;
    private final JButton queenButton;
    private final JButton rookButton;
    
    private Object msg;
    private IResult.ResponseReceiver buttonListener;
    
    public UIPawnPromotionController() {
        super(new UIPawnPromotionView());
        
        bishopButton = (JButton)super.findViewById("BishopButton");
        knightButton = (JButton)super.findViewById("KnightButton");
        queenButton = (JButton)super.findViewById("QueenButton");
        rookButton = (JButton)super.findViewById("RookButton");
        
        setEventListeners();
    }
    
    private void setEventListeners() {
        bishopButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                msg = EPieces.BISHOP;
                close();
            }
        });
        
        knightButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                msg = EPieces.KNIGHT;
                close();
            }
        });
        
        queenButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                msg = EPieces.QUEEN;
                close();
            }
        });
        
        rookButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                msg = EPieces.ROOK;
                close();
            }
        });
    }
    
    @Override
    public void show() {
        msg = null;
        App.getInstance().setEnabled(false);
        super.show();
    }
    
    @Override
    public void close() {
        if (buttonListener != null) {
            buttonListener.receiveResult(msg);
        }
        super.close();
        App.getInstance().setEnabled(true);
        App.getInstance().setVisible(true);
    }
    
    public void setButtonListener(IResult.ResponseReceiver listener) {
        buttonListener = listener;
    }
    
}
