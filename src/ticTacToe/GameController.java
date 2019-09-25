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

    private List<List<Integer>> getAvailableMoves() {
        return availableMoves;
    }

    private void setAvailableMoves(List<List<Integer>> availableMoves) {
        this.availableMoves = availableMoves;
    }


    private List<Integer> clickedButton;

    private List<Integer> getClickedButton() {
        return clickedButton;
    }

    private void setClickedButton(List<Integer> clickedButton) {
        this.clickedButton = clickedButton;
    }

    private Player actualPlayer;

    private Player getActualPlayer() {
        return actualPlayer;
    }

    private void setActualPlayer(Player actualPlayer) {
        this.actualPlayer = actualPlayer;
    }

    private HumanPlayer humanPlayer = new HumanPlayer("John");

    private ComputerPlayer computerPlayer = new ComputerPlayer("My Best Computer");


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setSpanNumber(2);
        setEasyButtonEvent();
        setMediumButtonEvent();
        setHardButtonEvent();
        setResetButton();
        setAvailableMoves(makeGrid(spanNumber));
        resetButton.setDisable(true);
        setActualPlayer(humanPlayer);
        gameButtonControlEvent();
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

    private void gameButtonControlEvent() {
        gameButtonControl.setOnMouseClicked(event -> {
            Game game = new Game(humanPlayer, computerPlayer, getSpanNumber(), getAvailableMoves());

            if (getActualPlayer().equals(game.getHumanPlayer())) {
                System.out.println(gameButtonControl.textProperty());
                setClicked(true);
                setGridButtonsClickEvent(getSpanNumber());
                gameButtonControl.textProperty().setValue("CONFIRM MOVE");
                recalculateAvailableMovesList();
                setActualPlayer(game.getComputerPlayer());
            }
            else if (getActualPlayer().equals(game.getComputerPlayer())) {
                gameButtonControl.textProperty().setValue("COMPUTER ON MOVE");
                game.getComputerPlayer().makeMove();
                setActualPlayer(game.getHumanPlayer());
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
        Circle circle = (Circle) stackPane.getChildren().get(0);
        Button button = (Button) stackPane.getChildren().get(1);

            button.setOnMouseClicked(event -> {
                List<Integer> buttonCoordinates = new ArrayList<>();
                buttonCoordinates.add(GridPane.getRowIndex(button.getParent()));
                buttonCoordinates.add(GridPane.getColumnIndex(button.getParent()));

                if (event.getButton() == MouseButton.PRIMARY && isClicked()) {
                    if(circle.getStyleClass().toString().equals("defaultShape")) {
                        resetButton.setDisable(true);
                    }

                    circle.getStyleClass().remove("defaultShape");
                    circle.getStyleClass().add("humanCircleChoice");

                    setClicked(false);
                    setClickedButton(buttonCoordinates);

//                    buttonCoordinates.clear();
                    System.out.println(getClickedButton());
                }
                else if (event.getButton() == MouseButton.SECONDARY && !isClicked() && getAvailableMoves().contains(buttonCoordinates)) {
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

    private void recalculateAvailableMovesList() {
        System.out.println(getClickedButton());
        getAvailableMoves().remove(getClickedButton());
        System.out.println("Dostępne ruchy: " + availableMoves);
    }
}
