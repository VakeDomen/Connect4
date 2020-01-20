import game.Game;
import gfx.Gui;
import players.AlphaBetaAI;
import players.MiniMaxAI;

public class Main {

    public static Game game;

    public static void main(String[] args) {
            game = new Game(new MiniMaxAI(1, 4), new AlphaBetaAI(2, 4));
            Gui gui = new Gui(game);
            game.play();
    }


}
