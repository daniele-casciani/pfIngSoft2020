package client;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.*;

public class Controller extends  Application implements ClientController{
	ClientLauncher client;
	Stage Pstage;
	Stage Sstage;
	Scene game = null;
	
	@Override
	public void start(Stage stage) {
		Pstage= stage;
		
		try {
			game = (Scene)FXMLLoader.load(getClass().getResource("/fxml/game.fxml"));
		} catch (IOException e) {
			System.out.println("start impossibile caricare game.fxml");
			e.printStackTrace();
			System.out.println("end impossibile caricare game.fxml");
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
	public void login() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fxml/log-in.fxml"));
		AnchorPane an = (AnchorPane) loader.load();
		Scene scene = new Scene(an,480,640);
		Sstage.initModality(Modality.APPLICATION_MODAL);
		Sstage.setResizable(false);
		Sstage.setScene(scene);
		Sstage.showAndWait();
		String user = ((LoginController) loader.getController()).getusername();
		if(user.isEmpty()) {
			user="player " + Math.random()%1000;
		}
		client.sendMessage(new UserNameResponse(user));
		System.out.print("inviato username" + user);
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
	public void boolChoice(String string) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("scelta.fxml"));
		AnchorPane an = loader.load();
		Scene scene = new Scene(an,220,100);
		Sstage.initModality(Modality.APPLICATION_MODAL);
		Sstage.setResizable(false);
		((BooleanController) loader.getController()).setText(string);
		Sstage.setScene(scene);
		Sstage.showAndWait();
		boolean choice = ((BooleanController) loader.getController()).getChoice();
		client.sendMessage(new EffectResponse(choice));
	}

}
