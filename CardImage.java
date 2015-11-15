import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CardImage extends JLabel
{
    private int rank;
    private int suite;
    private int stack;
    private CardImage nextCard;
    private boolean isMoveable;
    private int memX;
    private int memY;

    public CardImage(int s, int r)
    {
        ImageIcon icon = getImage(r,s);
        this.setIcon(icon);
        rank = r;
        suite = s;
        stack = 0;
        nextCard = null;
        this.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
        isMoveable = false;
        memX = 0;
        memY = 0;
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
        switch(j) {
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
        return new String("images/" + i + c + ".gif");
    }

    public void setMoveable(boolean newMoveable)
    {
        isMoveable = newMoveable;
    }

    public boolean isMoveable(){
        return isMoveable;
    }

    public void setStack(int newStack) {
        stack = newStack;
    }

    private int getStack() {
        return stack;
    }

    public void setNextCard(CardImage newNextCard) {
        nextCard = newNextCard;
    }

    public CardImage getNextCard() {
        return nextCard;
    }
    public int getSuite(){
        return suite;
    }
    public int getRank() {
        return rank;
    }
}