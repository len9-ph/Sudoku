package Controller;

import Model.Puzzle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonController implements ActionListener {
    private Puzzle puzzle;

    /**
     * @param puzzle
     */
    public ButtonController(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    /**
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
