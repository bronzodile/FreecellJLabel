import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CardImage extends JLabel
{
    private int rank;
    private int suite;
    private boolean isMoveable;
    private int memX;
    private int memY;

    public CardImage(int r, int s)
    {
        ImageIcon icon = getImage(r,s);
        this.setIcon(icon);
        rank = r;
        suite = s;
        this.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        isMoveable = false;
        memX = 0;
        memY = 0;
        // this.addMouseListener(this);
        // this.addMouseMotionListener(this);
    }

    private ImageIcon getImage(int r, int s)
    {
        return createImageIcon(getPath(r, s));
    }

    private static ImageIcon createImageIcon(String path)
    {
        java.net.URL imgURL = CardImage.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private static String getPath(int i, int j) {
        char c = ' ';        
        switch(i) {
            case 0:
            c = 'h';
            break;
            case 1:
            c = 'd';
            break;
            case 2:
            c = 's';
            break;
            case 3:
            c = 'c';
            break;
        }
        return new String("images/" + j + c + ".gif");
    }

    public void setMoveable(boolean newMoveable)
    {
        isMoveable = newMoveable;
    }

}