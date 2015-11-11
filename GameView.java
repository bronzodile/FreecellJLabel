import javax.swing.*;
import java.awt.*;


public class GameView extends JPanel 
{
    private JLayeredPane layeredPane;
    private CardImage[][] cards;

    public GameView(GameController gc){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(300, 310));        
        cards = new CardImage[4][13];
        Point origin = new Point(15, 15);
        int offset = 15;
        for (int i = 0; i < 4; i++){
            for(int j = 0; j < 13; j++){
                cards[i][j] = new CardImage(i, j + 1, origin);
                origin.x += offset;
                origin.y += offset;
                layeredPane.add(cards[i][j],new Integer(1));
            }
        }
       this.add(layeredPane);
       
    }
}