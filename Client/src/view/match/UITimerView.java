package view.match;

import model.define.FontDefine;
import javax.swing.JLabel;
import view.UIView;

public class UITimerView extends UIView {
    
    private JLabel hourLabel;
    private JLabel minLabel;
    private JLabel secLabel;
    
    public UITimerView() {
        super();
        
        addComponents();
    }

    private void addComponents() {
        hourLabel = new JLabel();
        hourLabel.setFont(FontDefine.TIMER);
        this.components.put("HourLabel", hourLabel);
        this.add(hourLabel);
        
        this.add(new JLabel(":"));
        
        minLabel = new JLabel();
        minLabel.setFont(FontDefine.TIMER);
        this.components.put("MinLabel", minLabel);
        this.add(minLabel);
        
        this.add(new JLabel(":"));
        
        secLabel = new JLabel();
        secLabel.setFont(FontDefine.TIMER);
        this.components.put("SecLabel", secLabel);
        this.add(secLabel);
    }
    
}
