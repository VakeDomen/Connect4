package game;


import players.Player;

import java.util.Random;

public class Game {

    private static final int MAXROW = 6;     // 6 rows
    private static final int MAXCOL = 7;

    private int winner = 0;

    private int activePlayer = 0;
    private int turn;

    private Player player1;
    private Player player2;

    private int[][] board;

    public Game(Player player1, Player player2) {
        this.turn = 0;
        this.player1 = player1;
        this.player2 = player2;
        activePlayer = new Random().nextInt(2) + 1;
        initBoard();
    }

    private void initBoard() {
        board = new int[MAXROW][MAXCOL];
        for (int row = 0; row < MAXROW; row++){
            for (int col = 0; col < MAXCOL; col++){
                board[row][col] = 0;
            }
        }
    }

    public int getWinner() {
        return winner;
    }

    public void putDisk(int n) {
        int row;
        for (row=0; row<=MAXROW; row++) {
            if (row == MAXROW || board[row][n] == 0)
                break;
        }

        if (row == MAXROW) {
            System.out.println("Illegal move");
        } else {
            int[] column = board[row];
            column[n]=activePlayer;
            if (activePlayer==1)
                activePlayer=2;
            else
                activePlayer=1;

            turn = turn / 2;
        }
        winner = Evaluator.checkWinner(board);
        if (Evaluator.validColumns(board).size() == 0) {
            winner = -1;
        }
    }

    public void restart(Player p1, Player p2) {
        System.out.println("heeee");
        if (p1 == null || p2 == null) {
            return;
        }

        this.player1 = p1;
        this.player2 = p2;
        this.turn = 0;
        this.activePlayer = new Random().nextInt(2) + 1;
        this.initBoard();
        this.winner = 0;
        //this.play();
    }

    public void play() {
        while (true) {
            while (winner == 0) {
                if (activePlayer == 1) {
                    putDisk(player1.getMove(board));
                } else {
                    putDisk(player2.getMove(board));
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int[][] getBoard() {
        return this.board;
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    public int getTurn() {
        return turn;
    }
}
