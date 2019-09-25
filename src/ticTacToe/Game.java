package ticTacToe;

import java.util.List;

public class Game {

    private HumanPlayer humanPlayer;
    private ComputerPlayer computerPlayer;
    private int spanNumber;
//    private Player activePlayer;
    private List<List<Integer>> availableMoves;

    public Game(HumanPlayer humanPlayer, ComputerPlayer computerPlayer, int spanNumber, List<List<Integer>> availableMoves) {
        this.humanPlayer = humanPlayer;
        this.computerPlayer = computerPlayer;
        this.spanNumber = spanNumber;
        this.availableMoves = availableMoves;
    }

    Player getHumanPlayer() {
        return humanPlayer;
    }

    Player getComputerPlayer() {
        return computerPlayer;
    }

    boolean isGameEnd() {
        return false;
    }

    public int getSpanNumber() {
        return spanNumber;
    }

//    Player getActivePlayer() {
//        return activePlayer;
//    }
//
//    void setActivePlayer(Player activePlayer) {
//        this.activePlayer = activePlayer;
//    }

    public List<List<Integer>> getAvailableMoves() {
        return availableMoves;
    }
}
