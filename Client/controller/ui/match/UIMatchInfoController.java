package controller.ui.match;

import controller.ui.UIController;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import model.IResult;
import model.type.Time;
import model.type.enumeration.EPieceColors;
import view.UIView;
import view.match.UITimerView;

public class UIMatchInfoController extends UIController {
    
    private final JLabel roomNameLabel;
    private final UITimerController whiteTimer;
    private final UITimerController blackTimer;
    private final JButton drawButton;
    private final JButton exitButton;
    private final JTextArea historyTextArea;
    
    private IResult.Responser drawButtonResponser;
    private IResult.Responser exitButtonResponser;
    
    public UIMatchInfoController(UIView uiView) {
        super(uiView);
        
        roomNameLabel = (JLabel)super.findViewById("RoomNameLabel");
        whiteTimer = new UITimerController((UITimerView)super.findViewById("WhiteTimer"));
        blackTimer = new UITimerController((UITimerView)super.findViewById("BlackTimer"));
        drawButton = (JButton)super.findViewById("DrawButton");
        exitButton = (JButton)super.findViewById("ExitButton");
        historyTextArea = (JTextArea)super.findViewById("HistoryTextArea");
        
        whiteTimer.setTime(2, 0, 0);
        blackTimer.setTime(2, 0, 0);
        
        addEventListeners();
    }
    
    public void setRoomName(String roomName) {
        roomNameLabel.setText(String.format("PHÒNG %s", roomName));
    }
    
    public void addHistory(String content) {
        historyTextArea.append(String.format("%s\n", content));
    }
    
    private void addEventListeners() {
        drawButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (drawButtonResponser != null) {
                    drawButtonResponser.response();
                }
            } 
        });
        
        exitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (exitButtonResponser != null) {
                    exitButtonResponser.response();
                }
            } 
        });
    }
    
    public void setDrawButtonListener(IResult.Responser listener) {
        drawButtonResponser = listener;
    }
    
    public void setExitButtonListener(IResult.Responser listener) {
        exitButtonResponser = listener;
    }
    
    public void runTimer(EPieceColors color) {
        if (color == EPieceColors.WHITE) {
            whiteTimer.run();
        } else {
            blackTimer.run();
        }
    }
    
    public void pauseTimer(EPieceColors color) {
        if (color == EPieceColors.WHITE) {
            whiteTimer.pause();
        } else {
            blackTimer.pause();
        }
    }
    
    public void stopTimer(EPieceColors color) {
        if (color == EPieceColors.WHITE) {
            whiteTimer.stop();
        } else {
            blackTimer.stop();
        }
    }
    
    public void setTimeUpListener(IResult.Responser listener) {
        whiteTimer.setTimeUpListener(listener);
        blackTimer.setTimeUpListener(listener);
    }
    
    public String getTimeAsString(EPieceColors color) {
        if (color == EPieceColors.WHITE) {
            return whiteTimer.getTimeAsString();
        }
        
        return blackTimer.getTimeAsString();
    }
    
    public Time getTime(EPieceColors color) {
        if (color == EPieceColors.WHITE) {
            return whiteTimer.getTime();
        }
        
        return blackTimer.getTime();
    }
    
    public void setTime(EPieceColors color, int hour, int min, int sec) {
        if (color == EPieceColors.WHITE) {
            whiteTimer.setTime(hour, min, sec);
        } else {
            blackTimer.setTime(hour, min, sec);
        }
    }
    
}
