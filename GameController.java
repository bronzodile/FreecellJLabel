import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.*;

public class GameController extends JFrame implements KeyListener
{
    private static GameModel gm;
    private static GameView gv;

    /**
     * Constructor for objects of class GameController
     */
    public GameController()
    {
        gm = new GameModel();        
        /*
        gm.move(7,1,1);
        gm.move(10,3,9);
        gm.move(10,0,11);
        gm.move(2,3,2);
        gm.move(1,0,8);
         */
        // gv = new GameView();
        gv = new GameView(this);
        this.addKeyListener(this);

    }

    public static void main(String[] args) {
        GameController gc = new GameController();
        gc.getContentPane().add(gv, BorderLayout.CENTER);
        gc.setTitle("Freecell");
        gc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        gc.pack();
        for (int i = 1; i < 9; i++) {
            gv.setTableau(i, gm.getTableau(i),gm.getTableauMoveable(i));
        }
        for (int i = 0; i < 4; i++) {
            gv.setFreeCell(i, gm.getFreeCellCard(i), true);
        }
        for (int i = 0; i < 4; i++) {
            gv.setHomeCell(i, gm.getHomeCellCards(i), false);
        }

        gc.setVisible(true);
    }

    public void click(int rank, int suite, int heap) {
        gm.move(rank, suite, heap);
        for (int i = 1; i < 9; i++) {
            gv.setTableau(i, gm.getTableau(i),gm.getTableauMoveable(i));
        }
        for (int i = 0; i < 4; i++) {
            gv.setFreeCell(i, gm.getFreeCellCard(i), true);
        }
        for (int i = 0; i < 4; i++) {
            gv.setHomeCell(i, gm.getHomeCellCards(i), false);
        }

        gv.repaint();
    }

    public void keyTyped(KeyEvent e) {
    }

    /** Handle the key-pressed event from the text field. */
    public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_Z) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
            gm.unMove();
            for (int i = 1; i < 9; i++) {
                gv.setTableau(i, gm.getTableau(i),gm.getTableauMoveable(i));
            }
            for (int i = 0; i < 4; i++) {
                gv.setFreeCell(i, gm.getFreeCellCard(i), true);
            }
            for (int i = 0; i < 4; i++) {
                gv.setHomeCell(i, gm.getHomeCellCards(i), false);
            }

            gv.repaint();

        }

    }

    /** Handle the key-released event from the text field. */
    public void keyReleased(KeyEvent e) {
    }    
}
