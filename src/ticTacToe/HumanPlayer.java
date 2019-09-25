package ticTacToe;

import java.util.List;

public class HumanPlayer implements Player {

    private String humanName;
    private List<Integer> madeMove = null;

    HumanPlayer(String humanName) {
        this.humanName = humanName;
    }

    @Override
    public void makeMove() {

        System.out.println("Ruch: " + getHumanName());
    }

    private String getHumanName() {
        return humanName;
    }

    public List<Integer> getMadeMove() {
        return madeMove;
    }

    public void setMadeMove(List<Integer> availableMoves) {
        this.madeMove = availableMoves;
    }
}
