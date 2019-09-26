package ticTacToe;

import java.util.List;

public class HumanPlayer implements Player {

    private String humanName;

    HumanPlayer(String humanName) {
        this.humanName = humanName;
    }

    @Override
    public List<Integer> makeMove(List<List<Integer>> availableMoves, int spanNumber) {

        System.out.println("Ruch: " + getHumanName());
        return null;
    }

    private String getHumanName() {
        return humanName;
    }

}
