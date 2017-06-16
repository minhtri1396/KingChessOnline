package controller.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JTextPane;
import main.App;
import model.IResult;
import view.UIAlertView;
import view.UIView;

public class UIAlertController extends UISubWindowController {
    
    private final JTextPane titleLabel;
    private final JTextPane content;
    private final JButton okButton;
    private final JButton cancelButton;
    
    private IResult.Responser okButtonListener;
    private IResult.Responser cancelButtonListener;
    
    public UIAlertController() {
        this(new UIAlertView());
    }
    
    public UIAlertController(UIView uiView) {
        super(uiView);
        
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
    }
    
    @Override
    public void show() {
        App.getInstance().setEnabled(false);
        super.show();
    }
    
    @Override
    public void close() {
        super.close();
        App.getInstance().setEnabled(true);
        App.getInstance().setVisible(true);
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
    
    public void setHideOKButton(boolean hide) {
        okButton.setVisible(!hide);
    }
    
    public void setHideCancelButton(boolean hide) {
        cancelButton.setVisible(!hide);
    }
    
    public void setOKButtonTitle(String tilte) {
        okButton.setText(tilte);
    }
    
    public void setCancelButtonTitle(String tilte) {
        cancelButton.setText(tilte);
    }
    
}
