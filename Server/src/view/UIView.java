package view;

import java.util.HashMap;
import javax.swing.JPanel;

public class UIView extends JPanel {
    protected final HashMap<String, Object> components;
    
    public UIView() {
        super();
        components = new HashMap<>();
    }
    
    public Object findViewById(String id) {
        return components.get(id);
    }
    
}
