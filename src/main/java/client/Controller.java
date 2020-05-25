package client;

import java.io.EOFException;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.*;

public class Controller extends  Application implements ClientController{
	
	private String ip;
	
	private Stage Pstage;
	private Scene Pscene;
	private GameController gameCont ;

	private Stage Sstage;
	private Thread stageT;
	
	private ObjectOutputStream output;
	private Socket socket;
	private Thread listT;
	
	private String playerName;
	private Controller cont=this;	
	
	@Override
	public void start(Stage stage){
		
		Thread.currentThread().setName("PrimaryStage");
		Platform.setImplicitExit(true);
		
		newPstage(stage);		
		newSstage();
		
		int tryer=1;
		while (tryer <=3) {
			System.out.println("ip request "+tryer);
			ip = setip();
			if(newConnection(ip)) {
				Pstage.show();
				break;
			}
			else tryer++;
		}
	}
	private boolean newConnection(String ip) {
		try {
			socket = new Socket(ip, 51344);
			output = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				System.out.println("server unreachable game closed");
				return false;
			}
		listT= new Thread(new Listener(socket));
		listT.setDaemon(true);
		listT.setName("listener");
		listT.start();
		return true;
	}
	private void newPstage(Stage stage) {
		Pstage= stage;
		try {
			FXMLLoader ploader = new FXMLLoader();
			ploader.setLocation(getClass().getResource("/fxml/game.fxml"));
			Pscene = new Scene((StackPane)ploader.load(),1280,720);
			gameCont = ((GameController) ploader.getController());
		} catch (IOException e1) {
			System.out.println("start impossibile caricare game.fxml");
			e1.printStackTrace();
			System.out.println("end impossibile caricare game.fxml");
		}	
		Pstage.setResizable(false);
		Pstage.setScene(Pscene);
		System.out.println("Primary stage created");
	}	
	private void newSstage() {
		Sstage = new Stage();
		Sstage.initOwner(Pstage);
		Sstage.initModality(Modality.WINDOW_MODAL);
		Sstage.setResizable(false);
		System.out.println("Secondary stage created");
	}
	private void reStart() {
		System.out.println("restarting");
		try {
			socket.close();
		} catch (IOException e) {
			System.out.println("errore chiusura socket");
		}
		gameCont.initialize();
		if(!newConnection(ip)) {
			Pstage.close();
			System.out.println("reconnection error");
		}
	}
	private String setip() {
		String user="127.0.0.1";
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/fxml/log-in.fxml"));
			AnchorPane an = (AnchorPane) loader.load();
			Scene scene = new Scene(an,470,470);
			((LoginController) loader.getController()).setText("ip address");
			Sstage.setScene(scene);
			Sstage.showAndWait();
			
			user = ((LoginController) loader.getController()).getText();
			((LoginController) loader.getController()).initialize();
			if(user.isEmpty()) {
				user="127.0.0.1";
			}
		} catch (IOException e) {
			System.out.println("start : impossibile aprire finestra login");
			e.printStackTrace();
			System.out.println("end : impossibile aprire finestra login");
		}
		return user;
	}
	
	public void notify(PlayerDisconnect playerDisconnect) {
		System.out.println("start disconnect");
		stageT = new Thread (()->{
			HBox an = new HBox();
			an.setAlignment(Pos.CENTER);
			Label label = new Label();
			label.setAlignment(Pos.CENTER);
			label.setFont(new Font(20));

			label.setText(playerDisconnect.getPlayer()+" disconnected");			
			Platform.runLater(()->{
				an.getChildren().add(label);
				Sstage.setScene(new Scene(an,300,100));
				Sstage.showAndWait();
				if(socket.isClosed()) {
					reStart();
				}
			});
		});
		stageT.setDaemon(true);
		stageT.setName("disconnected");
		stageT.start();
		}
	
	public void notify(Loser loser) {
			System.out.println("start lose");
			stageT = new Thread (()->{
				HBox an = new HBox();
				an.setAlignment(Pos.CENTER);
				Label label = new Label();
				label.setText("you lose");
				label.setAlignment(Pos.CENTER);
				label.setFont(new Font(40));				
				Platform.runLater(()->{
					an.getChildren().add(label);
					Sstage.setScene(new Scene(an,300,100));
					Sstage.showAndWait();
					try {
						socket.close();
					} catch (IOException e) {
					}
				});
			});
			stageT.setDaemon(true);
			stageT.setName("loseGame");
			stageT.start();
		}
	public void notify(Winner winner) {
			System.out.println("start win");
			stageT = new Thread (()->{
				HBox an = new HBox();
				an.setAlignment(Pos.CENTER);
				Label label = new Label();
				label.setText("you win");
				label.setAlignment(Pos.CENTER);
				label.setFont(new Font(40));				
				Platform.runLater(()->{
					an.getChildren().add(label);
					Sstage.setScene(new Scene(an,300,100));
					Sstage.showAndWait();
					try {
						socket.close();
					} catch (IOException e) {
					}
				});
			});
			stageT.setDaemon(true);
			stageT.setName("winGame");
			stageT.start();
		}	
	
	public void notify(InvalidAction message) {
		Platform.runLater(()->{
		setText(message.getError());
		});
	}
	private void setText(String message) {
		gameCont.setText(message);
	}

	public  void update(BuilderUpdate update) {
		addConstructor(update.getPosition()[0],update.getPosition()[1],update.getName());	
		sendMessage(new Ack());
		System.out.println("new constructor");
	}
	private void addConstructor(int x, int y, String name) {
		ImageView node;
		if(name.equals(playerName)) {
			node = new ImageView("/image/MyBuilder.png");
			node.setFitHeight(80);
			node.setFitWidth(80);
			node.setOnDragDetected(e->{
				gameCont.dragStart(node);
				e.consume();
			});
			node.setOnDragDone(e->{
				gameCont.dragDone();
				e.consume();
			});
		}
		else {
			node = new ImageView("/image/EnemyBuilder.png");
			node.setFitHeight(80);
			node.setFitWidth(80);
			node.setMouseTransparent(true); 
		}
		gameCont.addElement(node, x, y);
	}

	public void update(SwitchPositionUpdate update) {
		construction(update.getPositions()[0], update.getPositions()[1], update.getPositions()[2]);
		construction(update.getPositions()[3], update.getPositions()[4], update.getPositions()[5]);
		addConstructor(update.getPositions()[0],update.getPositions()[1],update.getName1());
		addConstructor(update.getPositions()[3],update.getPositions()[4],update.getName2());
		gameCont.setText("scambio posizioni eseguito");
		sendMessage(new Ack());
		System.out.println("switched");
	}
	public void update(MoveUpdate update) {
		construction(update.getMovement()[0], update.getMovement()[1], update.getMovement()[2]);
		construction(update.getMovement()[3], update.getMovement()[4], update.getMovement()[5]);
		addConstructor(update.getMovement()[3], update.getMovement()[4],update.getName());
		gameCont.setText("spostamento effettuato");
		sendMessage(new Ack());
		System.out.println("move update");
	}
	public void update(BuildUpdate update) {
		construction(update.getPosition()[0],update.getPosition()[1],update.getPosition()[2]);	
		gameCont.setText("costruzione eseguita");
		sendMessage(new Ack());
		System.out.println("build update "+update.getPosition()[0]+update.getPosition()[1]+update.getPosition()[2]);
	}
	private void construction(int x, int y, int z) {
		gameCont.clearCell(x, y);
		if (z>0 && z<5) {
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
			
			String user = ((LoginController) loader.getController()).getText();
			if(user.isEmpty()) {
				user=("player" + (int)(Math.random()*1000));
			}
			sendMessage(new UserNameResponse(user));
			System.out.println("username sent " + user);
			setText("logged as "+ user );
			playerName=user;
			
		} catch (IOException e) {
			System.out.println("start : impossibile aprire finestra login");
			e.printStackTrace();
			System.out.println("end : impossibile aprire finestra login");
		}
	}

	public void execute(PlayerNumberRequest request) {
		System.out.println("starting player selection");
		
		stageT = new Thread(()->{
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
				gameCont.cleanTextInput();
			});
		});
		stageT.setDaemon(true);
		stageT.setName("player number");
		stageT.start();
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
		stageT = new Thread(()->{
			System.out.println("(controller-drag)start catch drag");
			int[] start = null;
			int[] end = null;
			synchronized(this){
				gameCont.clearInput();
				gameCont.setListening(true);
				while(!(gameCont.isStartValid() && gameCont.isEndValid())) {
					gameCont.clearInput();
					Platform.runLater(()->{gameCont.setText("trascina casella");});
					while(!gameCont.isChanged()) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					start = gameCont.getStart();
					end = gameCont.getEnd();
					Platform.runLater(()->{gameCont.setText("valutazione mossa");});
				}		
				if (message instanceof MoveRequest) {
					sendMessage(new MoveResponse(start, end));
					System.out.println("(controller-drag)inviata move "+start[0]+start[1]+" "+end[0]+end[1]);
				}
				else if (message instanceof BuildRequest) {
					sendMessage(new BuildResponse(start, end));
					System.out.println("(controller-drag)inviata build "+start[0]+start[1]+" "+end[0]+end[1]);
				}
				else {
					sendMessage(new InvalidAction("messaggio non riconosciuto"));
					System.out.println("(controller-drag)message not supported");
				}
				gameCont.setListening(false);
				gameCont.clearInput();
			}
			Thread.currentThread().interrupt();
		});
		stageT.setDaemon(true);
		stageT.setName("catchDrag");
		stageT.start();
	}
	
	public void execute(BuilderRequest message) {
		setText("scegli dove posizionare il costruttore");
		catchPosition();
		
	}
	public void catchPosition() {
		stageT =new Thread(()->{
			synchronized(this){
				gameCont.clearInput();
				gameCont.setListening(true);
				System.out.println("(controller-position)listenig for position");
				int[] position = null;
				while(!gameCont.isStartValid()) {
					gameCont.clearInput();
					Platform.runLater(()->{gameCont.setText("inserisci posizione valida");});
						while(!gameCont.isChanged()) {
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						position = gameCont.getStart();
				}
				sendMessage(new BuilderResponse(position));
				System.out.println("(controller-position)builderResponse at "+position[0]+" "+position[1]);
				gameCont.setListening(false);
				gameCont.clearInput();
			}
			Platform.runLater(()->{setText("validazione costruttore");});
			Thread.currentThread().interrupt();;
		});
		stageT.setDaemon(true);
		stageT.setName("catchPos");
		stageT.start();
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
		loader.setLocation(getClass().getResource("/fxml/cardSelection.fxml"));
		AnchorPane an = (AnchorPane)loader.load();
		((SelectController)loader.getController()).setCard(cardlist);
		Scene scene = new Scene(an,630,470);
		Sstage.setScene(scene);
		ArrayList<Integer> array = null;
		 do{
			Sstage.showAndWait();
			array = ((SelectController)loader.getController()).getSelection();
		}while (array.size()!=i);
		System.out.println("returned selected divinity");
		gameCont.setText("attendi gli avversari" );
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
		System.out.println("(controller-bool)start I.O.E");
		e.printStackTrace();
		System.out.println("(controller-bool)end I.O.E");
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
	
		Listener(Socket socket){
				try {
					input=new ObjectInputStream(socket.getInputStream());
				} catch (IOException e) {
					System.out.println("error creating input socket");
				}
		}
		@Override
		public void  run() {
			try {
			while(true) {
				synchronized (this){
					try {
						message = (Message) input.readObject();
						if (message instanceof MessageToClient) {
							Platform.runLater(()->{
							((MessageToClient)message).accept(cont);
							//long thread methods, send response
							});
						}
						else if (message instanceof MessageUpdate) {
							Platform.runLater(()->{
							((MessageUpdate)message).accept(cont);
							//must send ack
							});
						}
						else if (message instanceof MessageSystem) {
							((MessageSystem)message).accept(cont);
							//fast synchronized methods
						}		
					} catch (ClassNotFoundException e) {
						System.out.println("(listener) message not supported");
					}
				}
			}
			}catch(EOFException e) {
			}catch(IOException e) {	
				System.out.println("(listener)IOException socket error ");
			}
			System.out.println("listener closed");
			Platform.runLater(()->{
				reStart();
			});
		}
	}
}