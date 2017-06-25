package controller.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import main.App;
import model.IResult;
import view.UIStartView;

public class UIStartController extends UIController implements IResult.ResponseReceiver {
    
    private static final Pattern PATTERN = Pattern.compile(
        "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    
    private final JFormattedTextField ipTextField1;
    private final JFormattedTextField ipTextField2;
    private final JFormattedTextField ipTextField3;
    private final JFormattedTextField ipTextField4;
    private final JFormattedTextField portTextField;
    private final JButton startButton;
    private final JLabel authorLabel;
    private final JTextArea historyTextArea;
    
    public UIStartController() {
        super(new UIStartView());
        
        ipTextField1 = (JFormattedTextField) super.findViewById("IPTextField1");
        ipTextField2 = (JFormattedTextField) super.findViewById("IPTextField2");
        ipTextField3 = (JFormattedTextField) super.findViewById("IPTextField3");
        ipTextField4 = (JFormattedTextField) super.findViewById("IPTextField4");
        
        portTextField = (JFormattedTextField) super.findViewById("PortTextField");
        
        startButton = (JButton) super.findViewById("StartButton");
        authorLabel = (JLabel) super.findViewById("AuthorLabel");
        
        historyTextArea = (JTextArea) super.findViewById("HistoryTextArea");
        
        setEventListeners();
    }
    
    private void setEventListeners() {
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (startButton.isEnabled()) {
                    boolean isCompleted = false;
                    String ip = String.format("%s.%s.%s.%s",
                            ipTextField1.getText(),
                            ipTextField2.getText(),
                            ipTextField3.getText(),
                            ipTextField4.getText()
                    );
                    ip = ip.trim();
                    if (ip.isEmpty()) {
                        historyTextArea.append("You need to set ip for server!\n");
                    } else if (PATTERN.matcher(ip).matches()) {
                        isCompleted = true;
                    } else {
                        historyTextArea.append("The IP is invalid!\n");
                    }

                    if (isCompleted) {
                        String portAsString = portTextField.getText().trim();
                        if (portAsString.isEmpty()) {
                            historyTextArea.append("You need to set port for server!\n");
                        } else {
                            try {
                                App.INSTANCE.start(ip, Integer.parseInt(portAsString), UIStartController.this);
                            } catch (NumberFormatException nfe) {
                                historyTextArea.append("The PORT is invalid, it should be an integer value!\n");
                            }
                        }
                    }
                } // end of if (startButton.isEnabled())
            }
        });
    }

    @Override
    public void receiveResult(Object obj) {
        if (obj instanceof Boolean) { // created server
            ipTextField1.setEnabled(false);
            ipTextField2.setEnabled(false);
            ipTextField3.setEnabled(false);
            ipTextField4.setEnabled(false);
            portTextField.setEnabled(false);
            startButton.setEnabled(false);
//            startButton.setVisible(false);
//            authorLabel.setVisible(true);
        } else { // message
            historyTextArea.append(String.format("%s\n", (String)obj));
        }
    }
    
}
