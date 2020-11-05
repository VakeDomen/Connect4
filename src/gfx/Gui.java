package gfx;
import game.Game;
import javax.swing.*;
import java.awt.*;

public class Gui  extends JPanel {

    int[][]                 theArray;
    boolean                 gameStart;
    public static final int BLANK = 0;
    public static final int RED = 1;
    public static final int YELLOW = 2;
    public static final int MAXROW = 6;     // 6 rows
    public static final int MAXCOL = 7;     // 7 columns

    public static final String SPACE = "                  "; // 18 spaces


    private Game game;
    private JFrame f;
    private SideMenu menu;

    public Gui(Game game) {
        this.game = game;
        this.menu = new SideMenu(game);
        f = new JFrame("Connect 4");
        f.setPreferredSize(new Dimension(1124,768));
        f.pack();
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.setLayout(new BorderLayout());

        f.getContentPane().add(this, BorderLayout.CENTER);
//        f.getContentPane().add(menu, BorderLayout.LINE_START);
        f.setVisible(true);


        // Build control panel.
        initialize();
        // Set to a reasonable size.
        setSize(1024, 768);
    } // Connect4


    public void initialize() {
        theArray=new int[MAXROW][MAXCOL];
        for (int row=0; row<MAXROW; row++)
            for (int col=0; col<MAXCOL; col++)
                theArray[row][col]=BLANK;
        gameStart=false;
    } // initialize


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.theArray = game.getBoard();
        paintBoard(g);
//        menu.refresh();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.repaint();
    }

    public void paintBoard(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(110, 50, 100+100*MAXCOL, 100+100*MAXROW);
        for (int row=0; row<MAXROW; row++)
            for (int col=0; col<MAXCOL; col++) {
                if (theArray[row][col] == BLANK) g.setColor(Color.WHITE);
                if (theArray[row][col] == RED) g.setColor(Color.RED);
                if (theArray[row][col] == YELLOW) g.setColor(Color.YELLOW);
                g.fillOval(1024 - 100 - (160 + 100 * col), 768 -100 - (100 + 100 * row), 100, 100);
            }
        if (game.getWinner() != 0) {
            displayWinner(g, game.getWinner());
        }
    } // paint

    public void displayWinner(Graphics g, int n) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Courier", Font.BOLD, 100));
        if (n==RED)
            g.drawString("Red wins!", 100, 400);
        else if (n==YELLOW)
            g.drawString("Yellow wins!", 100, 400);
        else
            g.drawString("Draw!", 100, 400);

    }

} // class
