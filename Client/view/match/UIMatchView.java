package view.match;

import model.define.ColorDefine;
import javax.swing.SpringLayout;
import view.UIView;

public class UIMatchView extends UIView {
    
    private final UIBoardView uiBoardView;
    private final UIMatchInfoView uiMatchInfoView;
    
    public UIMatchView() {
        super();
        super.setBackground(ColorDefine.MATCH);
        
        uiBoardView = new UIBoardView();
        uiMatchInfoView = new UIMatchInfoView();
        
        createComponents();
        setConstraints();
    }

    private void createComponents() {
        super.components.put("UIBoardView", uiBoardView);
        super.add(uiBoardView);
        
        super.components.put("UIMatchInfoView", uiMatchInfoView);
        super.add(uiMatchInfoView);
    }
    
    private void setConstraints() {
        SpringLayout layout = new SpringLayout();
        super.setLayout(layout);
        
        // uiMatchInfoView
        layout.putConstraint(
                SpringLayout.NORTH, uiMatchInfoView,
                20,
                SpringLayout.NORTH, this
        );
        layout.putConstraint(
                SpringLayout.WEST, uiMatchInfoView,
                70,
                SpringLayout.EAST, uiBoardView
        );
        layout.putConstraint(
                SpringLayout.SOUTH, uiMatchInfoView,
                -20,
                SpringLayout.SOUTH, this
        );
        layout.putConstraint(
                SpringLayout.EAST, uiMatchInfoView,
                -70,
                SpringLayout.EAST, this
        );
        
        // uiBoardView
        layout.putConstraint(
                SpringLayout.NORTH, uiBoardView,
                20,
                SpringLayout.NORTH, this
        );
        layout.putConstraint(
                SpringLayout.WEST, uiBoardView,
                50,
                SpringLayout.WEST, this
        );
        layout.putConstraint(
                SpringLayout.SOUTH, uiBoardView,
                -20,
                SpringLayout.SOUTH, this
        );
        layout.putConstraint(
                SpringLayout.WIDTH, uiBoardView,
                0,
                SpringLayout.HEIGHT, uiBoardView
        );
    }
    
}
