import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;

public class GameView extends JPanel implements MouseListener, MouseMotionListener
{
    private JLayeredPane layeredPane;
    private CardImage[][] cards;
    private CardImage dragLabel;
    private ArrayList<CardImage> dragGroup;
    private int dragLabelWidthDiv2;
    private int dragLabelHeightDiv3;
    private int dragFromX;
    private int dragFromY;
    private int dragFromLayer;
    private JLabel[] dropPanels;

    private static final int WINDOWH = 600;
    private static final int WINDOWW = 1000;
    private static final int TABLEAUTOP = 100;
    private static final int MARGIN = 15;
    private static final int CARDOFFSET = 30;
    private static final int TABLEAUSTEP = 100;
    private static final int FIRSTHOVERINGLEVEL = 20;

    // public GameView(GameController gc){
    public GameView(){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(WINDOWW, WINDOWH));        
        cards = new CardImage[4][13];
        dragLabel = null;
        dragGroup = new ArrayList<CardImage>();
        dragLabelWidthDiv2 = 0;
        dragLabelHeightDiv3 = 0;
        dragFromX = 0;
        dragFromY = 0;
        dragFromLayer = 0;
        dropPanels = new JLabel[8];
        for (int i = 0; i < 8; i++) {
            dropPanels[i] = new JLabel();
            dropPanels[i].setVerticalAlignment(JLabel.TOP);
            dropPanels[i].setHorizontalAlignment(JLabel.CENTER);
            dropPanels[i].setOpaque(true);
            dropPanels[i].setBackground(Color.gray);
            dropPanels[i].setForeground(Color.black);
            dropPanels[i].setBorder(BorderFactory.createLineBorder(Color.black));
            dropPanels[i].setBounds((i + 1) * TABLEAUSTEP + MARGIN,TABLEAUTOP + MARGIN  , 90, WINDOWH - TABLEAUTOP);            
            layeredPane.add(dropPanels[i],new Integer(0));
        }

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

    public void setTableau(int i, ArrayList<Point> tableau, ArrayList<Boolean> tableauMoveable) {
        int cardCounter = 1;
        Boolean b = null;
        CardImage prevCard = null;
        for (Point p: tableau) {
            b = tableauMoveable.get(tableau.indexOf(p));
            layeredPane.setLayer(cards[p.x][p.y - 1],cardCounter);
            if (prevCard != null) {
                prevCard.setNextCard(cards[p.x][p.y - 1]);
            }
            cards[p.x][p.y - 1].setLocation(i * TABLEAUSTEP + MARGIN,TABLEAUTOP + MARGIN + CARDOFFSET * cardCounter);
            cards[p.x][p.y - 1].setMoveable(b.booleanValue());
            cards[p.x][p.y - 1].setStack(i + 9);
            prevCard = cards[p.x][p.y - 1];
            cardCounter += 1;
        }
    }

    public void mousePressed(MouseEvent e) {        
        Component comp = e.getComponent();
        if (comp instanceof JLabel) {
            dragLabel = (CardImage) comp;
            if (dragLabel.isMoveable()) {
                dragLabelWidthDiv2 = dragLabel.getWidth() / 2;
                dragLabelHeightDiv3 = dragLabel.getHeight() / 3;
                dragFromX = dragLabel.getLocation().x;
                dragFromY = dragLabel.getLocation().y ;
                dragFromLayer = layeredPane.getLayer(dragLabel);

                int x = dragFromX + e.getPoint().x - dragLabelWidthDiv2;
                int y = dragFromY + e.getPoint().y - dragLabelHeightDiv3;
                dragLabel.setLocation(x, y);
                layeredPane.setLayer(dragLabel,FIRSTHOVERINGLEVEL);

                CardImage tempLabel = dragLabel.getNextCard();
                int draggedCardsCount = 0;
                while (tempLabel != null) {
                    draggedCardsCount += 1;
                    y = y + CARDOFFSET;
                    tempLabel.setLocation(x, y);
                    layeredPane.setLayer(tempLabel,FIRSTHOVERINGLEVEL + draggedCardsCount);
                    tempLabel = tempLabel.getNextCard();
                }
            } else {
                dragLabel = null;
            }            
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (dragLabel != null)
        {
            Point p = SwingUtilities.convertPoint(dragLabel, e.getPoint(), layeredPane);
            int x = p.x - dragLabelWidthDiv2;
            int y = p.y - dragLabelHeightDiv3;
            dragLabel.setLocation(x, y);
            CardImage tempLabel = dragLabel.getNextCard();
            while (tempLabel != null) {
                y = y + CARDOFFSET;
                tempLabel.setLocation(x, y);
                tempLabel = tempLabel.getNextCard();
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (dragLabel != null)
        {
            dragLabel.setLocation(dragFromX, dragFromY);
            layeredPane.setLayer(dragLabel,dragFromLayer);
            CardImage tempLabel = dragLabel.getNextCard();
            while (tempLabel != null) {
                dragFromLayer += 1;
                dragFromY = dragFromY + CARDOFFSET;
                tempLabel.setLocation(dragFromX, dragFromY);
                layeredPane.setLayer(tempLabel,dragFromLayer);                
                tempLabel = tempLabel.getNextCard();
            }

            Point p = SwingUtilities.convertPoint(dragLabel, e.getPoint(), dropPanels[0]);
            
            dragLabel = null;
            dragLabelWidthDiv2 = 0;
            dragLabelHeightDiv3 = 0;
            dragFromX = 0;
            dragFromY = 0;            
            dragFromLayer = 0;
            
            
        }
    }  

    public void mouseExited  (MouseEvent e) {}  // ignore these events
    public void mouseMoved   (MouseEvent e) {}  // ignore these events
    public void mouseEntered (MouseEvent e) {}  // ignore these events
    public void mouseClicked (MouseEvent e) {}  // ignore these events
}