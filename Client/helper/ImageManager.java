package helper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImageManager {
    
    public final boolean DEBUG = false;
    
    private final StringBuilder pathImagesDir;
    
    public static final ImageManager Instance = new ImageManager();
    
    private ImageManager() {
        pathImagesDir = new StringBuilder(512);
        if (DEBUG) {
            pathImagesDir.append("./res/images/");
        } else {
            pathImagesDir.append("./res/images/");
        }
    }
    
    public JLabel getImageFrom(String filename, int width, int height) {
        int length = pathImagesDir.length();
        try {
            pathImagesDir.append(filename);
            BufferedImage image = ImageIO.read(new File(pathImagesDir.toString()));
            return new JLabel(new ImageIcon(image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH)));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            pathImagesDir.delete(length, pathImagesDir.length());
        }
        
        return null;
    }
    
    public ImageIcon getImageSrcFrom(String filename, int width, int height) {
        int length = pathImagesDir.length();
        try {
            pathImagesDir.append(filename);
            BufferedImage image = ImageIO.read(new File(pathImagesDir.toString()));
            return new ImageIcon(image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            pathImagesDir.delete(length, pathImagesDir.length());
        }
        
        return null;
    }
    
}
