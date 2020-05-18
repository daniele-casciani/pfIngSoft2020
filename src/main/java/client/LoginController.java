package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController{
	private String username;
	
    @FXML
    private TextField usernametext;

    @FXML
    private Button okbutton;

    @FXML
    void saveusername(ActionEvent event) {
    	username= usernametext.getText();
    }
    @FXML
    public void initialize(){
    	usernametext.setText("");;
    	username=usernametext.getText();
    }
    String getusername(){
    	return username;
    }
    
}