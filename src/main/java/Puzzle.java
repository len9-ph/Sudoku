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
    }

    private void toTranspose(){
        int[][] temp = puzzle;
        for(int i = 0; i < BOARD_SIZE; i++)
            for(int j = 0; j < BOARD_SIZE; j++) {
                this.puzzle[j][i] = temp[i][j];
            }
    }

    private void swapRows() {
        Random r = new Random();
        int firstLine = r.nextInt(BOARD_SIZE) + 1;
        int secondLine = r.nextInt(BOARD_SIZE) + 1;
        while(firstLine == secondLine)
            secondLine = r.nextInt(BOARD_SIZE) + 1;

        //int[] tempRow = new int[BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            int temp = puzzle[firstLine][i];
            puzzle[firstLine][i] = puzzle[secondLine][i];
            puzzle[secondLine][i] = temp;
        }
    }

    private void swapColumns() {
        toTranspose();
        swapRows();
        toTranspose();
    }

    private void swapRowsArea() {
        Random r = new Random();
        int firstArea = r.nextInt((int) Math.sqrt(BOARD_SIZE)) + 1;
        int secondArea = r.nextInt((int) Math.sqrt(BOARD_SIZE)) + 1;
        while(firstArea == secondArea)
            secondArea = r.nextInt((int) Math.sqrt(BOARD_SIZE)) + 1;
        

    }

    public void print() {
        for(int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++)
                System.out.print(this.puzzle[i][j] + "  ");
            System.out.println();
        }
    }
}
