package players;

import game.Evaluator;

import java.util.ArrayList;
import java.util.Random;

public class RandomAI implements Player {

    private int pieces;

    public RandomAI(int pieces) {
        this.pieces = pieces;
    }

    @Override
    public int getMove(int[][] board) {
        ArrayList<Integer> cols = Evaluator.validColumns(board);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return cols.get(new Random().nextInt(cols.size()));
    }

}
