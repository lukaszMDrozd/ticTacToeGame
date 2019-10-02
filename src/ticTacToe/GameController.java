package ticTacToe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
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

    private List<Point2D> availableMoves;

    private void setAvailableMoves(List<Point2D> availableMoves) {
        this.availableMoves = availableMoves;
    }

    private Point2D clickedButton;

    private void setClickedButton(Point2D clickedButton) {
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

    private List<Point2D> humanMadeMoves;

    private List<Point2D> computerMadeMoves;

    private boolean gameStatus;

    private List<List<Point2D>> winningMoves;

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
                    Point2D coordinates = computerPlayer.makeMove(availableMoves);
                    setClickedButton(coordinates);
                    computerMadeMoves.add(coordinates);
                    StackPane stackPane = (StackPane) getNodeByRowColumnIndex(coordinates.getX(), coordinates.getY(), gridPane);
                    stackPane.getChildren().get(0).getStyleClass().add("computerCircleChoice");
                    stackPane.getChildren().get(1).setDisable(true);
                    setGameStatus(isGameOn(computerMadeMoves));
                    recalculateAvailableMovesList();
                    gameButtonControl.setDisable(false);
                    isGameEnd();
                    actualPlayer = humanPlayer;
                    if(availableMoves.size() == 0 && !gameStatus) {
                        gameDraw();
                    }
                } else {
                    gameDraw();
                }
            }
            confirmButton.setDisable(true);
        });
    }

    private void gameDraw() {
        gameStatusButton.setVisible(true);
        gameStatusButton.textProperty().setValue("DRAW");
        confirmButton.setDisable(true);
        gameButtonControl.setDisable(true);
    }

    private void isGameEnd() {
        if (gameStatus) {
            gameStatusButton.setVisible(true);
            confirmButton.setDisable(true);
            gameButtonControl.setDisable(true);

            if(actualPlayer == humanPlayer) {
                gameStatusButton.textProperty().setValue("YOU WON");
            } else if (actualPlayer == computerPlayer){
                gameStatusButton.textProperty().setValue("COMPUTER WON");
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

        private List<Point2D> makeGrid(int spanNumber) {
        List<Point2D> buttonStartingCoordinates = new ArrayList<>();

        for (int row = 0; row <= spanNumber; row++) {
            for (int column = 0; column <= spanNumber; column++) {
                try {
                    gridPane.add(new Square().makeSquare(), row, column);
                    Point2D buttonCoordinates = new Point2D(row,column);
                    buttonStartingCoordinates.add(buttonCoordinates);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for (Node node : gridPane.getChildren()) {
                if (GridPane.getRowIndex(node) != spanNumber) {
                    node.getStyleClass().add("gridRow");
                }
                if (GridPane.getColumnIndex(node) != spanNumber) {
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

    private Node getNodeByRowColumnIndex(final double row, final double column, GridPane gridPane) {
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
                Point2D buttonCoordinates = new Point2D(GridPane.getRowIndex(button.getParent()), GridPane.getColumnIndex(button.getParent()));

                if(actualPlayer.equals(game.getHumanPlayer()) && !circle.getStyleClass().toString().equals("computerCircleChoice")) {

                    if (event.getButton() == MouseButton.PRIMARY && isClicked() && availableMoves.contains(buttonCoordinates)) {
                        circle.getStyleClass().remove("defaultShape");
                        circle.getStyleClass().add("humanCircleChoice");
                        setClicked(false);
                        setClickedButton(buttonCoordinates);
                        humanMadeMoves.add(buttonCoordinates);
                        gameButtonControl.setDisable(true);
                        confirmButton.setDisable(false);
                    }
                    else if (event.getButton() == MouseButton.SECONDARY && !isClicked() && availableMoves.contains(buttonCoordinates)) {
                        if(circle.getStyleClass().toString().equals("humanCircleChoice")) {
                            resetButton.setDisable(false);
                            confirmButton.setDisable(true);
                            circle.getStyleClass().remove("humanCircleChoice");
                            circle.getStyleClass().add("defaultShape");
                            humanMadeMoves.remove(clickedButton);
                        }
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

    private boolean isGameOn(List<Point2D> madeMoves) {
        int counter = (spanNumber * 2) + 4;
        for (int i = 0; i < counter; i++) {
                if(madeMoves.containsAll(winningMoves.get(i)) && madeMoves.size() > spanNumber) {
                    return true;
            }
        }
        return false;
    }

    private List<List<Point2D>> winningMoves() {
        List<List<Point2D>> result = new ArrayList<>();

        //Creates winning lists a row by row and adding to result's list
        for (int row = 0; row <= spanNumber; row++) {
            List<Point2D> rowList = new ArrayList<>();
            for (int column = 0; column <= spanNumber; column++) {
                rowList.add(new Point2D(row, column));
            }
            result.add(rowList);
        }

        //Creates winning lists a column by column and adding to result's list
        for (int column = 0; column <= spanNumber; column++) {
            List<Point2D> columnList = new ArrayList<>();
            for (int row = 0; row <= spanNumber; row++) {
                columnList.add(new Point2D(row, column));
            }
            result.add(columnList);
        }

        //Creates diagonal winning list in direction: \ and adding to result list
        List<Point2D> leftDiagonal = new ArrayList<>();
        for(int i = 0; i <= spanNumber; i++) {
            leftDiagonal.add(new Point2D(i,i));
        }
        result.add(leftDiagonal);

        //Creates diagonal winning list in direction: / and adding to result list
        List<Point2D> rightDiagonal = new ArrayList<>();
        for(int i = 0, j = spanNumber; i <= spanNumber && j >= 0; i++, j--) {
            rightDiagonal.add(new Point2D(j,i));
        }

        result.add(rightDiagonal);

        return result;
    }
}
