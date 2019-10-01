package ticTacToe;

import javafx.geometry.Point2D;

import java.util.List;

public interface Player {

    Point2D makeMove(List<Point2D> availableMoves);
}

