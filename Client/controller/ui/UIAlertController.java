package controller.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.JButton;
import javax.swing.JTextPane;
import model.IResult;
import view.UIAlertView;

public class UIAlertController extends UISubWindowController {
    
    private final JTextPane titleLabel;
    private final JTextPane content;
    private final JButton okButton;
    private final JButton cancelButton;
    
    private IResult.Responser okButtonListener;
    private IResult.Responser cancelButtonListener;
    
    public UIAlertController() {
        super(new UIAlertView());
        
        titleLabel = (JTextPane)super.findViewById("TitleLabel");
        content = (JTextPane)super.findViewById("Content");
        okButton = (JButton)super.findViewById("OKButton");
        cancelButton = (JButton)super.findViewById("CancelButton");
        
        setEventListeners();
        
        super.dialog.setUndecorated(true);
    }
    
    private void setEventListeners() {
        okButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                close();
                if (okButtonListener != null) {
                    okButtonListener.response();
                }
            }
        });
        
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                close();
                if (cancelButtonListener != null) {
                    cancelButtonListener.response();
                }
            }
        });
        
        super.dialog.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {}
            
            @Override
            public void windowLostFocus(WindowEvent e) {
                close();
                if (cancelButtonListener != null) {
                    cancelButtonListener.response();
                }
            }
        });
    }
    
    @Override
    public void setTitle(String title) {
        titleLabel.setText(title);
    }
    
    public void setContent(String content) {
        this.content.setText(content);
    }
    
    public void setOKButtonListener(IResult.Responser okButtonListener) {
        this.okButtonListener = okButtonListener;
    }
    
    public void setCancelButtonListener(IResult.Responser cancelButtonListener) {
        this.cancelButtonListener = cancelButtonListener;
    }
    
}
