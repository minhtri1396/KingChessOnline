package controller.ui;

import view.UIView;

public abstract class UIController {
    
    protected int id;
    
    protected final UIView uiView;
    
    protected UIController(UIView uiView) {
        this.uiView = uiView;
        id = 0;
    }
    
    public Object findViewById(String viewID) {
        return uiView.findViewById(viewID);
    }
    
    public UIView getContentView() {
        return this.uiView;
    }
    
    public boolean isVisible() {
        return this.uiView.isVisible();
    }
    
    public void setVisible(boolean visble) {
        this.uiView.setVisible(visble);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
