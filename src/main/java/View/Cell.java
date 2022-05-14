package View;

import javax.swing.*;
import java.awt.*;

public class Cell extends JTextField {
    int row;
    int column;
    int value;
    CellStatus status;

    public static final Color BG_GIVEN = new Color(240, 240, 240); // RGB
    public static final Color FG_GIVEN = Color.BLACK;
    public static final Color FG_NOT_GIVEN = Color.GRAY;
    public static final Color BG_TO_GUESS  = Color.YELLOW;
    public static final Color BG_CORRECT_GUESS = new Color(0, 216, 0);
    public static final Color BG_WRONG_GUESS   = new Color(216, 0, 0);
    public static final Font FONT_FOR_NUMBERS = new Font("Monospaced", Font.BOLD, 24);

    public Cell(int row, int col) {
        super();
        this.row = row;
        this.column = col;
        super.setHorizontalAlignment(JTextField.CENTER);
        super.setFont(FONT_FOR_NUMBERS);
    }

    public void paint() {
        if (status == CellStatus.GIVEN) {
            super.setText(value + "");
            super.setEditable(false);
            super.setBackground(BG_GIVEN);
            super.setForeground(FG_GIVEN);
        }
        else if (status == CellStatus.TO_GUESS) {
            super.setText("");
            super.setEditable(true);
            super.setBackground(BG_TO_GUESS);
            super.setForeground(FG_NOT_GIVEN);
        }
        else if (status == CellStatus.GUESSED_RIGHT)
            super.setBackground(BG_CORRECT_GUESS);
        else if (status == CellStatus.GUESSED_WRONG)
            super.setBackground(BG_WRONG_GUESS);
    }

    public void initCell(int number, boolean isHidden) {
         this.value = number;
         this.status = isHidden ? CellStatus.TO_GUESS : CellStatus.GIVEN;
    }
}
