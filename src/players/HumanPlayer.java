package players;

import java.util.Scanner;

public class HumanPlayer implements Player {
    @Override
    public int getMove(int[][] board) {
        Scanner s = new Scanner(System.in);
        return s.nextInt();
    }
}
