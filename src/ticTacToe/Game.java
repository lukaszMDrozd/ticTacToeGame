package ticTacToe;

public class Game {

    private HumanPlayer humanPlayer;
    private ComputerPlayer computerPlayer;

    public Game(HumanPlayer humanPlayer, ComputerPlayer computerPlayer) {
        this.humanPlayer = humanPlayer;
        this.computerPlayer = computerPlayer;
    }

    Player getHumanPlayer() {
        return humanPlayer;
    }

    Player getComputerPlayer() {
        return computerPlayer;
    }
}
