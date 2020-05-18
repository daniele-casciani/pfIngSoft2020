package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class GameController {
	
	int x1; int y1; int x2; int y2;
	
    @FXML
    private TextFlow text;
    @FXML
    private GridPane grid;
    @FXML
    private TextField textinput;
    @FXML
    private Button okbutton;
    
    public void initialize(){
    	textinput.clear();
    	text.getChildren().clear();
    	text.getChildren().add(new Label("in attesa del login"));
    	text.getChildren().add(new Text(System.lineSeparator()));
    	grid.getChildren().clear();
    }

    public void setText(String message) {
		text.getChildren().add(new Label(message));	
		text.getChildren().add(new Text(System.lineSeparator()));
	}
    

	public String getTextInput() {
		return textinput.getText();
	}
	
	public void addElement(Node node,int x, int y) {
		grid.add(node, x, y);
	}
	
	public void clearCell(int x, int y) {
		for(Node nd : grid.getChildren()) {
			if (GridPane.getRowIndex(nd)==y && GridPane.getColumnIndex(nd)==x) {
				grid.getChildren().remove(nd);
			}
		}
	}
    
    @FXML
    void dragEnd(DragEvent event) {
    	//TODO
    }
    @FXML
    void dragStart(MouseEvent event) {
    	//TODO
    }
    @FXML
    void passturn(ActionEvent event) {	
    	//TODO
    }
    @FXML
    void click(MouseEvent event) {
    	//TODO
    	x1 = (int)event.getX();
    }
    
}
