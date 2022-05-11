package Core;

import java.util.Random;
import java.lang.Math;

/**
 * This class represents puzzle that will be generated for game
 * (I implement this class using singleton pattern)
 */
public final class Puzzle {
    public static final int BOARD_SIZE = 9;

    private int[][] puzzle;
    private final boolean[][] isHidden;

    private static Puzzle INSTANCE;

    /**
     * Constructor that create game board
     */
    private Puzzle() {
        puzzle = new int[BOARD_SIZE][BOARD_SIZE];
        isHidden = new boolean[BOARD_SIZE][BOARD_SIZE];
        for(int i = 0; i < BOARD_SIZE; i++)
            for(int j = 0; j < BOARD_SIZE; j++) {
                this.puzzle[i][j] = (i * 3 + i/3 + j) % 9 + 1;
                this.isHidden[i][j] = false;
            }
        mixBoard();
        hideCells();
    }

    /**
     * @return array BOARD_SIZE x BOARD_SIZE which illustrate game board
     */
    public int[][] getPuzzle() {
        return puzzle;
    }

    /**
     * @return array BOARD_SIZE x BOARD_SIZE which illustrate cell status (hidden / not)
     */
    public boolean[][] getIsHidden() {
        return isHidden;
    }

    /**
     * @return - instance of the class
     */
    public static Puzzle getInstance() {
        if (INSTANCE == null)
            INSTANCE = new Puzzle();
        return INSTANCE;
    }

    /**
     *  This method transpose game board
     */
    private final Shuffle toTranspose = () -> {
        for(int i = 0; i < BOARD_SIZE; i++)
            for(int j = i ; j < BOARD_SIZE; j++) {
                if (i != j)  {
                    int temp = puzzle[i][j];
                    puzzle[i][j] = puzzle[j][i];
                    puzzle[j][i] = temp;
                }
            }
    };

    /**
     * This method swap two rows in one area
     */
    private final Shuffle swapRows = () -> {
        Random r = new Random();
        int area = r.nextInt((int) Math.sqrt(BOARD_SIZE));
        int firstLine = r.nextInt((int) Math.sqrt(BOARD_SIZE));
        int secondLine = r.nextInt((int) Math.sqrt(BOARD_SIZE));
        while(firstLine == secondLine)
            secondLine = r.nextInt((int) Math.sqrt(BOARD_SIZE));

        int[] temp = puzzle[firstLine + area * 3];
        puzzle[firstLine + area * 3] = puzzle[secondLine + area * 3];
        puzzle[secondLine + area * 3] = temp;
    };

    /**
     * This method swap two columns in one area
     */
    private final Shuffle swapColumns = () -> {
        toTranspose.shuffleBoard();
        swapRows.shuffleBoard();
        toTranspose.shuffleBoard();
    };

    /**
     * This method change two rows area
     */
    private final Shuffle swapRowsArea = () -> {
        Random r = new Random();
        int firstArea = r.nextInt((int) Math.sqrt(BOARD_SIZE));
        int secondArea = r.nextInt((int) Math.sqrt(BOARD_SIZE));
        while(firstArea == secondArea)
            secondArea = r.nextInt((int) Math.sqrt(BOARD_SIZE));

        int[] temp;

        for (int i = 0; i < (int) Math.sqrt(BOARD_SIZE); i++){
            temp = puzzle[firstArea * 3 + i];
            puzzle[firstArea * 3 + i] = puzzle[secondArea * 3 + i];
            puzzle[secondArea * 3 + i] = temp;
        }
    };

    /**
     * This method swap two columns area
     */
    private final Shuffle swapColumnsArea = () -> {
        toTranspose.shuffleBoard();
        swapRowsArea.shuffleBoard();
        toTranspose.shuffleBoard();
    };

    /**
     * This method mix board by using shuffle functions
     */
    public void mixBoard() {
        Random r = new Random();
        int k = r.nextInt(10) + 10;
        Shuffle[] mixFunctions = {toTranspose, swapRows, swapColumns, swapRowsArea, swapColumnsArea};
        for (int i = 0; i < k; i++)
            mixFunctions[r.nextInt(mixFunctions.length)].shuffleBoard();
    }

    public void hideCells() {
        boolean[][] floor = new boolean[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++)
            for (int j = 0; j < BOARD_SIZE; j++)
                floor[i][j] = false;

        int difficult = BOARD_SIZE * BOARD_SIZE;
        Random r = new Random();
        for (int it = 0; it < BOARD_SIZE * BOARD_SIZE; it++) {
            int i = r.nextInt(BOARD_SIZE);
            int j = r.nextInt(BOARD_SIZE);

            if (!floor[i][j]) {
                floor[i][j] = true;

                //int temp = puzzle[i][j];
                //puzzle[i][j] = 0;
                isHidden[i][j] = true;
                difficult -= 1;

                int[][] table_solution = new int[BOARD_SIZE][BOARD_SIZE];
                for (int x = 0; x < BOARD_SIZE; x++)
                    for (int y = 0; y < BOARD_SIZE; y++)
                        table_solution[x][y] = puzzle[x][y];
                if(!Solver.solveSudoku(table_solution)){
                    difficult += 1;
                    isHidden[i][j] = false;
                }
            }

        }
    }


    /**
     * Delete before publication
     */
    public void print() {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++)
                System.out.print(this.puzzle[i][j] + "  ");
            System.out.println();
        }
        System.out.println();
        for(int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++)
                System.out.print(this.isHidden[i][j] + "  ");
            System.out.println();
        }

    }

}
