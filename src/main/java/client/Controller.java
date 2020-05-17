package client;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.*;

public class Controller extends  Application implements ClientController{
	ClientLauncher client;
	Stage Pstage;
	Scene Pscene;
	FXMLLoader ploader;
	StackPane game = null;
	Stage Sstage;
	
	
	@Override
	public void start(Stage stage) {
		Pstage= stage;
		
		try {
			ploader = new FXMLLoader();
			ploader.setLocation(getClass().getResource("/fxml/game.fxml"));
			game = (StackPane)ploader.load();
			Pscene = new Scene(game,1288,725);
		} catch (IOException e) {
			System.out.println("start impossibile caricare game.fxml");
			e.printStackTrace();
			System.out.println("end impossibile caricare game.fxml");
		}	
			Pstage.setResizable(false);
			Pstage.setScene(Pscene);
			Pstage.show();
	}

	@Override
	public void setText(String message) {
		((GameController) ploader.getController()).setText(message);
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
		GameController gc = ((GameController) ploader.getController());
		int num = 0;
		while (!(num>1|num<4)) {
			try {
			//	TODO aspetto inserimento input
			num= Integer.parseInt(gc.getTextInput());
			break;
			}catch(NumberFormatException e) {
				num=0;
				gc.setText("numero non valido");
				System.out.print("numero non valido");
			}
		}
		client.sendMessage(new PlayerNumberResponse(num));
		System.out.print("inviato username" + num);
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
