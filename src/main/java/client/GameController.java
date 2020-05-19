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
	
	private int[] startCell = {-1,-1}; 
	private int[] endCell = {-1,-1};	
	private boolean changed;	
    @FXML
    private TextFlow text;
    @FXML
    private GridPane grid;
    @FXML
    private TextField textinput;
    @FXML
    private Button okbutton;
    
    public void initialize(){
    	textinput.setText("");
    	text.getChildren().clear();
    	text.getChildren().add(new Label("in attesa del login"));
    	text.getChildren().add(new Text(System.lineSeparator()));
    	grid.getChildren().clear();
    	changed=false;
    }
   
    public void cleanTextInput() {
    	textinput.setText("");
    }
    public void clearCell(int x, int y) {
		for(Node nd : grid.getChildren()) {
			if (GridPane.getRowIndex(nd)==y && GridPane.getColumnIndex(nd)==x) {
				grid.getChildren().remove(nd);
			}
		}
	}
    public void clearInput() {
    	startCell[0] = -1;
    	startCell[1] = -1;
    	endCell[0] = -1;
    	endCell[1] = -1;
    	changed=false;
    }
    
	public String getTextInput() {
		return textinput.getText();
	}
	public boolean isChanged() {
		return changed;
	}
	public boolean isStartValid() {
		if(startCell[0]<0 | startCell[0]>5) {return false;}
		if(startCell[1]<0 | startCell[1]>5) {return false;}
		return true;
	}
	public boolean isEndValid() {
		if(endCell[0]<0 | endCell[0]>5) {return false;}
		if(endCell[1]<0 | endCell[1]>5) {return false;}
		return true;
	}
	public int[] getStart() {
		return startCell;
	}
	public int[] getEnd() {
		return endCell;
	}
	
	public void addElement(Node node,int x, int y) {
		grid.add(node, x, y);
	}
	public void setText(String message) {
		text.getChildren().add(new Label(message));	
		text.getChildren().add(new Text(System.lineSeparator()));
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
    	changed=true;
    }
    @FXML
    void click(MouseEvent event) {
    	startCell[0] = (int)event.getX();
    	startCell[1] = (int)event.getY();
    } 
}
