package ticTacToe;

public class ComputerPlayer implements Player{

    private String computerName;

    ComputerPlayer(String computerName) {
        this.computerName = computerName;
    }

    @Override
    public void makeMove() {
        System.out.println("Ruch komputera: " + getComputerName());
    }

    private String getComputerName() {
        return computerName;
    }
}
