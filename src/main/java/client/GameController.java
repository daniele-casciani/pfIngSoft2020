package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextFlow;

public class GameController {

    @FXML
    private TextFlow text;
    @FXML
    private GridPane grid;
    @FXML
    private TextField textinput;
    @FXML
    private Button okbutton;

    public void setText(String message) {
		text.getChildren().add(new Label(message));	
	}
    

	public String getTextInput() {
		return textinput.getText();
	}
    
    @FXML
    void dragEnd(DragEvent event) {
    }
    @FXML
    void dragStart(MouseEvent event) {
    }
    @FXML
    void passturn(ActionEvent event) {

    }
}
