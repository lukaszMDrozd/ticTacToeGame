package ticTacToe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TicTacToeController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private HBox hBoxBottom;

    @FXML
    private HBox hBoxTop;

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

    @FXML
    private GridPane gridPane;



    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for(int row = 0; row <= 2; row++) {
            for (int column = 0; column <= 2; column++) {
                try {
                    gridPane.add(new Square().makeSquare(), row, column, 1,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        for(int row = 0; row <= 2; row++) {
            for (int column = 0; column <= 2; column++) {
                setButtonClickEvent((StackPane) getNodeByRowColumnIndex(row, column, gridPane));
                System.out.print(row + " ");
                System.out.println(column);
            }
        }
    }

    private void setButtonClickEvent(final StackPane stackPane) {

        Circle circle = (Circle) stackPane.getChildren().get(0);
        Button button = (Button) stackPane.getChildren().get(1);

        button.setOnMouseClicked(t -> {
            if (t.getButton() == MouseButton.SECONDARY) {
                circle.getStyleClass().remove("correctShapeChoice");
                circle.getStyleClass().add("defaultShape");
            }
            if (t.getButton() == MouseButton.PRIMARY) {
                circle.getStyleClass().remove("defaultShape");
                circle.getStyleClass().add("correctShapeChoice");
            }
        });
    }

    private javafx.scene.Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        javafx.scene.Node result = null;
        ObservableList<javafx.scene.Node> children = gridPane.getChildren();

        for (javafx.scene.Node node : children) {
            if(GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }
}
