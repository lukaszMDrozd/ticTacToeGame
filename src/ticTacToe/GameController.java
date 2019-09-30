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

    private List<List<List<Integer>>> winningMoves;

    private void setGameStatus(boolean gameStatus) {
        this.gameStatus = gameStatus;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setSpanNumber(2);
        winningMoves = winningMoves();
        setEasyButtonEvent();
        setMediumButtonEvent();
        setHardButtonEvent();
        setResetButton();
        setAvailableMoves(makeGrid(spanNumber));
        resetButton.setDisable(true);
        setActualPlayer(humanPlayer);
        gameButtonControlEvent();
        humanMadeMoves = new ArrayList<>();
        computerMadeMoves = new ArrayList<>();
        this.game = new Game(humanPlayer, computerPlayer);
        gameStatus = false;
        gameStatusButton.setVisible(false);
        gameStatusButtonControlEvent();
    }

    private void gameStatusButtonControlEvent() {
        gameStatusButton.setOnMouseClicked(event -> {
            gridPane.getChildren().clear();
            setSpanNumber(2);
            winningMoves = winningMoves();
            easyButton.setDisable(false);
            mediumButton.setDisable(false);
            hardButton.setDisable(false);
            confirmButton.setDisable(false);
            gameButtonControl.setDisable(false);
            confirmButton.setDisable(true);
            gameButtonControl.textProperty().setValue("START GAME");
            setActualPlayer(humanPlayer);
            setAvailableMoves(makeGrid(spanNumber));
            humanMadeMoves.clear();
            computerMadeMoves.clear();
            this.game = new Game(humanPlayer, computerPlayer);
            gameStatus = false;
            gameStatusButton.setVisible(false);
        });
    }

    private void gameButtonControlEvent() {
        gameButtonControl.setOnMouseClicked(event -> {
            gameButtonControl.textProperty().setValue("MAKE MOVE");
            easyButton.setDisable(true);
            mediumButton.setDisable(true);
            hardButton.setDisable(true);
            confirmMoveButtonEvent();
            confirmButton.setDisable(true);
            if (getActualPlayer().equals(game.getHumanPlayer())) {
                setClicked(true);
                setGridButtonsClickEvent(getSpanNumber());
            }
        });
    }

    private void confirmMoveButtonEvent() {
        confirmButton.setOnMouseClicked(event -> {
            setGameStatus(isGameOn(humanMadeMoves));
            isGameEnd();
            recalculateAvailableMovesList();
            if(!gameStatus) {
                actualPlayer = computerPlayer;
                if (availableMoves.size() != 0) {
                    List<Integer> coordinates = computerPlayer.makeMove(availableMoves, getSpanNumber());
                    setClickedButton(coordinates);
                    computerMadeMoves.add(coordinates);
                    StackPane stackPane = (StackPane) getNodeByRowColumnIndex(coordinates.get(0), coordinates.get(1), gridPane);
                    stackPane.getChildren().get(0).getStyleClass().add("computerCircleChoice");
                    setGameStatus(isGameOn(computerMadeMoves));
                    recalculateAvailableMovesList();
                    gameButtonControl.setDisable(false);
                    isGameEnd();
                    actualPlayer = humanPlayer;
                } else {
                    gameStatusButton.setVisible(true);
                    gameStatusButton.textProperty().setValue("REMIS");
                    confirmButton.setDisable(true);
                    gameButtonControl.setDisable(true);
                }
            }
            confirmButton.setDisable(true);
        });
    }

    private void isGameEnd() {
        if (gameStatus) {
            gameStatusButton.setVisible(true);
            confirmButton.setDisable(true);
            gameButtonControl.setDisable(true);
            if(actualPlayer == humanPlayer) {
                gameStatusButton.textProperty().setValue("ZWYCIĘŻYŁEŚ");
            } else if (actualPlayer == computerPlayer){
                gameStatusButton.textProperty().setValue("WYGRAŁ KOMUPTER");
            }
        }
    }

    private void setEasyButtonEvent() {
        easyButton.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                setSpanNumber(2);
                gridPane.getChildren().clear();
                setAvailableMoves(makeGrid(getSpanNumber()));
                winningMoves = winningMoves();
            }
        });
    }

    private void setMediumButtonEvent() {
        mediumButton.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                setSpanNumber(3);
                gridPane.getChildren().clear();
                setAvailableMoves(makeGrid(getSpanNumber()));
                winningMoves = winningMoves();
            }
        });
    }

    private void setHardButtonEvent() {
        hardButton.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                setSpanNumber(4);
                gridPane.getChildren().clear();
                setAvailableMoves(makeGrid(getSpanNumber()));
                winningMoves = winningMoves();
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
                        gameButtonControl.setDisable(true);
                        confirmButton.setDisable(false);
                    }
                    else if (event.getButton() == MouseButton.SECONDARY && !isClicked() && getAvailableMoves().contains(buttonCoordinates)) {
                        if(circle.getStyleClass().toString().equals("humanCircleChoice")) {
                            resetButton.setDisable(false);
                        }
                        circle.getStyleClass().remove("humanCircleChoice");
                        circle.getStyleClass().add("defaultShape");
                        humanMadeMoves.remove(clickedButton);
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
        System.out.println("Dostępne ruchy " + availableMoves);
        System.out.println("Ruchy człowieka " + humanMadeMoves);
        System.out.println("Ruchy komputera " + computerMadeMoves);
        System.out.println("Ruchy wygrywające " + winningMoves());
    }

    private boolean isGameOn(List<List<Integer>> madeMoves) {
        int counter = (spanNumber * 2) + 4;
        for (int i = 0; i < counter; i++) {
            if(madeMoves.containsAll(winningMoves.get(i)) && madeMoves.size() > spanNumber) {
                return true;
            }
        }
        return false;
    }

    private List<List<List<Integer>>> winningMoves() {
        List<List<List<Integer>>> result = new ArrayList<>();

        for (int row = 0; row <= spanNumber; row++) {
            List<List<Integer>> stepResult = new ArrayList<>();
            for (int column = 0; column <= spanNumber; column++) {
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
                    case 3: {
                        List<Integer> resultAtom = new ArrayList<>();
                        resultAtom.add(3);
                        resultAtom.add(column);
                        stepResult.add(resultAtom);
                        break;
                    }
                    case 4: {
                        List<Integer> resultAtom = new ArrayList<>();
                        resultAtom.add(4);
                        resultAtom.add(column);
                        stepResult.add(resultAtom);
                        break;
                    }
                }
            }
            result.add(stepResult);
        }

        for (int column = 0; column <= spanNumber; column++) {
            List<List<Integer>> stepResult = new ArrayList<>();
            for (int row = 0; row <= spanNumber; row++) {
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
                    case 3: {
                        List<Integer> resultAtom = new ArrayList<>();
                        resultAtom.add(row);
                        resultAtom.add(3);
                        stepResult.add(resultAtom);
                        break;
                    }
                    case 4: {
                        List<Integer> resultAtom = new ArrayList<>();
                        resultAtom.add(row);
                        resultAtom.add(4);
                        stepResult.add(resultAtom);
                        break;
                    }
                }
            }
            result.add(stepResult);
        }

        List<List<Integer>> stepResultX = new ArrayList<>();
        for(int i = 0; i <= spanNumber; i++) {
            List<Integer> resultAtom = new ArrayList<>();
            resultAtom.add(i);
            resultAtom.add(i);
            stepResultX.add(resultAtom);
        }
        result.add(stepResultX);

        List<List<Integer>> stepResultY = new ArrayList<>();
        for(int i = 0, j = spanNumber; i <= spanNumber && j >= 0; i++, j--) {
            List<Integer> resultAtom = new ArrayList<>();
            resultAtom.add(j);
            resultAtom.add(i);
            stepResultY.add(resultAtom);
        }
        result.add(stepResultY);

        return result;
    }
}
