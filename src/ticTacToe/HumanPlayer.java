package ticTacToe;

import javafx.geometry.Point2D;

import java.util.List;

public class HumanPlayer implements Player {

    private String humanName;

    HumanPlayer(String humanName) {
        this.humanName = humanName;
    }

    @Override
    public Point2D makeMove(List<Point2D> availableMoves) {

        System.out.println("Ruch: " + getHumanName());
        return null;
    }

    private String getHumanName() {
        return humanName;
    }

}
