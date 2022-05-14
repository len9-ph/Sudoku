package Model;

import java.util.Observable;
import java.util.Random;
import java.lang.Math;

/**
 * This class represents puzzle that will be generated for game
 * (I implement this class using singleton pattern)
 */
public final class Puzzle {
    public static final int BOARD_SIZE = 9;

    private int[][] solution;
    private boolean[][] isHidden;
    private int [][] game; //Board for player
    //private boolean[][] check;
    private static Puzzle INSTANCE;

    /**
     * Constructor that create game board
     */
    private Puzzle() {
        newGame();
    }

    public void newGame() {
        solution = new int[BOARD_SIZE][BOARD_SIZE];
        isHidden = new boolean[BOARD_SIZE][BOARD_SIZE];
        game = new int [BOARD_SIZE][BOARD_SIZE];
        for(int i = 0; i < BOARD_SIZE; i++)
            for(int j = 0; j < BOARD_SIZE; j++) {
                this.solution[i][j] = (i * 3 + i/3 + j) % 9 + 1;
                this.isHidden[i][j] = false;
            }
        generateSolution();
        generateGame();
        for(int i = 0; i < BOARD_SIZE; i++)
            for(int j = 0; j < BOARD_SIZE; j++) {
                if (isHidden[i][j])
                    game[i][j] = 0;
                else
                    game[i][j] = solution[i][j];
            }
    }

    public void checkGame() {

    }

    /**
     * @return array BOARD_SIZE x BOARD_SIZE which illustrate game board
     */
    public int[][] getSolution() {
        return solution;
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
                    int temp = solution[i][j];
                    solution[i][j] = solution[j][i];
                    solution[j][i] = temp;
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

        int[] temp = solution[firstLine + area * 3];
        solution[firstLine + area * 3] = solution[secondLine + area * 3];
        solution[secondLine + area * 3] = temp;
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
            temp = solution[firstArea * 3 + i];
            solution[firstArea * 3 + i] = solution[secondArea * 3 + i];
            solution[secondArea * 3 + i] = temp;
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
     * Generate solution
     */
    public void generateSolution() {
        Random r = new Random();
        int k = r.nextInt(20) + 10;
        Shuffle[] mixFunctions = {toTranspose, swapRows, swapColumns, swapRowsArea, swapColumnsArea};
        for (int i = 0; i < k; i++)
            mixFunctions[r.nextInt(mixFunctions.length)].shuffleBoard();
    }

    public void generateGame() {
        boolean[][] floor = new boolean[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++)
            for (int j = 0; j < BOARD_SIZE; j++)
                floor[i][j] = false;

        Random r = new Random();
        for (int it = 0; it < BOARD_SIZE * BOARD_SIZE; it++) {
            int i = r.nextInt(BOARD_SIZE);
            int j = r.nextInt(BOARD_SIZE);

            if (!floor[i][j]) {
                floor[i][j] = true;

                isHidden[i][j] = true;

                int[][] table_solution = new int[BOARD_SIZE][BOARD_SIZE];
                for (int x = 0; x < BOARD_SIZE; x++)
                    for (int y = 0; y < BOARD_SIZE; y++)
                        table_solution[x][y] = solution[x][y];
                if (!Solver.solveSudoku(table_solution)) {
                    isHidden[i][j] = false;
                }
            }
        }
    }


    /**
     * print to console function used to debug
     * Delete before publication
     */
    public void print() {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++)
                if(isHidden[i][j])
                    System.out.print(0 + "  ");
                else
                    System.out.print(this.solution[i][j] + "  ");
            System.out.println();
        }
    }

}
