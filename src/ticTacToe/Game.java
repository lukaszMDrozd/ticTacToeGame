package ticTacToe;

public class Game {

    private Player humanPlayer;
    private Player computerPlayer;

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
