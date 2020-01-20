package players;

import game.Evaluator;

import java.util.ArrayList;

public class AlphaBetaAI implements Player{

    private int pieces;
    private int searchDepth;

    private int startingAlpha = Integer.MIN_VALUE;
    private int startingBeta = Integer.MAX_VALUE;

    public AlphaBetaAI(int pieces, int searchDepth) {
        this.pieces = pieces;
        this.searchDepth = searchDepth;
    }


    @Override
    public int getMove(int[][] board) {
        MinmaxResult move = minmaxWithAlphaBeta(board, searchDepth, startingAlpha, startingBeta, pieces);
        if (pieces == Evaluator.MINIMIZING_PLAYER)
            System.out.println("I am minimizing to: " + move.score);
        else
            System.out.println("I am maximizing to: " + move.score);
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return move.column;
    }

    private MinmaxResult minmaxWithAlphaBeta(int[][] board, int depth, int alpha, int beta, int player) {

        MinmaxResult result = new MinmaxResult(0, 0);
        ArrayList<Integer> cols = Evaluator.validColumns(board);
        System.out.println(cols);
        if (depth == 0 || Evaluator.checkWinner(board) != 0) {
            result.score = Evaluator.evaluate(board);
            result.column = -1;
            return result;
        }

        if (player == Evaluator.MINIMIZING_PLAYER) {
            result.score = Integer.MAX_VALUE;
            for (Integer col : cols) {
                MinmaxResult boardResult = minmaxWithAlphaBeta(
                        generateBoard(board, player, col),
                        depth - 1,
                        alpha,
                        beta,
                        otherPlayer(player)
                );
                System.out.println(boardResult.toString(depth, this.searchDepth));
                if (boardResult.score < result.score) {
                    result = boardResult;
                    result.column = col;
                }

                if (result.score < beta) {
                    beta = result.score;
                }

                if (alpha >= beta) {
                    break;
                }
            }
        } else {
            result.score = Integer.MIN_VALUE;
            for (Integer col : cols) {
                MinmaxResult boardResult = minmaxWithAlphaBeta(
                        generateBoard(board, player, col),
                        depth - 1,
                        alpha,
                        beta,
                        otherPlayer(player)
                );
                System.out.println(boardResult.toString(depth, this.searchDepth));

                if (boardResult.score > result.score) {
                    result = boardResult;
                    result.column = col;
                }

                if (alpha < result.score) {
                    alpha = result.score;
                }

                if (alpha >= beta) {
                    break;
                }
            }
        }
        //result.score = (int)Math.round((100.0 - depthTraversed * 3.0)/100 * result.score);
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


        public String toString(int depth, int fullDepth) {
            String tabs = "";
            for (int i = 0 ; i < fullDepth - depth ; i++) {
                tabs += "\t";
            }
            return tabs + "column: " + column + " | score: " + score;
        }
    }
}
