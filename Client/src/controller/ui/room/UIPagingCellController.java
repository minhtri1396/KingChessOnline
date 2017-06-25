package controller.ui.room;

import controller.ui.UIController;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.IResult;
import model.define.ColorDefine;
import view.room.UIPagingCellView;

public class UIPagingCellController extends UIController {
    
    private final JPanel pagingPanel;
    private final JLabel titleLabel;
    private final UIPagingCellController This;
    
    private IResult.ResponseReceiver mouseClickCallback;
    
    public UIPagingCellController() {
        super(new UIPagingCellView());
        
        This = this;
        
        titleLabel = (JLabel)super.findViewById("TitleLabel");
        pagingPanel = (JPanel)super.findViewById("PagingPanel");
        
        setEventListeners();
    }
    
    private void setEventListeners() {
        pagingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (mouseClickCallback != null) {
                    mouseClickCallback.receiveResult(This);
                }
            }
        });
    }
    
    public void setMouseListener(IResult.ResponseReceiver listener) {
        mouseClickCallback = listener;
    }
    
    public void setTitle(String title) {
        titleLabel.setText(title);
    }
    
    public void check() {
        titleLabel.setForeground(Color.WHITE);
        pagingPanel.setBackground(ColorDefine.PAGING_CELL_CHECKED);
    }
    
    public void uncheck() {
        titleLabel.setForeground(Color.BLACK);
        pagingPanel.setBackground(ColorDefine.PAGING_CELL_UNCHECKED);
    }
    
}
