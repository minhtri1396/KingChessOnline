package controller.ui.match;

import controller.ui.UIController;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.Timer;
import model.IResult;
import model.type.Time;
import view.UIView;

public class UITimerController extends UIController {
    
    private final Timer timer;
    private final JLabel hourLabel;
    private final  JLabel minLabel;
    private final  JLabel secLabel;
    
    private final Time time;
    
    private IResult.Responser timeUpResponser;
    
    public UITimerController(UIView uiView) {
        super(uiView);
        
        time = new Time();
        
        hourLabel = (JLabel)super.findViewById("HourLabel");
        minLabel = (JLabel)super.findViewById("MinLabel");
        secLabel = (JLabel)super.findViewById("SecLabel");
        
        timer = new Timer(1000, (ActionEvent e) -> {
            setTimer();
        });
        timer.setRepeats(true);
        
    }
    
    private void setTimer() {
        synchronized(time) {
            time.decrease();

            hourLabel.setText(String.format("%02d", time.getHour()));
            minLabel.setText(String.format("%02d", time.getMin()));
            secLabel.setText(String.format("%02d", time.getSec()));
        }

        if (time.isTimeUp()) {
            timer.stop();
            timeUpResponser.response();
        }
    }
    
    public void setTime(int hour, int min, int sec) {
        timer.stop();
        
        synchronized(time) {
            time.setHour(hour);
            time.setMin(min);
            time.setSec(sec);
        
            hourLabel.setText(String.format("%02d", hour));
            minLabel.setText(String.format("%02d", min));
            secLabel.setText(String.format("%02d", sec));
        }
    }
    
    public void run() {
        timer.start();
    }
    
    public void pause() {
        timer.stop();
    }
    
    public void stop() {
        timer.stop();
    }
    
    public void setTimeUpListener(IResult.Responser listener) {
        timeUpResponser = listener;
    }
    
    public String getTimeAsString() {
        return String.format("%s:%s:%s", hourLabel.getText(), minLabel.getText(), secLabel.getText());
    }
    
    public Time getTime() {
        return time;
    }
    
}
