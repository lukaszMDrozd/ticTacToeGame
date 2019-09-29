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

    @FXML
    private Button gameStatusButton;

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

    private List<List<Integer>> humanMadeMoves;

    private List<List<Integer>> computerMadeMoves;

    private boolean gameStatus;

    public void setGameStatus(boolean gameStatus) {
        this.gameStatus = gameStatus;
    }

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
        humanMadeMoves = new ArrayList<>();
        computerMadeMoves = new ArrayList<>();
        this.game = new Game(humanPlayer, computerPlayer);
        gameStatus = false;
        gameStatusButton.setDisable(true);
    }

    private void gameButtonControlEvent() {
        gameButtonControl.setOnMouseClicked(event -> {
            gameButtonControl.textProperty().setValue("MAKE MOVE");

            if (getActualPlayer().equals(game.getHumanPlayer())) {
                setClicked(true);
                setGridButtonsClickEvent(getSpanNumber());
            }
        });
    }

    private void confirmMoveButtonEvent() {
        confirmButton.setOnMouseClicked(event -> {
                setGameStatus(isGameOn(humanMadeMoves));
                System.out.println("Klika człowiek " + isGameOn(humanMadeMoves));
                System.out.println(gameStatus);
                recalculateAvailableMovesList();
                List<Integer> coordinates = computerPlayer.makeMove(availableMoves, getSpanNumber());
                setClickedButton(coordinates);
                computerMadeMoves.add(coordinates);
                StackPane stackPane = (StackPane) getNodeByRowColumnIndex(coordinates.get(0), coordinates.get(1), gridPane);
                stackPane.getChildren().get(0).getStyleClass().add("computerCircleChoice");
                recalculateAvailableMovesList();
                setGameStatus(isGameOn(computerMadeMoves));
                System.out.println(gameStatus);
                coordinates.clear();

                if (gameStatus){
                gameStatusButton.setDisable(false);
                gameStatusButton.textProperty().setValue("ZWYCIĘŻYŁEŚ");
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

    private void setMediumButtonEvent() {
        mediumButton.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                setSpanNumber(3);
                gridPane.getChildren().clear();
                setAvailableMoves(makeGrid(getSpanNumber()));
            }
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
                        circle.getStyleClass().remove("defaultShape");
                        circle.getStyleClass().add("humanCircleChoice");

                        setClicked(false);
                        setClickedButton(buttonCoordinates);
                        humanMadeMoves.add(buttonCoordinates);
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
        availableMoves.remove(clickedButton);
        System.out.println("Wybrany przycik: " + clickedButton);
        System.out.println("Dostępne ruchy: " + availableMoves);
        System.out.println("Human made moves: " + humanMadeMoves);
        System.out.println("Computer made moves: " + computerMadeMoves);
        System.out.println("Wyniki " + winningMoves());
    }

    private boolean isGameOn(List<List<Integer>> madeMoves) {
        for (int i = 0; i < 6; i++) {
            if(winningMoves().get(i).containsAll(madeMoves) && madeMoves.size() >= 3) {
                return true;
            }
        }
        return false;
    }

    private List<List<List<Integer>>> winningMoves() {
        List<List<List<Integer>>> result = new ArrayList<>();

        for (int row = 0; row < 3; row++) {
            List<List<Integer>> stepResult = new ArrayList<>();
            for (int column = 0; column < 3; column++) {
                switch (row) {
                    case 0: {
                        List<Integer> resultAtom = new ArrayList<>();
                        resultAtom.add(0);
                        resultAtom.add(column);
                        stepResult.add(resultAtom);
                        break;
                    }

                    case 1: {
                        List<Integer> resultAtom = new ArrayList<>();
                        resultAtom.add(1);
                        resultAtom.add(column);
                        stepResult.add(resultAtom);
                        break;
                    }
                    case 2: {
                        List<Integer> resultAtom = new ArrayList<>();
                        resultAtom.add(2);
                        resultAtom.add(column);
                        stepResult.add(resultAtom);
                        break;
                    }
                }
            }
            result.add(stepResult);
        }

        for (int column = 0; column < 3; column++) {
            List<List<Integer>> stepResult = new ArrayList<>();
            for (int row = 0; row < 3; row++) {
                switch (column) {
                    case 0: {
                        List<Integer> resultAtom = new ArrayList<>();
                        resultAtom.add(row);
                        resultAtom.add(0);
                        stepResult.add(resultAtom);
                        break;
                    }

                    case 1: {
                        List<Integer> resultAtom = new ArrayList<>();
                        resultAtom.add(row);
                        resultAtom.add(1);
                        stepResult.add(resultAtom);
                        break;
                    }
                    case 2: {
                        List<Integer> resultAtom = new ArrayList<>();
                        resultAtom.add(row);
                        resultAtom.add(2);
                        stepResult.add(resultAtom);
                        break;
                    }
                }
            }
            result.add(stepResult);
        }

        return result;
    }
}
