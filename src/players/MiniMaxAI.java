package players;

import game.Evaluator;

import java.util.ArrayList;

public class MiniMaxAI implements Player {

    private int pieces;
    private int searchDepth;

    public MiniMaxAI(int pieces, int searchDepth) {
        this.pieces = pieces;
        this.searchDepth = searchDepth;
    }


    @Override
    public int getMove(int[][] board) {
        MinmaxResult move = minmax(board, searchDepth, pieces);
        if (pieces == Evaluator.MINIMIZING_PLAYER)
            System.out.println("I am minimizing to: " + move.score);
        else
            System.out.println("I am maximizing to: " + move.score);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return move.column;
    }

    private MinmaxResult minmax(int[][] board, int depth, int player) {
        MinmaxResult result = new MinmaxResult(0, 0);
        ArrayList<Integer> cols = Evaluator.validColumns(board);
        if (depth == 0 || Evaluator.checkWinner(board) != 0 || cols.size() == 0) {
            result.score = Evaluator.evaluate(board);
            result.column = -1;
            return result;
        }
        if (player == Evaluator.MINIMIZING_PLAYER) {
            result.score = Integer.MAX_VALUE;
            for (Integer col : cols) {
                MinmaxResult boardResult = minmax(
                        generateBoard(board, player, col),
                        depth - 1,
                        otherPlayer(player)
                );
                if (boardResult.score <= result.score) {
                    result = boardResult;
                    result.column = col;

                }
            }
        } else {
            result.score = Integer.MIN_VALUE;
            for (Integer col : cols) {
                MinmaxResult boardResult = minmax(
                        generateBoard(board, player, col),
                        depth - 1,
                        otherPlayer(player)
                );
                if (boardResult.score >= result.score) {
                    result = boardResult;
                    result.column = col;
                }
            }
        }
        return result;
    }

    private int[][] generateBoard(int[][] board, int player, int col) {
        int[][] newBoard = new int[board.length][board[0].length];
        for (int i = 0 ; i < newBoard.length ; i++) {
            for(int j = 0 ; j < newBoard[i].length ; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
        for (int row = 0 ; row < board[0].length ; row++) {
            if (newBoard[row][col] == 0) {
                newBoard[row][col] = player;
                break;
            }
        }
        return newBoard;
    }

    private int otherPlayer(int player) {
        if (player == 1) {
            return 2;
        } else {
            return 1;
        }
    }

    class MinmaxResult {
        int column;
        int score;

        public MinmaxResult(int column, int score) {
            this.column = column;
            this.score = score;
        }
    }
}
