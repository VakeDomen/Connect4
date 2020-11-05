import game.Game;
import gfx.Gui;
import players.AlphaBetaAI;
import players.HumanPlayer;
import players.MiniMaxAI;
import players.Player;

public class Main {

    public static Game game;

    public static void main(String[] args) {
        Player p1 = new HumanPlayer();
        Player p2 = new MiniMaxAI(2, 6);
        if (args.length == 1) {
            if (args[0].equals("human")) {
                p2 = new HumanPlayer();
            } else if (args[0].equals("ab")) {
                p2 = new AlphaBetaAI(2, 6);
            } else if (args[0].equals("minmax")) {
                p2 = new MiniMaxAI(2, 6);
            } else if (args[0].equals("random")) {
                p2 = new MiniMaxAI(2, 6);
            }
        }
        if (args.length > 1) {
            p2 = p1;
            if (args[0].equals("human")) {
                p1 = new HumanPlayer();
            } else if (args[0].equals("ab")) {
                p1 = new AlphaBetaAI(2, 6);
            } else if (args[0].equals("minmax")) {
                p1 = new MiniMaxAI(2, 6);
            } else if (args[0].equals("random")) {
                p1 = new MiniMaxAI(2, 6);
            }
        }
        game = new Game(p1, p2);
        Gui gui = new Gui(game);
        game.play();
    }


}
