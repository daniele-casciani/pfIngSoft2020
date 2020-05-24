package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController{
	private String text;
	
    @FXML
    private TextField usernametext;

    @FXML
    private Button okbutton;

    @FXML
    void saveusername(ActionEvent event) {
    	text= usernametext.getText();
    	((Stage) okbutton.getScene().getWindow()).close();
    }
    @FXML
    public void initialize(){
    	usernametext.setText("");;
    	text=usernametext.getText();
    }
    String getText(){
    	return text;
    }
    void  setText(String text){
    	usernametext.setPromptText(text);
    }
}