import javax.swing.*;
import java.awt.*;

public class CardImage extends JLabel
{
    private int rank;
    private int suite;
    
    public CardImage(int r, int s, Point origin)
    {
        ImageIcon icon = getImage(r,s);
        this.setIcon(icon);
        rank = r;
        suite = s;
        this.setBounds(origin.x, origin.y, icon.getIconWidth(), icon.getIconHeight());
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
}