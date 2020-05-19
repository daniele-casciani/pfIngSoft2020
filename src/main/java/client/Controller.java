package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
	private Stage Pstage;
	private Scene Pscene;
	private FXMLLoader ploader;
	private StackPane game = null;
	private Stage Sstage;
	
	private Listener listener;
	private Thread listT;
	private Socket socket;
	private ObjectOutputStream output;
	
	public static void main( String[] args ) {
		Application.launch(Controller.class);
	 }
	
	@Override
	public void start(Stage stage) {

		try {
			socket= new Socket("127.0.0.1", 51344);
			output =new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				System.out.println("start server unreachble ");
				e.printStackTrace();
				System.out.println("end server unreachble ");
				return;
			}
		listener = new Listener(socket,this);
		listT= new Thread (listener);
		listT.start();
		
		Pstage= stage;
		Thread.currentThread().setName("PrimaryStage");
		try {
			ploader = new FXMLLoader();
			ploader.setLocation(getClass().getResource("/fxml/game.fxml"));
			game = (StackPane)ploader.load();
			Pscene = new Scene(game,1280,720);
		} catch (IOException e1) {
			System.out.println("start impossibile caricare game.fxml");
			e1.printStackTrace();
			System.out.println("end impossibile caricare game.fxml");
		}	
		Pstage.setResizable(false);
		Pstage.setScene(Pscene);
		Pstage.show();
		System.out.println("Primary stage show");
			
		Sstage = new Stage();
		Sstage.initOwner(Pstage);
		Sstage.initModality(Modality.WINDOW_MODAL);
		Sstage.setResizable(false);
		System.out.println("Secondary stage created");
	}

	public void notify(InvalidAction message) {
		setText(message.getError());
	}
	private void setText(String message) {
		((GameController) ploader.getController()).setText(message);
	}

	public void notify(NewBuilderUpdate update) {
		addConstructor(update.getPosition()[0],update.getPosition()[1]);	
	}
	private void addConstructor(int x, int y) {
		GameController cont = ploader.getController();
		ImageView node = new ImageView("/image/builder.png");
		node.setFitHeight(80);
		node.setFitWidth(80);
		cont.addElement(node, x, y);
	}

	public void notify(SwitchPositionUpdate update) {
		construction(update.getPositions()[0], update.getPositions()[1], update.getPositions()[2]);
		construction(update.getPositions()[3], update.getPositions()[4], update.getPositions()[5]);
		addConstructor(0, 1);
		addConstructor(3, 4);
		
		}
	public void notify(MoveUpdate update) {
		construction(update.getMovement()[0], update.getMovement()[1], update.getMovement()[2]);
		construction(update.getMovement()[3], update.getMovement()[4], update.getMovement()[5]);
		addConstructor(3, 4);
	}
	public void notify(BuildUpdate update) {
		construction(update.getPosition()[0],update.getPosition()[1],update.getPosition()[2]);	
	}
	private void construction(int x, int y, int z) {
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
	
	public void notify(Loser loser) {
		System.out.println("start lose");
		
		AnchorPane an = new AnchorPane();
		Label label = new Label();
		label.setText("you win");
		label.setAlignment(Pos.CENTER);
		label.setFont(new Font(40));
		Sstage.setScene(new Scene(an));
		Sstage.showAndWait();
		Thread.currentThread().interrupt();
		
		System.out.println("end lose");
		return;
	}

	public void notify(Winner winner) {
		System.out.println("start win");
		
		AnchorPane an = new AnchorPane();
		Label label = new Label();
		label.setText("you lose");
		label.setAlignment(Pos.CENTER);
		label.setFont(new Font(40));
		Sstage.setScene(new Scene(an));
		Sstage.showAndWait();
		Thread.currentThread().interrupt();
		
		System.out.println("end win");
		return;
	}

	public void execute(UserNameRequest request) {
		System.out.println("start login");
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/log-in.fxml"));
			AnchorPane an;
			an = (AnchorPane) loader.load();
			Scene scene = new Scene(an,470,470);
			Sstage.setScene(scene);
			Sstage.showAndWait();
			
			String user = ((LoginController) loader.getController()).getusername();
			if(user.isEmpty()) {
				user=("player" + (int)(Math.random()*1000));
			}
			sendMessage(new UserNameResponse(user));
			System.out.println("username sent " + user);
			setText("logged as "+ user );
			
		} catch (IOException e) {
			System.out.println("start : impossibile aprire finestra login");
			e.printStackTrace();
			System.out.println("end : impossibile aprire finestra login");
		}
	}

	public void execute(PlayerNumberRequest request) {
		System.out.println("starting player selection");
		
		new Thread(()->{
			Platform.runLater(()->{
				setText("digita numero giocatori");
			});
		GameController gc = ((GameController) ploader.getController());
		int num = 0;
		while (!(num>1 && num<4)) {
			try {	
					String str = gc.getTextInput();
					if(!str.isEmpty()) {
					num= Integer.parseInt(str);
					}
			}catch(NumberFormatException e) {
				
				Platform.runLater(()->{
				gc.setText("numero non valido");
				System.out.println("numero non valido ");
				});
				num=0;
			}
		}
		sendMessage(new PlayerNumberResponse(num));
		System.out.println("number of player " + num);
		Platform.runLater(()->{
			setText("impostato numero giocatori");
			setText("in attesa di altri giocatori ");
			gc.cleanTextInput();});
		}).start();
	}

	public void execute(BuildRequest message) {
		setText("scegli dove costruire");
		catchDrag();
	}
	public void execute(MoveRequest request) {
		setText("scegli dove muoverti");
		catchDrag();
	}
	public void catchDrag() {
		// TODO Auto-generated method stub
		
	}

	public void execute(BuilderRequest message) {
		setText("posiziona il costruttore");
		catchPosition();
	}
	public void catchPosition() {
		// TODO Auto-generated method stub
		
	}

	public void execute(SelectCardRequest request) {
		setText("scegli le carte da usare");
		ArrayList<Integer> array;
		try {
			array = catchSelection(request.getCardlist(),request.getNumber());
		} catch (IOException e) {
			System.out.println("start impossibile caricare select image");
			e.printStackTrace();
			System.out.println("end impossibile caricare select image");
			return;
		}
		sendMessage(new SelectCardResponse(array));
	}
	public void execute(ChoseCardRequest request) {
		setText("scegli la tua carta");
		int cardID;
		try {
			 cardID = catchSelection(request.getCardlist(),1).get(0);
		} catch (IOException e) {
			System.out.println("start impossibile caricare select image");
			e.printStackTrace();
			System.out.println("end impossibile caricare select image");
			return;
		}
		sendMessage(new ChoseCardResponse(cardID));
		setText("hai scelto la carta "+cardID);
	}
	public ArrayList<Integer> catchSelection(ArrayList<Integer> cardlist, int i) throws IOException {
		System.out.println("starting divinity selection");
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/select card.fxml"));
		AnchorPane an = (AnchorPane)loader.load();
		Scene scene = new Scene(an,480,640);
		Sstage.setScene(scene);
		ArrayList<Integer> array = null;
		while (array.size()!=i) {
			Sstage.showAndWait();
			array = ((SelectController)loader.getController()).getSelection();
		}
		System.out.println("returned selected divinity");
		return array;

	}

	public void execute(BooleanRequest request){
		setText(request.getStr());		
		System.out.println("starting bool choice");
		try {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fxml/scelta.fxml"));
		AnchorPane an;
		an = (AnchorPane)loader.load();
		Scene scene = new Scene(an,220,100);
		((BooleanController) loader.getController()).setText(request.getStr());
		Sstage.setScene(scene);
		Sstage.showAndWait();
		boolean choice = ((BooleanController) loader.getController()).getChoice();
		sendMessage(new BooleanResponse(choice));
		System.out.println("selected "+choice);
	} catch (IOException e) {
		System.out.println("start impossibile richiedere scelta");
		e.printStackTrace();
		System.out.println("end impossibile richiedere scelta");
	}
	}
	
	public void handle(Message message) {
		if (message instanceof MessageToClient) {
			((MessageToClient)message).accept(this);
		}
		else if (message instanceof MessageSystem) {
			((MessageSystem)message).accept(this);
		}
	}
	
	public void sendMessage(Message message) {
		try {
			output.writeObject(message);
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public class Listener implements Runnable{
		ObjectInputStream input;
		Message message;
	
		Listener(Socket socket,Controller controller){
			try {
				input=new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				System.out.println("start error socket input creation ");
				e.printStackTrace();
				System.out.println("end error socket input creation ");
			}
		}
		@Override
		public void  run() {
			Thread.currentThread().setName("listener");
			try {
			while(true) {
				try {
					message = (Message) input.readObject();
					Platform.runLater(()->{
					handle(message);
					});
				} catch (ClassNotFoundException e) {
					System.out.println(" start message cast error ");
					e.printStackTrace();
					System.out.println(" end message cast error ");
				}
			}
			}catch(IOException e) {
				System.out.println("start socket error ");
				e.printStackTrace();
				System.out.println("end socket error ");
			} finally {
				try {
					socket.close();
					System.out.println("socket closed");
				} catch (IOException e) {
					System.out.println("socket close error ");
				}
			}
			return;
		}
	}
}