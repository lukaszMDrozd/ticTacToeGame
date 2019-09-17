package ticTacToe;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private GridPane grid;

    @FXML
    private HBox HBox00;

    @FXML
    private HBox HBox01;

    @FXML
    private HBox HBox10;

    @FXML
    private HBox HBox11;

    @FXML
    private HBox HBox02;

    @FXML
    private HBox HBox12;

    @FXML
    private HBox HBox20;

    @FXML
    private HBox HBox21;

    @FXML
    private Circle circle01;

    @FXML
    private Circle circle02;

    @FXML
    private Circle circle10;

    @FXML
    private Circle circle11;

    @FXML
    private Circle circle12;

    @FXML
    private Circle circle20;

    @FXML
    private Circle circle21;

    @FXML
    private Circle circle22;

    @FXML
    private Button button00;

    @FXML
    private Circle circle00;

    @FXML
    private Button button01;

    @FXML
    private Button button10;

    @FXML
    private Button button11;

    @FXML
    private Button button12;

    @FXML
    private Button button20;

    @FXML
    private Button button21;

    @FXML
    private Button button22;

    @FXML
    private Button button02;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setButtonClickEvent(button00, circle00);
        setButtonClickEvent(button01, circle01);
        setButtonClickEvent(button02, circle02);
        setButtonClickEvent(button10, circle10);
        setButtonClickEvent(button11, circle11);
        setButtonClickEvent(button12, circle12);
        setButtonClickEvent(button20, circle20);
        setButtonClickEvent(button21, circle21);
        setButtonClickEvent(button22, circle22);
    }

    private void setButtonClickEvent(Button button, Circle circle) {
        button.setOnMouseClicked(t -> {
            if (t.getButton() == MouseButton.SECONDARY) {
                circle.getStyleClass().removeAll("correctChoice");
                circle.getStyleClass().add("defaultColor");
            }
            if (t.getButton() == MouseButton.PRIMARY) {
                circle.getStyleClass().remove("defaultColor");
                circle.getStyleClass().add("correctChoice");
            }
        });
    }
}
