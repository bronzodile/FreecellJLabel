import java.util.ArrayList;
import java.awt.Point;
import java.awt.event.*;

public class GameModel
{
    // instance variables - replace the example below with your own
    private Deck deck;
    private FreeCell[] freeCells;
    private HomeCell[] homeCells;
    private Tableau[] tableaus;
    private ArrayList<Move> moveLog;
    private ArrayList<ActionListener> listeners;

    public GameModel()
    {

        freeCells = new FreeCell[4];
        homeCells = new HomeCell[4];        
        for (int i = 0; i < 4; i++) {
            freeCells[i] = new FreeCell();
            homeCells[i] = new HomeCell();
        }
        tableaus = new Tableau[8];
        deck = new Deck(6);
        for (int i = 0; i < 4; i++) {
            tableaus[i] = new Tableau();
            for (int j = 0; j < 7; j++) {
                tableaus[i].place(deck.getCard());
            }
        }
        for (int i = 4; i < 8; i++) {
            tableaus[i] = new Tableau();            
            for (int j = 0; j < 6; j++) {
                tableaus[i].place(deck.getCard());
            }
        }
        moveLog = new ArrayList<Move>();
        listeners = new ArrayList<ActionListener>();
    }

    public void move(int r, int s, int where) {
        int prevSize = moveLog.size();
        Card currentCard = deck.findCard(r, s);
        if (!currentCard.getMoveable()) return;
        Location loc = null;
        switch (where) {
            case 1: case 2: case 3: case 4: // Going to a Free Cell
            loc = currentCard.getLocation();
            if (loc instanceof Tableau){
                Tableau tab = (Tableau) loc;
                ArrayList<Card> movingGroup = tab.getMoveGroup(currentCard);
                if (movingGroup.size() > 1) {
                    break;
                }
            }
            if (freeCells[where - 1].isEmpty()) {
                loc.remove();
                logDaMove(loc, s, r);            
                freeCells[where - 1].place(currentCard);
            }     
            break;
            case 5: case 6: case 7: case 8: // Going to a Home Cell
            loc = currentCard.getLocation();
            if (loc instanceof Tableau){
                Tableau tab = (Tableau) loc;
                ArrayList<Card> movingGroup = tab.getMoveGroup(currentCard);
                if (movingGroup.size() > 1) {
                    break;
                }
            }

            if (homeCells[where - 5].isEmpty()) {
                if (currentCard.getRank() == 1) {
                    loc.remove();
                    logDaMove(loc, s, r);
                    homeCells[where - 5].place(currentCard);
                    currentCard.setMoveable(false);
                }
            } else if (homeCells[where - 5].peek().getRank() + 1 == currentCard.getRank()) {
                if (homeCells[where - 5].peek().getSuite() == currentCard.getSuite()) {
                    loc.remove();
                    logDaMove(loc, s, r);                    
                    homeCells[where - 5].place(currentCard);
                    currentCard.setMoveable(false);
                }
            }
            break;
            case 9: case 10: case 11: case 12: case 13: case 14: case 15: case 16: // Going to a Tableau
            loc = currentCard.getLocation();
            ArrayList<Card> movingGroup = new ArrayList<Card>();
            if (loc instanceof Tableau){
                Tableau tab = (Tableau) loc;
                movingGroup = tab.getMoveGroup(currentCard);
                int availableTempSpots = 0;
                for (FreeCell fc: freeCells){
                    if (fc.isEmpty()){
                        availableTempSpots++;
                    }
                }
                for (Tableau t: tableaus){
                    if (t.isEmpty() && (t != tableaus[where - 9])){
                        availableTempSpots++;
                    }
                }

                if (movingGroup.size() - 1 > availableTempSpots) {
                    break;
                }
            }
            if (tableaus[where - 9].isEmpty()) {
                loc.remove();
                logDaMove(loc, s, r);                
                tableaus[where - 9].place(currentCard);
                if (movingGroup.size() > 1) {
                    for (int i = 1; i < movingGroup.size();i++) {
                        loc.remove(); 
                    }
                    for (int i = 1; i < movingGroup.size();i++) {
                        tableaus[where - 9].place(movingGroup.get(i));
                    }
                }
            } else if (tableaus[where - 9].peek().getRank() - 1 == currentCard.getRank()) {
                if (isOpposite(tableaus[where - 9].peek().getSuite(), currentCard.getSuite())) {
                    loc.remove();
                    logDaMove(loc, s, r);
                    tableaus[where - 9].place(currentCard);
                    if (movingGroup.size() > 1) {
                        for (int i = 1; i < movingGroup.size();i++) {
                            loc.remove(); 
                        }
                        for (int i = 1; i < movingGroup.size();i++) {
                            tableaus[where - 9].place(movingGroup.get(i));
                        }
                    }
                }
            }
            break;
            default:
            break;
        }
        if (prevSize != moveLog.size()){
            for (ActionListener a: listeners) {
                a.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,new String("move")));
            }

        }

    }

    private boolean isOpposite(int suite1, int suite2) {
        if ((suite1 == 0) || (suite1 == 1)) {
            if ((suite2 == 2) || (suite2 == 3)) {
                return true;
            }
        } else {
            if ((suite2 == 0) || (suite2 == 1)) {
                return true;
            }
        }
        return false;
    }

    public void printMe()
    {
        for (int i = 0; i < 4; i++) {
            System.out.print(freeCells[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < 4; i++) {
            System.out.print(homeCells[i] + " ");
        }
        System.out.println();
        for (int i = 0; i < 8; i++) {
            System.out.println(tableaus[i]);
        }
    }

    public ArrayList<Point> getTableau(int tableauNumber) {
        return tableaus[tableauNumber - 1].getCards();
    }    

    public ArrayList<Boolean> getTableauMoveable(int tableauNumber) {
        return tableaus[tableauNumber -1].getMoveable();
    }

    public Point getFreeCellCard(int i){
        Card c = freeCells[i].peek();
        if (c != null) {
            return new Point(c.getSuite(),c.getRank());
        }
        return null;
    }

    public ArrayList<Point> getHomeCellCards(int i){
        return homeCells[i].getCards();
    }

    private void logDaMove(Location from, int suite, int rank) {
        if (from instanceof FreeCell){
            int i = 0;
            for (int j = 0; j < 4; j++) {
                if (freeCells[j] == from){
                    i = j + 1;
                }
            }
            moveLog.add(new Move(suite, rank, i));

        }
        if (from instanceof HomeCell){
            int i = 0;
            for (int j = 0; j < 4; j++) {
                if (homeCells[j] == from){
                    i = j + 5;
                }
            }
            moveLog.add(new Move(suite, rank, i));
        }
        if (from instanceof Tableau){
            int i = 0;
            for (int j = 0; j < 8; j++) {
                if (tableaus[j] == from){
                    i = j + 9;
                }
            }
            moveLog.add(new Move(suite, rank, i));
        }
    }

    public void unMove(){
        if (moveLog.isEmpty()) {
            return;
        }
        Move m = moveLog.remove(moveLog.size() - 1);
        Card currentCard = deck.findCard(m.getRank(), m.getSuite());
        Location loc = currentCard.getLocation();
        int from = m.getFrom();
        ArrayList<Card> movingGroup = new ArrayList<Card>();
        if (loc instanceof Tableau){
            Tableau tab = (Tableau) loc;
            movingGroup = tab.getMoveGroup(currentCard);
        }
        switch (from) {
            case 1: case 2: case 3: case 4: // Going to a Free Cell
            loc.remove();
            freeCells[from - 1].place(currentCard);
            break;
            case 5: case 6: case 7: case 8: // Going to a Home Cell
            loc.remove();
            homeCells[from - 5].place(currentCard);
            break;
            case 9: case 10: case 11: case 12: case 13: case 14: case 15: case 16: // Going to a Tableau                  
            loc.remove();
            tableaus[from - 9].place(currentCard);
            if (movingGroup.size() > 1) {
                for (int i = 1; i < movingGroup.size();i++) {
                    loc.remove(); 
                }
                for (int i = 1; i < movingGroup.size();i++) {
                    tableaus[from - 9].place(movingGroup.get(i));
                }
            }
            break;
            default:
            break;        
        }   
        for (ActionListener a: listeners) {
            a.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,new String("move")));
        }
    }

    public void addActionListener(ActionListener newListener) {
        if (listeners.contains(newListener)) {
            return;
        }
        listeners.add(newListener);
    }

}