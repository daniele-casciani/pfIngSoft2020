package client;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

public class SelectController {
	
	private ArrayList<Integer> card;
    @FXML
    private TilePane imagepane;
    @FXML
    private TilePane checkpane;
    
    public void setCard(ArrayList<Integer> cardlist) {
    	card=cardlist;
    	for(Integer x : card) {
    		ImageView image = new ImageView("image/"+x+".png");
    		image.setFitHeight(150);
    		image.setFitWidth(200);
    		image.setPreserveRatio(true);
    		imagepane.getChildren().add(image);
    		CheckBox c = new CheckBox();
    		c.setPadding(new Insets(4,4,4,4));
    		c.setSelected(false);
    		checkpane.getChildren().add(c);
    	}
    }
    
    public void initialize(){
    	imagepane.getChildren().clear();
    	checkpane.getChildren().clear();	
    }
    
    ArrayList<Integer> getSelection(){
    	ArrayList<Integer> retcard = new ArrayList<Integer>();
    	for(int x=0;x<=card.size()-1;x++) {
    		if (((CheckBox) checkpane.getChildren().get(x)).isSelected()) {
    			retcard.add(card.get(x));
    		}
    	}
		return retcard;
    	
    }
}