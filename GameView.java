import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;

public class GameView extends JPanel implements MouseListener, MouseMotionListener
{
    private JLayeredPane layeredPane;
    private CardImage[][] cards;
    private CardImage dragLabel;
    int dragLabelWidthDiv2;
    int dragLabelHeightDiv2;
    int dragFromX;
    int dragFromY;

    private static final int TABLEAUTOP = 100;
    private static final int MARGIN = 15;
    private static final int CARDOFFSET = 25;
    private static final int TABLEAUSTEP = 100;

    public GameView(GameController gc){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(300, 310));        
        cards = new CardImage[4][13];
        dragLabel = null;
        int dragLabelWidthDiv2 = 0;
        int dragLabelHeightDiv2 = 0;
        int dragFromX = 0;
        int dragFromY = 0;

        for (int i = 0; i < 4; i++){
            for(int j = 0; j < 13; j++){
                cards[i][j] = new CardImage(i, j + 1);
                layeredPane.add(cards[i][j],new Integer(1));
                cards[i][j].addMouseListener(this);
                cards[i][j].addMouseMotionListener(this);
            }
        }
        this.add(layeredPane);

    }

    public void setTableau(int i, ArrayList<Point> tableau) {
        int cardCounter = 0;
        for (Point p: tableau) {
            layeredPane.setLayer(cards[p.x][p.y - 1],cardCounter);
            cards[p.x][p.y - 1].setLocation(i * TABLEAUSTEP + MARGIN,TABLEAUTOP + MARGIN + CARDOFFSET * cardCounter);
            cards[p.x][p.y - 1].setMoveable(true);
            cardCounter += 1;
        }
    }

    public void mousePressed(MouseEvent e) {        
        Component comp = e.getComponent();
        if (comp instanceof JLabel) {
            dragLabel = (CardImage) comp;
            dragLabelWidthDiv2 = dragLabel.getWidth() / 2;
            dragLabelHeightDiv2 = dragLabel.getHeight() / 2;
            dragFromX = dragLabel.getLocation().x;
            dragFromY = dragLabel.getLocation().y ;

            int x = dragFromX + e.getPoint().x - dragLabelWidthDiv2;
            int y = dragFromY + e.getPoint().y - dragLabelHeightDiv2;
            dragLabel.setLocation(x, y);
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (dragLabel != null)
        {
            Point p = SwingUtilities.convertPoint(dragLabel, e.getPoint(), layeredPane);
            int x = p.x - dragLabelWidthDiv2;
            int y = p.y - dragLabelHeightDiv2;
            dragLabel.setLocation(x, y);
            
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (dragLabel != null)
        {
            dragLabel.setLocation(dragFromX, dragFromY);
            dragLabel = null;
            dragLabelWidthDiv2 = 0;
            dragLabelHeightDiv2 = 0;
            dragFromX = 0;
            dragFromY = 0;            
        }
    }  

    public void mouseExited  (MouseEvent e) {}  // ignore these events
    public void mouseMoved   (MouseEvent e) {}  // ignore these events
    public void mouseEntered (MouseEvent e) {}  // ignore these events
    public void mouseClicked (MouseEvent e) {}  // ignore these events
}