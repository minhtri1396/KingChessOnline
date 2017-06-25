package main;

import controller.ui.UIStartController;
import java.awt.Dimension;
import javax.swing.JFrame;


public class Main {
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("CHESS GAME SERVER");
        
        frame.setMinimumSize(new Dimension(800, 500));
        frame.setSize(new Dimension(800, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setContentPane(new UIStartController().getContentView());
        
        frame.setVisible(true);
    }
    
}
