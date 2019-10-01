package ticTacToe;

import javafx.geometry.Point2D;

import java.util.List;
import java.util.Random;

public class ComputerPlayer implements Player{

    private String computerName;

    ComputerPlayer(String computerName) {
        this.computerName = computerName;
    }

    @Override
    public Point2D makeMove(List<Point2D> availableMoves) {

        int randomPointNumber = new Random().nextInt(availableMoves.size());

        return availableMoves.get(randomPointNumber);

    }

    private String getComputerName() {
        return computerName;
    }

}
