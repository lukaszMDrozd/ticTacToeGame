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

    @FXML
    private Button confirmButton;

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

    private Game game;


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
        confirmMoveButtonEvent();
        this.game = new Game(humanPlayer, computerPlayer, getSpanNumber(), getAvailableMoves());
    }

    private void setMediumButtonEvent() {
        mediumButton.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                setSpanNumber(3);
                gridPane.getChildren().clear();
                setAvailableMoves(makeGrid(getSpanNumber()));
            }
        });
    }

    private void gameButtonControlEvent() {
        gameButtonControl.setOnMouseClicked(event -> {
            gameButtonControl.textProperty().setValue("YOUR MOVE");

            if (getActualPlayer().equals(game.getHumanPlayer())) {
                setClicked(true);
                System.out.println(gameButtonControl.textProperty());
                setGridButtonsClickEvent(getSpanNumber());
                recalculateAvailableMovesList();
            }
        });
    }

    private void confirmMoveButtonEvent() {
        confirmButton.setOnMouseClicked(event -> {
            List<Integer> coordinates = computerPlayer.makeMove(availableMoves, getSpanNumber());
            setClickedButton(coordinates);
            recalculateAvailableMovesList();
            StackPane stackPane = (StackPane) getNodeByRowColumnIndex(coordinates.get(0), coordinates.get(1), gridPane);
            stackPane.getChildren().get(0).getStyleClass().add("computerCircleChoice");
            coordinates.clear();
        });
    }

    private void setHardButtonEvent() {
        hardButton.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                setSpanNumber(4);
                gridPane.getChildren().clear();
                setAvailableMoves(makeGrid(getSpanNumber()));
                System.out.println("Range in hard Button" + getSpanNumber());
            }
        });
    }

    private void setEasyButtonEvent() {
        easyButton.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                setSpanNumber(2);
                gridPane.getChildren().clear();
                setAvailableMoves(makeGrid(getSpanNumber()));
            }
        });
    }

    private List<List<Integer>> makeGrid(int spanNumber) {
        List<List<Integer>> buttonStartingCoordinates = new ArrayList<>();

        for (int row = 0; row <= spanNumber; row++) {
            for (int column = 0; column <= spanNumber; column++) {
                try {
                    gridPane.add(new Square().makeSquare(), row, column);
                    List<Integer> buttonCoordinates = new ArrayList<>();
                    buttonCoordinates.add(row);
                    buttonCoordinates.add(column);
                    buttonStartingCoordinates.add(buttonCoordinates);
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
        return buttonStartingCoordinates;
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

                if(actualPlayer.equals(game.getHumanPlayer()) && !circle.getStyleClass().toString().equals("computerCircleChoice")) {
                    if (event.getButton() == MouseButton.PRIMARY && isClicked()) {
//                        if(circle.getStyleClass().toString().equals("defaultShape")) {
//                            resetButton.setDisable(true);
//                        }

                        circle.getStyleClass().remove("defaultShape");
                        circle.getStyleClass().add("humanCircleChoice");

                        setClicked(false);
                        setClickedButton(buttonCoordinates);

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
        System.out.println("Dostępne ruchy: " + getAvailableMoves());
    }
}
