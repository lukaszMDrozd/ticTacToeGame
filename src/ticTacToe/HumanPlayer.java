package ticTacToe;

public class HumanPlayer implements Player {

    private String humanName;

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
}
