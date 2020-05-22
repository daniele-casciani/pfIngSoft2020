package client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
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
            	pane.setOnMouseClicked(e->{click(pane);});
            	pane.setOnDragDropped(new EventHandler<DragEvent>() {
            	    public void handle(DragEvent event) {
            	    	boolean success=false;
            	    	if(isListening()) {
            	    		endCell[0] = GridPane.getColumnIndex(pane);
            	    		endCell[1] = GridPane.getRowIndex(pane);
            	    		((Pane)pane).setStyle("-fx-background-color: none");
            	    		success=true;
            	    	}
            	    	event.setDropCompleted(success);	
            	    }});
            	pane.setOnDragExited(e->{dragExit(pane);});
            	pane.setOnDragEntered(e->{dragEntered(pane);});
            	pane.setOnDragOver(new EventHandler<DragEvent>() {
    			    public void handle(DragEvent event) {
    			    	event.acceptTransferModes(TransferMode.NONE); 
    			    	}});
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
    	pane.setOnMouseClicked(e->{click(pane);});
    	pane.setOnDragDropped(new EventHandler<DragEvent>() {
    	    public void handle(DragEvent event) {
    	    	boolean success=false;
    	    	if(isListening()) {
    	    		endCell[0] = GridPane.getColumnIndex(pane);
    	    		endCell[1] = GridPane.getRowIndex(pane);
    	    		((Pane)pane).setStyle("-fx-background-color: none");
    	    		success=true;
    	    		setText("cella arrivo "+(startCell[0]+1)+" "+(startCell[1]+1));
    	    	}
    	    	event.setDropCompleted(success);}});
    	
    	pane.setOnDragExited(e->{dragExit(pane);});
    	pane.setOnDragEntered(e->{dragEntered(pane);});
    	pane.setOnDragOver(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		    	event.acceptTransferModes(TransferMode.NONE); 
		    }});
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

    void dragStart(ImageView node) {
    	Dragboard db = node.startDragAndDrop(TransferMode.MOVE);
    	db.setDragView(node.getImage());
    	System.out.println("(gamecont-dragstart)validate catch");
    	if(isListening()) {
    		startCell[0] = GridPane.getColumnIndex(node);
    		startCell[1] = GridPane.getRowIndex(node);
    		setText("cella partenza "+(startCell[0]+1)+" "+(startCell[1]+1));
    	}
    	
    }

    void dragEntered(Node node) {
    	
    	if(isListening()) {
    		int x = GridPane.getColumnIndex(node);
    		int y = GridPane.getRowIndex(node);
    	
    		if( Math.abs(startCell[0]-x)<2 && Math.abs(startCell[1]-y)<2)
    			((Pane)node).setStyle("-fx-background-color: green");
    		else ((Pane)node).setStyle("-fx-background-color: red");
   
    	}
    }
    
    void dragExit(Node node) {
    	((Pane)node).setStyle("-fx-background-color: none");
    }
    
    void dragDone() {
    	if(isListening()) {
    		changed = true;
    	}
    }
}
