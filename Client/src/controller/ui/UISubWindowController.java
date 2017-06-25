package controller.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JDialog;
import model.IResult;
import view.UIView;

public class UISubWindowController extends UIController {
    
    private String note;
    
    protected JDialog dialog;
    
    protected IResult.ResponseReceiver receiver;
    
    public void setResponseReceiver(IResult.ResponseReceiver receiver) {
        this.receiver = receiver;
    }
    
    public UISubWindowController(UIView uiView) {
        super(uiView);
        
        createWindow();
    }
    
    private void createWindow() {
        //JDialog.setDefaultLookAndFeelDecorated(true);
        dialog = new JDialog();
        dialog.setResizable(false);
        dialog.setSize(uiView.getSize());
        
        dialog.setContentPane(uiView);
        
        dialog.setVisible(false);
        dialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                close();
            }
        });
    }
    
    public void resize(Dimension size) {
        uiView.setSize(size);
        dialog.setSize(size);
        centerWindow();
    }
    
    public void close() {
        dialog.setVisible(false);
    }
    
    public void setTitle(String title) {
        dialog.setTitle(title);
    }
    
    public void show() {
        dialog.setVisible(true);
    }
    
    public void centerWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - dialog.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - dialog.getHeight()) / 2);
        dialog.setLocation(x, y);
    }
    
    public void focus() {
        dialog.setVisible(true);
        dialog.requestFocusInWindow();
    }
    
    public void setNote(String note) {
        this.note = note;
    }
    
    public String getNote() {
        return note;
    }
    
}
