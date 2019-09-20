package ticTacToe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TicTacToeController implements Initializable {


    @FXML
    private GridPane gridPane;

    @FXML
    private Button startButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button easyButton;

    @FXML
    private Button mediumButton;

    @FXML
    private Button hardButton;

    private int spanNumber;

    private void setSpanNumber(int spanNumber) {
        this.spanNumber = spanNumber;
    }

    private int getSpanNumber() {
        return spanNumber;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setSpanNumber(2);
        makeGrid(getSpanNumber());
        setGridButtonsClickEvent(getSpanNumber());
        setEasyButtonEvent();
        setMediumButtonEvent();
        setHardButtonEvent();
    }

    private void setMediumButtonEvent() {
        mediumButton.setOnMouseClicked(t -> {
            if (t.getButton() == MouseButton.PRIMARY) {
                setSpanNumber(3);
                gridPane.getChildren().clear();
                makeGrid(getSpanNumber());
                setGridButtonsClickEvent(getSpanNumber());
            }
        });
    }

    private void setHardButtonEvent() {
        hardButton.setOnMouseClicked(t -> {
            if (t.getButton() == MouseButton.PRIMARY) {
                setSpanNumber(4);
                gridPane.getChildren().clear();
                makeGrid(getSpanNumber());
                setGridButtonsClickEvent(getSpanNumber());
            }
        });
    }

    private void setEasyButtonEvent() {
        easyButton.setOnMouseClicked(t -> {
            if (t.getButton() == MouseButton.PRIMARY) {
                setSpanNumber(2);
                gridPane.getChildren().clear();
                makeGrid(getSpanNumber());
                setGridButtonsClickEvent(getSpanNumber());
            }
        });
    }

    private void makeGrid(int spanNumber) {

        for (int row = 0; row <= spanNumber; row++) {
            for (int column = 0; column <= spanNumber; column++) {
                try {
                    gridPane.add(new Square().makeSquare(), row, column, 1, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for (Node node : gridPane.getChildren()) {
                if (!(GridPane.getRowIndex(node) == spanNumber)) {
                    node.getStyleClass().add("gridRow");
                }
                if (!(GridPane.getColumnIndex(node) == spanNumber)) {
                    node.getStyleClass().add("gridColumn");
                }
            }
        }
    }

    private void setGridButtonsClickEvent(int SpanNumber) {
        for (int row = 0; row <= SpanNumber; row++) {
            for (int column = 0; column <= SpanNumber; column++) {
                setButtonClickEvent((StackPane) getNodeByRowColumnIndex(row, column, gridPane));
            }
        }
    }

    private Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    private void setButtonClickEvent(StackPane stackPane) {
        Circle circle = (Circle) stackPane.getChildren().get(0);
        Button button = (Button) stackPane.getChildren().get(1);

        button.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                circle.getStyleClass().remove("humanCircleChoice");
                circle.getStyleClass().add("defaultShape");
            }
            if (event.getButton() == MouseButton.PRIMARY) {
                circle.getStyleClass().remove("defaultShape");
                circle.getStyleClass().add("humanCircleChoice");
            }
        });
    }
}
