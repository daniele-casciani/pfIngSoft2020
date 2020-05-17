package client;

import java.io.IOException;
import java.util.ArrayList;
import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
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
		GameController cont = ploader.getController();
		ImageView node = new ImageView("/image/builder.png");
		node.setFitHeight(80);
		node.setFitWidth(80);
		cont.addElement(node, x, y);
	}

	@Override
	public void construction(int x, int y, int z) {
		GameController cont = ploader.getController();
		cont.clearCell(x, y);
		if (z>0|z<5) {
			Rectangle lv1 = new Rectangle();
			lv1.setWidth(90);
			lv1.setHeight(90);
			lv1.setArcHeight(5);
			lv1.setArcWidth(5);
			lv1.setFill(Color.rgb(160,160,160));
			cont.addElement(lv1, x, y);
			if(z>1) {
				Rectangle lv2 = new Rectangle();
				lv2.setWidth(70);
				lv2.setHeight(70);
				lv2.setArcHeight(5);
				lv2.setArcWidth(5);
				lv2.setFill(Color.rgb(210,210,210));
				cont.addElement(lv2, x, y);
				if(z>2) {
					Rectangle lv3 = new Rectangle();
					lv3.setWidth(50);
					lv3.setHeight(50);
					lv3.setArcHeight(5);
					lv3.setArcWidth(5);
					lv3.setFill(Color.rgb(240,240,240));
					cont.addElement(lv3, x, y);
					if(z==4) {
						Circle dome = new Circle(20);
						dome.setFill(Color.BLUE);
						cont.addElement(dome, x, y);
					}		
				}
			}
		}
	}

	@Override
	public void endLoser(Loser loser) {
		AnchorPane an = new AnchorPane();
		Label label = new Label();
		label.setText("you win");
		label.setAlignment(Pos.CENTER);
		label.setFont(new Font(40));
		Sstage.setScene(new Scene(an));
		Sstage.showAndWait();
		Thread.currentThread().interrupt();
		return;
	}

	@Override
	public void endWinner(Winner winner) {
		AnchorPane an = new AnchorPane();
		Label label = new Label();
		label.setText("you lose");
		label.setAlignment(Pos.CENTER);
		label.setFont(new Font(40));
		Sstage.setScene(new Scene(an));
		Sstage.showAndWait();
		Thread.currentThread().interrupt();
		return;
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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/scelta.fxml"));
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
