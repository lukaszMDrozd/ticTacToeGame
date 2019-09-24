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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private GridPane gridPane;

    @FXML
    private Button gameButtonControl;

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

    private boolean clicked;

    private boolean isClicked() {
        return clicked;
    }

    private void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    private List<List<Integer>> availableMoves;

    public List<List<Integer>> getAvailableMoves() {
        return availableMoves;
    }

    public void setAvailableMoves(List<List<Integer>> availableMoves) {
        this.availableMoves = availableMoves;
    }

    private List<Integer> clickedButton;

    public List<Integer> getClickedButton() {
        return clickedButton;
    }

    public void setClickedButton(List<Integer> clickedButton) {
        this.clickedButton = clickedButton;
    }

    private Game game =  new Game(new HumanPlayer("John"), new ComputerPlayer("My Best Computer"), getSpanNumber(), getAvailableMoves());


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setSpanNumber(2);
        setEasyButtonEvent();
        setMediumButtonEvent();
        setHardButtonEvent();
        setResetButton();
        setAvailableMoves(makeGrid(getSpanNumber()));
        resetButton.setDisable(true);
        game.setActivePlayer(game.getHumanPlayer());
        gridButtonEvent();
    }

    private void setMediumButtonEvent() {
        mediumButton.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                setSpanNumber(3);
                gridPane.getChildren().clear();
                makeGrid(getSpanNumber());
            }
        });
    }

    private void gridButtonEvent() {
        gameButtonControl.setOnMouseClicked(event -> {

            if (game.getActivePlayer().equals(game.getHumanPlayer())) {
                System.out.println(gameButtonControl.textProperty());
                setClicked(true);
                setGridButtonsClickEvent(getSpanNumber());
                game.getHumanPlayer().makeMove();
                gameButtonControl.textProperty().setValue("MAKE MOVE");
                game.setActivePlayer(game.getComputerPlayer());
                System.out.println(getClickedButton());
            }
            else if (game.getActivePlayer().equals(game.getComputerPlayer())) {
                game.getComputerPlayer().makeMove();
                gameButtonControl.textProperty().setValue("WAIT");
                game.setActivePlayer(game.getHumanPlayer());
            }
        });
    }

    private void setHardButtonEvent() {
        hardButton.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                setSpanNumber(4);
                gridPane.getChildren().clear();
                makeGrid(getSpanNumber());
            }
        });
    }

    private void setEasyButtonEvent() {
        easyButton.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                setSpanNumber(2);
                gridPane.getChildren().clear();
                makeGrid(getSpanNumber());
            }
        });
    }

    private List<List<Integer>> makeGrid(int spanNumber) {
        List<List<Integer>> startingButtonsCoordinates = new ArrayList<>();

        for (int row = 0; row <= spanNumber; row++) {
            for (int column = 0; column <= spanNumber; column++) {
                try {
                    gridPane.add(new Square().makeSquare(), row, column);
                    List<Integer> buttonCoordinates = new ArrayList<>();
                    buttonCoordinates.add(row);
                    buttonCoordinates.add(column);
                    startingButtonsCoordinates.add(buttonCoordinates);
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
        System.out.println(startingButtonsCoordinates);
        return startingButtonsCoordinates;
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
        List<Integer> buttonCoordinates = new ArrayList<>();
        Circle circle = (Circle) stackPane.getChildren().get(0);
        Button button = (Button) stackPane.getChildren().get(1);

            button.setOnMouseClicked(event -> {
                if (event.getButton() == MouseButton.PRIMARY && isClicked()) {
                    if(circle.getStyleClass().toString().equals("defaultShape")) {
                        resetButton.setDisable(true);
                    }
                    circle.getStyleClass().remove("defaultShape");
                    circle.getStyleClass().add("humanCircleChoice");

                    buttonCoordinates.add(GridPane.getRowIndex(button.getParent()));
                    buttonCoordinates.add(GridPane.getColumnIndex(button.getParent()));
                    setClickedButton(buttonCoordinates);

                    System.out.println(buttonCoordinates);

                    setClicked(false);
                }
                else if (event.getButton() == MouseButton.SECONDARY && !isClicked()) {
                    if(circle.getStyleClass().toString().equals("humanCircleChoice")) {
                        resetButton.setDisable(false);
                    }
                    circle.getStyleClass().remove("humanCircleChoice");
                    circle.getStyleClass().add("defaultShape");
                    System.out.println(isClicked());
                }
            });
    }

    private void setResetButton() {
        resetButton.setOnMouseClicked(event -> {
            setClicked(true);
            resetButton.setDisable(true);
        });
    }
}
