package Core;

import java.lang.Math;

/**
 * This class solve given sudoku task
 */
public class Solver {
    /**
     * @param board - game board 9 x 9
     * @param row - num of row
     * @param col - num of column
     * @param num - number
     * @return - true if this combination safe, otherwise false
     */
    private static boolean isSafe(int[][] board, int row, int col, int num) {
        for (int d = 0; d < board.length; d++)
            if (board[row][d] == num)
                return false;

        for (int r = 0; r < board.length; r++)
            if (board[r][col] == num)
                return false;

        int sqrt = (int)Math.sqrt(Puzzle.BOARD_SIZE);
        int boxRowStart = row - row % sqrt;
        int boxColStart = col - col % sqrt;

        for (int r = boxRowStart; r < boxRowStart + sqrt; r++)
            for (int d = boxColStart; d < boxColStart + sqrt; d++)
                if (board[r][d] == num)
                    return false;

        return true;
    }

    /**
     * @param board - game board with zeros representing empty cells
     * @return true if game have solution, otherwise false
     */
    public static boolean solveSudoku(int[][] board){
        int row = -1;
        int col = -1;
        boolean isEmpty = true;
        for (int i = 0; i < Puzzle.BOARD_SIZE; i++) {
            for (int j = 0; j < Puzzle.BOARD_SIZE; j++)
                if (board[i][j] == 0){
                    row = i;
                    col = j;
                    isEmpty = false;
                    break;
                }
            if (!isEmpty)
                break;
        }

        if (isEmpty)
            return true;

        for (int num = 1; num <= Puzzle.BOARD_SIZE; num++) {
            if (isSafe(board, row, col, num)) {
                board[row][col] = num;
                if (solveSudoku(board))
                    return true;
                else
                    board[row][col] = 0;
            }
        }
        return false;
    }
}

