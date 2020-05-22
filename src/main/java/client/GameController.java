package client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
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
    	changed=false;
    	listening=false;
    	textinput.setText("");
    	text.getChildren().clear();
    	setText("in attesa del login");
    	grid.getChildren().clear();
    	for (int i = 0 ; i < 6 ; i++) {
            for (int j = 0; j < 6; j++) {
            	Pane pane = new Pane();
            	setPane(pane);
            	grid.add(pane, i, j);
            }    
        }
    }
   
    public void cleanTextInput() {
    	textinput.setText("");
    	changed=false;
    }
    public void clearCell(int x, int y) {
    	grid.getChildren().removeIf(nd ->GridPane.getRowIndex(nd)==y && GridPane.getColumnIndex(nd)==x);			
		Pane pane = new Pane();
    	setPane(pane);
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
		text.getChildren().add(0,new Label(message));	
		text.getChildren().add(0,new Text(System.lineSeparator()));
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

    void dragStart(ImageView node) {
    	if(isListening()) {
    	Dragboard db = node.startDragAndDrop(TransferMode.MOVE);
    	ClipboardContent content = new ClipboardContent();
    	content.putImage(node.getImage());
    	db.setDragView(node.getImage());
    	db.setContent(content);
    	System.out.println("(gamecont-dragstart)startcell taken");
    		startCell[0] = GridPane.getColumnIndex(node);
    		startCell[1] = GridPane.getRowIndex(node);
    		setText("cella partenza "+(startCell[0]+1)+" "+(startCell[1]+1));
    	}
    }
   
    void dragDone() {}
    
    void setPane(Pane pane) {
    	
    	pane.setOnMouseClicked(e->{
    		if(isListening()) {
        		startCell[0] = GridPane.getColumnIndex(pane);
        		startCell[1] = GridPane.getRowIndex(pane);
        		setText("selezionata cella "+(startCell[0]+1)+" "+(startCell[1]+1));
        	}
    		e.consume();
    		});
    	
    	pane.setOnDragDropped(new EventHandler<DragEvent>() {
    		public void handle(DragEvent event) {
    			boolean success=false;
    			if(isListening()) {
    				System.out.println("(gamecont-dragstart)endcell taken");
    				endCell[0] = GridPane.getColumnIndex(pane);
    				endCell[1] = GridPane.getRowIndex(pane);
    				((Pane)pane).setStyle("-fx-background-color: none");
    				success=true;
    				setText("cella arrivo "+(endCell[0]+1)+" "+(endCell[1]+1));
    			}
    			event.setDropCompleted(success);
    			event.consume();
    		}});
    	pane.setOnDragExited(new EventHandler<DragEvent>() {
    		public void handle(DragEvent event) {
    			pane.setStyle("-fx-background-color: none");
    			event.consume();
    		}});
    	pane.setOnDragEntered(new EventHandler<DragEvent>() {
    		public void handle(DragEvent event) {
    			if(isListening()) {
    	    		int x = GridPane.getColumnIndex(pane);
    	    		int y = GridPane.getRowIndex(pane);
    	    	
    	    		if( Math.abs(startCell[0]-x)<2 && Math.abs(startCell[1]-y)<2)
    	    			pane.setStyle("-fx-background-color: green");
    	    		else pane.setStyle("-fx-background-color: red");
    	    	}
    			event.consume();
    		}});
    	pane.setOnDragOver(new EventHandler<DragEvent>() {
		    public void handle(DragEvent event) {
		    	event.acceptTransferModes(TransferMode.MOVE); 
		    	event.consume();
		    }});
    	
    
    
    
    
    
    
    }
}
