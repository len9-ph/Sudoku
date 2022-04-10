import java.util.LinkedList;
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
        print();
        System.out.println();
        mixBoard();
    }

    Shuffle toTranspose = () -> {
        int[][] temp = puzzle;
        for(int i = 0; i < BOARD_SIZE; i++)
            for(int j = 0; j < BOARD_SIZE; j++) {
                this.puzzle[j][i] = temp[i][j];
            }
    };

    //Remake
    Shuffle swapRows = () -> {
        Random r = new Random();
        int firstLine = r.nextInt(BOARD_SIZE);
        int secondLine = r.nextInt(BOARD_SIZE);
        while(firstLine == secondLine)
            secondLine = r.nextInt(BOARD_SIZE);

        //int[] tempRow = new int[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            int temp = puzzle[firstLine][i];
            puzzle[firstLine][i] = puzzle[secondLine][i];
            puzzle[secondLine][i] = temp;
        }
    };

    Shuffle swapColumns = () -> {
        toTranspose.shuffleBoard();
        swapRows.shuffleBoard();
        toTranspose.shuffleBoard();
    };

    Shuffle swapRowsArea = () -> {
        Random r = new Random();
        int firstArea = r.nextInt((int) Math.sqrt(BOARD_SIZE));
        int secondArea = r.nextInt((int) Math.sqrt(BOARD_SIZE));
        while(firstArea == secondArea)
            secondArea = r.nextInt((int) Math.sqrt(BOARD_SIZE));

        for (int i = firstArea * 3, j = secondArea * 3 ; i < firstArea * 3 + 2 & j < secondArea * 3 + 2; i++, j++) {
            int[] temp = puzzle[i];
            puzzle[i] = puzzle[j];
            puzzle[j] = temp;
        }
    };

    Shuffle swapColumnsArea = () -> {
        toTranspose.shuffleBoard();
        swapRowsArea.shuffleBoard();
        toTranspose.shuffleBoard();
    };


    public void mixBoard() {
        Random r = new Random();
        int k = r.nextInt(10) + 10;
        Shuffle[] mixFunctions = {toTranspose, swapRows, swapColumns, swapRowsArea, swapColumnsArea };
        for (int i = 0; i < k; i++){
            mixFunctions[r.nextInt(mixFunctions.length)].shuffleBoard();
        }
    }

    public void print() {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++)
                System.out.print(this.puzzle[i][j] + "  ");
            System.out.println();
        }
    }
}
