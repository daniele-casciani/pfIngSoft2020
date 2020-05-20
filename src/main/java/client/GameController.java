package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class GameController {
	
	private int[] startCell = {-1,-1}; 
	private int[] endCell = {-1,-1};	
	private boolean changed;
	private boolean listening;
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
    	for (int i = 0 ; i < 6 ; i++) {
            for (int j = 0; j < 6; j++) {
            	Pane pane = new Pane();
            	pane.setOnMouseClicked(e->{
            		click(pane);
            	});
            	grid.add(pane, i, j);
            }    
        }
    	changed=false;
    	listening=false;
    }
   
    public void cleanTextInput() {
    	textinput.setText("");
    	changed=false;
    }
    public void clearCell(int x, int y) {
		for(Node nd : grid.getChildren()) {
			if (GridPane.getRowIndex(nd)==y && GridPane.getColumnIndex(nd)==x) {
				grid.getChildren().remove(nd);
			}
		}
		Pane pane = new Pane();
    	pane.setOnMouseClicked(e->{
    		click(pane);
    	});
    	grid.add(pane, x, y);
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
	public boolean isListening() {
		return listening;
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
	public void setListening(boolean listening) {
		this.listening = listening;
	}
	
    @FXML
    void validateAction(ActionEvent event) {	
    	if(isListening()) {
    		changed=true;
    		System.out.println("validating action");
    	}
    	else {setText("non e il tuo turno");};
    }

    void click(Pane pane) {
    	if(isListening()) {
    		startCell[0] = GridPane.getColumnIndex(pane);
    		startCell[1] = GridPane.getRowIndex(pane);
    	setText("selezionata cella "+(startCell[0]+1)+" "+(startCell[1]+1));
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
}
