package client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BooleanController {
	
	boolean choice;
    @FXML
    private Text text;
    @FXML
    private Button butyes;
    @FXML
    private Button butno;

    @FXML
    void SetNo(ActionEvent event) {
    	choice=false;
    	((Stage) butyes.getScene().getWindow()).close();
    }

    @FXML
    void setYes(ActionEvent event) {
    	choice=true;
    	((Stage) butyes.getScene().getWindow()).close();
    }

    BooleanController(){
    	choice=false;
    }
    
    boolean getChoice(){
    	return choice;
    }
    
    void setText(String question) {
    	text.setText(question);
    }
}
