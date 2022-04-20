import java.util.Random;
import java.lang.Math;

/**
 * This class represents puzzle that will be generated for game
 */
public class Puzzle {
    public static final int BOARD_SIZE = 9;

    public int[][] puzzle;

    /**
     * Constructor that create simple board
     */
    public Puzzle() {
        puzzle = new int[9][9];
        for(int i = 0; i < BOARD_SIZE; i++)
            for(int j = 0; j < BOARD_SIZE; j++) {
                this.puzzle[i][j] = (i * 3 + i/3 + j) % 9 + 1;
            }
        mixBoard();
    }

    /**
     *  This method transpose game board
     */
    Shuffle toTranspose = () -> {
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
    Shuffle swapRows = () -> {
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
    Shuffle swapColumns = () -> {
        toTranspose.shuffleBoard();
        swapRows.shuffleBoard();
        toTranspose.shuffleBoard();
    };

    /**
     * This method change two rows area
     */
    Shuffle swapRowsArea = () -> {
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
    Shuffle swapColumnsArea = () -> {
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

    public void print() {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++)
                System.out.print(this.puzzle[i][j] + "  ");
            System.out.println();
        }
    }

}
