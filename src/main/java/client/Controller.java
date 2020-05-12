package client;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.Loser;
import utils.Winner;

public class Controller extends  Application implements ClientController{
	Stage Pstage;
	AnchorPane login = null;
	Scene game = null;
	
	@Override
	public void start(Stage stage) {
		Pstage= stage;
		
		try {
			game = (Scene)FXMLLoader.load(getClass().getResource("game 2.fxml"));
			login = (AnchorPane)FXMLLoader.load(getClass().getResource("log-in.fxml"));
		} catch (IOException e) {
			System.out.print("errore : impossibile caricare fxml");
			e.printStackTrace();
		}	
			Pstage.setScene(game);
			Pstage.show();
	}

	@Override
	public void setText(String message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addConstructor(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void construction(int x, int y, int z) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endLoser(Loser loser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endWinner(Winner winner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void login() {
		Scene logScene= new Scene(login);
		Stage logStage= new Stage();
		logStage.initModality(Modality.APPLICATION_MODAL);
		logStage.setScene(logScene);
		logStage.showAndWait();
		
		
	}

	@Override
	public void playerNumber() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void catchDrag() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void catchPosition() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void catchSelection(ArrayList<Integer> cardlist, int i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void boolChoice(String string) {
		// TODO Auto-generated method stub
		
	}

}
