import java.util.*;
import java.awt.Point;

public class HomeCell implements Location
{
    // instance variables - replace the example below with your own
    private ArrayList<Card> cards;
    
    public HomeCell()
    {       
        cards = new ArrayList<Card>();
    }

    public boolean isEmpty() {
        return (cards.isEmpty());
    }
    
    public void remove() {        
        cards.remove(cards.size() - 1);
    }
    
    public void place(Card c) {
        cards.add(c);
        c.setLocation(this);
        c.setMoveable(false);
    }
    
    public Card peek() {
        return cards.get(cards.size() - 1);
    }
    
    public String toString() {
        if (isEmpty()) return null;
        Iterator i = cards.iterator();
        StringBuilder s = new StringBuilder();
        while (i.hasNext()) {
            if (s.length() != 0) {
                s.append(' ');
            }
            s.append(i.next());
        }
        return s.toString();
    }
    
    public ArrayList<Point> getCards() {
        ArrayList<Point> list = new ArrayList<Point>();
        for (Card c: cards) {
            list.add(new Point(c.getSuite(),c.getRank()));
        }
        return list;
    }
    
}
