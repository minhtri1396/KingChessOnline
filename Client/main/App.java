package main;

import model.define.SizeDefine;
import model.define.StringDefine;
import java.awt.CardLayout;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JFrame;
import model.type.Player;
import model.type.Room;
import model.type.RoomList;
import view.UIView;

public class App extends JFrame {
    public static final App INSTANCE = new App();
    
    private RoomList rooms; // the rooms list which will be synchronized with server
    private Player player; // the player who logged in
    private Room room; // the room of which player is admin
    
    public void start() {
        
    }
    
    private Queue<String> screenNamesQueue;
    
    private static final App THIS = new App();
    
    private CardLayout card = new CardLayout();
    
    private HashMap<String, UIView> namesMap;
            
    private String showingViewName;
    
    public static App getInstance() {
        return THIS;
    }
    
    private App() {
        super.setMinimumSize(SizeDefine.MINIMUM_SIZE_FRAME);
        super.setSize(SizeDefine.MINIMUM_SIZE_FRAME);
        super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        super.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                UIView showingView = namesMap.get(showingViewName);
                namesMap.remove(showingViewName);
                showingViewName = screenNamesQueue.poll();
                if (showingViewName == null) {
                    System.exit(0);
                } else {
                    card.removeLayoutComponent(showingView);
                    getContentPane().remove(showingView);
                    setTitle(String.format("%s (%s)", StringDefine.PROGRAM_NAME, showingViewName));
                    card.show(getContentPane(), showingViewName);
                    setExtendedState(JFrame.MAXIMIZED_BOTH); 
                    setVisible(true);
                    revalidate();
                    repaint();
                }
            }
        });
        
        card = new CardLayout();
        super.getContentPane().setLayout(card);
        
        namesMap = new HashMap<>();
        screenNamesQueue = new LinkedList<>();
        showingViewName = null;
    }
    
    public void show(UIView uiView, String viewName) {
        if (showingViewName != null) {
            screenNamesQueue.offer(showingViewName);
        } else {
            this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
            this.setVisible(true);
        }
        getContentPane().add(uiView, viewName);
        card.show(getContentPane(), viewName);
        
        showingViewName = viewName;
        namesMap.put(viewName, uiView);
        
        super.setTitle(String.format("%s (%s)", StringDefine.PROGRAM_NAME, showingViewName));
    }
    
}
