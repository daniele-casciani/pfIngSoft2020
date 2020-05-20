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
	private GameController gameCont ;
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
			gameCont = ((GameController) ploader.getController());
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
		gameCont.setText(message);
	}

	public void notify(NewBuilderUpdate update) {
		addConstructor(update.getPosition()[0],update.getPosition()[1]);	
	}
	private void addConstructor(int x, int y) {
		ImageView node = new ImageView("/image/builder.png");
		node.setFitHeight(80);
		node.setFitWidth(80);
		gameCont.addElement(node, x, y);
		gameCont.setText("aggiunto costruttore");
	}

	public void notify(SwitchPositionUpdate update) {
		construction(update.getPositions()[0], update.getPositions()[1], update.getPositions()[2]);
		construction(update.getPositions()[3], update.getPositions()[4], update.getPositions()[5]);
		addConstructor(0, 1);
		addConstructor(3, 4);
		gameCont.setText("scambio posizioni eseguito");
		}
	public void notify(MoveUpdate update) {
		construction(update.getMovement()[0], update.getMovement()[1], update.getMovement()[2]);
		construction(update.getMovement()[3], update.getMovement()[4], update.getMovement()[5]);
		addConstructor(3, 4);
		gameCont.setText("spostamento effettuato");
	}
	public void notify(BuildUpdate update) {
		construction(update.getPosition()[0],update.getPosition()[1],update.getPosition()[2]);	
		gameCont.setText("costruzione eseguita");
	}
	private void construction(int x, int y, int z) {
		gameCont.clearCell(x, y);
		if (z>0|z<5) {
			Rectangle lv1 = new Rectangle();
			lv1.setWidth(90);
			lv1.setHeight(90);
			lv1.setArcHeight(5);
			lv1.setArcWidth(5);
			lv1.setFill(Color.rgb(160,160,160));
			lv1.setMouseTransparent(true);
			gameCont.addElement(lv1, x, y);
			if(z>1) {
				Rectangle lv2 = new Rectangle();
				lv2.setWidth(70);
				lv2.setHeight(70);
				lv2.setArcHeight(5);
				lv2.setArcWidth(5);
				lv2.setFill(Color.rgb(210,210,210));
				lv2.setMouseTransparent(true);
				gameCont.addElement(lv2, x, y);
				if(z>2) {
					Rectangle lv3 = new Rectangle();
					lv3.setWidth(50);
					lv3.setHeight(50);
					lv3.setArcHeight(5);
					lv3.setArcWidth(5);
					lv3.setFill(Color.rgb(240,240,240));
					lv3.setMouseTransparent(true);
					gameCont.addElement(lv3, x, y);
					if(z==4) {
						Circle dome = new Circle(20);
						dome.setFill(Color.BLUE);
						dome.setMouseTransparent(true);
						gameCont.addElement(dome, x, y);
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
				gameCont.cleanTextInput();
				gameCont.setListening(true);
			});
		int num = 0;
		while (!(num>1 && num<4 )) {
			Platform.runLater(()->{gameCont.setText("scegli tra 2 e 3");});
			String str="";
			while (!gameCont.isChanged()) {	
				str= gameCont.getTextInput();
			}
			try {
				if(!str.isEmpty()) {
					num= Integer.parseInt(str);
				}
			}catch(NumberFormatException e) {				
				Platform.runLater(()->{
				gameCont.setText("non è un numero");
				gameCont.setText("reinserire il numero");
				System.out.println("player number not valid");
				});
				num=0;
			}
			gameCont.cleanTextInput();
		}
		sendMessage(new PlayerNumberResponse(num));
		System.out.println("number of player " + num);
		Platform.runLater(()->{
			gameCont.setListening(false);
			setText("impostato numero giocatori");
			setText("in attesa di altri giocatori ");
			gameCont.cleanTextInput();});
		}).start();
	}

	public void execute(BuildRequest request) {
		setText("scegli dove costruire");
		catchDrag(request);
	}
	public void execute(MoveRequest request) {
		setText("scegli dove muoverti");
		catchDrag(request);
	}
	public void catchDrag(MessageToClient message) {
		new Thread(()->{
			gameCont.clearInput();
			gameCont.setListening(true);
			int[] start = null;
			int[] end = null;
			while(!(gameCont.isStartValid() && gameCont.isEndValid())) {
				gameCont.clearInput();
				gameCont.setText("trascina casella");
				while(!gameCont.isChanged()) {
					start = gameCont.getStart();
					end = gameCont.getEnd();
				}
				gameCont.setText("valutazione mossa");
			}
			if (message instanceof MoveRequest) {
				sendMessage(new MoveResponse(start, end));
				System.out.println("inviata move "+ start+" " + end);
			}
			else if (message instanceof BuildRequest) {
				sendMessage(new BuildResponse(start, end));
				System.out.println("inviata build "+ start+" " + end);
			}
			else {
				sendMessage(new InvalidAction("messaggio non riconosciuto"));
				System.out.println("messagio non riconosciuto");
			}
			gameCont.setListening(false);
			gameCont.clearInput();
		}).start();
	}
	
	public void execute(BuilderRequest message) {
		setText("scegli dove posizionare il costruttore");
		catchPosition();
		
	}
	public void catchPosition() {
		new Thread(()->{
			gameCont.clearInput();
			gameCont.setListening(true);
			System.out.println("listenig for position");
			int[] position = null;
			while(!gameCont.isStartValid()) {
				gameCont.clearInput();
				Platform.runLater(()->{gameCont.setText("inserisci posizione valida");});
				while(!gameCont.isChanged()) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				position = gameCont.getStart();
			}
			sendMessage(new BuilderResponse(position));
			System.out.println("new builderResponse at "+position[0]+" "+position[1]);
			gameCont.clearInput();
			Platform.runLater(()->{
				setText("posizionamento costruttore");
				gameCont.setListening(false);
				gameCont.clearInput();				
			});
		}).start();
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
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/fxml/select card.fxml"));
		AnchorPane an = (AnchorPane)loader.load();
		((SelectController)loader.getController()).setCard(cardlist);
		Scene scene = new Scene(an,640,480);
		Sstage.setScene(scene);
		ArrayList<Integer> array = null;
		 do{
			Sstage.showAndWait();
			array = ((SelectController)loader.getController()).getSelection();
		}while (array.size()!=i);
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
			Platform.runLater(()->{
			return;
			});
		}
	}
}