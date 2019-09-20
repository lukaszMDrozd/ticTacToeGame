package ticTacToe;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;

import java.io.IOException;

class Square extends StackPane {

    StackPane makeSquare() throws IOException {

        FXMLLoader squareLoader = new FXMLLoader(getClass().getResource("Square.fxml"));
        squareLoader.setRoot(this);

        SquareController squareController = new SquareController();
        squareLoader.setController(squareController);
        squareLoader.load();
        return this;
    }
}
