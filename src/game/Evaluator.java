package game;

import java.util.ArrayList;
import java.util.HashMap;

public class Evaluator {

    private static final int MAXROW = 6;     // 6 rows
    private static final int MAXCOL = 7;

    public static int MAXIMIZING_PLAYER = 1;
    public static int MINIMIZING_PLAYER = 2;

    private static HashMap<Integer, Integer> evals = new HashMap<Integer, Integer>();


    public static int checkWinner(int[][] board) {
        // see if there are 4 disks in a row: horizontal, vertical or diagonal
        // horizontal rows
        int winner = 0;
        for (int row=0; row<MAXROW; row++) {
            for (int col=0; col<MAXCOL-3; col++) {
                int curr = board[row][col];
                if (curr>0
                        && curr == board[row][col+1]
                        && curr == board[row][col+2]
                        && curr == board[row][col+3]) {
                    winner = board[row][col];
                }
            }
        }
        // vertical columns
        for (int col=0; col<MAXCOL; col++) {
            for (int row=0; row<MAXROW-3; row++) {
                int curr = board[row][col];
                if (curr>0
                        && curr == board[row+1][col]
                        && curr == board[row+2][col]
                        && curr == board[row+3][col])
                    winner = board[row][col];
            }
        }
        // diagonal lower left to upper right
        for (int row=0; row<MAXROW-3; row++) {
            for (int col=0; col<MAXCOL-3; col++) {
                int curr = board[row][col];
                if (curr>0
                        && curr == board[row+1][col+1]
                        && curr == board[row+2][col+2]
                        && curr == board[row+3][col+3])
                    winner = board[row][col];
            }
        }
        // diagonal upper left to lower right
        for (int row=MAXROW-1; row>=3; row--) {
            for (int col=0; col<MAXCOL-3; col++) {
                int curr = board[row][col];
                if (curr>0
                        && curr == board[row-1][col+1]
                        && curr == board[row-2][col+2]
                        && curr == board[row-3][col+3])
                    winner = board[row][col];
            }
        }

        if(validColumns(board).size() == 0) {
            //draw game
            winner = -1;
        }
        return  winner;
    } // end check4

    public static ArrayList<Integer> validColumns(int[][] board) {
        ArrayList<Integer> cols = new ArrayList();
        for (int col = 0 ; col < MAXCOL ; col++) {
            if (board[MAXROW - 1][col] == 0) {
                cols.add(col);
            }
        }
        return cols;
    }


    public static int evaluate(int[][] board) {
        if (evals.get(board.hashCode()) != null) {
            return evals.get(board.hashCode());
        }
        int score = 0;
        int winner = checkWinner(board);
        if (winner != 0) {
            // someone wins
            if (winner == MAXIMIZING_PLAYER) {
                score = 10000;
            } else if(winner == MINIMIZING_PLAYER) {
                score = -10000;
            } else {
                score = 0;
            }
        } else {
            score = boardScore(board);
        }
        evals.put(board.hashCode(), score);
        return score;
    }

    private static int boardScore(int[][] board) {
        int score = 0;
        score += evalVertical(board);
        score += evalHorizontal(board);
        score += evalDiagonalLeft(board);
        score += evalDiagonalRight(board);
        return score;
    }

    private static int scoreWindow(int maxiCount, int miniCount) {
        if (maxiCount > 0 && miniCount > 0 || maxiCount == miniCount) {
            return 0;
        }

        if (maxiCount == 4)
            return 4000;
        else if(maxiCount == 3)
            return 300;
        else if(maxiCount == 2)
            return 20;
        else if(maxiCount == 1)
            return 1;

        else if(miniCount == 4)
            return -4000;
        else if(miniCount == 3)
            return -300;
        else if(miniCount == 2)
            return -20;
        else if(miniCount == 1)
            return -1;

        System.out.println(miniCount + " " + maxiCount);
        System.out.println("didnt eval ^^");
        return 0;
    }


    private static int evalVertical(int[][] board) {
        int score = 0;
        for(int row = 3 ; row < MAXROW ; row++) {
            for (int col = 0 ; col < MAXCOL ; col++) {
                int maxiCount = countVertical(board, row, col, MAXIMIZING_PLAYER);
                int miniCount = countVertical(board, row, col, MINIMIZING_PLAYER);
                score += scoreWindow(maxiCount, miniCount);
            }
        }
        return score;
    }

    private static int countVertical(int[][] board, int row, int col, int coinType) {
        int counter = 0;
        for (int i = 0 ; i < 4 ; i++) {
            if (board[row - i][col] == coinType) {
                counter++;
            }
        }
        return counter;
    }

    private static int evalHorizontal(int[][] board) {

        int score = 0;
        for(int row = 0 ; row < MAXROW ; row++) {
            for (int col = 0 ; col < MAXCOL - 3 ; col++) {
                int maxiCount = countHorizontal(board, row, col, MAXIMIZING_PLAYER);
                int miniCount = countHorizontal(board, row, col, MINIMIZING_PLAYER);
                score += scoreWindow(maxiCount, miniCount);
            }
        }
        return score;
    }

    private static int countHorizontal(int[][] board, int row, int col, int coinType) {
        int counter = 0;
        for (int i = 0 ; i < 4 ; i++) {
            if (board[row][col + i] == coinType) {
                counter++;
            }
        }
        return counter;
    }

    private static int evalDiagonalRight(int[][] board) {
        int score = 0;
        for(int row = 3 ; row < MAXROW ; row++) {
            for (int col = 0 ; col < MAXCOL - 3 ; col++) {
                int maxiCount = countDiagonalRight(board, row, col, MAXIMIZING_PLAYER);
                int miniCount = countDiagonalRight(board, row, col, MINIMIZING_PLAYER);
                score += scoreWindow(maxiCount, miniCount);
            }
        }
        return score;
    }

    private static int countDiagonalRight(int[][] board, int row, int col, int coinType) {
        int counter = 0;
        for (int i = 0 ; i < 4 ; i++) {
            if (board[row - i][col + i] == coinType) {
                counter++;
            }
        }
        return counter;
    }

    private static int evalDiagonalLeft(int[][] board) {
        int score = 0;
        for(int row = 3 ; row < MAXROW ; row++) {
            for (int col = 3 ; col < MAXCOL ; col++) {
                int maxiCount = countDiagonalLeft(board, row, col, MAXIMIZING_PLAYER);
                int miniCount = countDiagonalLeft(board, row, col, MINIMIZING_PLAYER);
                score += scoreWindow(maxiCount, miniCount);
            }
        }
        return score;
    }

    private static int countDiagonalLeft(int[][] board, int row, int col, int coinType) {
        int counter = 0;
        for (int i = 0 ; i < 4 ; i++) {
            if (board[row - i][col - i] == coinType) {
                counter++;
            }
        }
        return counter;
    }
}
