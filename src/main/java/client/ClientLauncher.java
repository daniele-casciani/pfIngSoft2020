package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import utils.*;

public class ClientLauncher implements Client {
	private ClientController controller ;
	private Socket socket;
	private ObjectOutputStream output;
	private Listener listener;
	private Thread listT;
	
	public static void main( String[] args ) {
		 new ClientLauncher().main();
	 }
	public void main(){
		try {
			controller = new Controller();
			Application.launch(Controller.class);
			socket= new Socket("127.0.0.1", 51344);
			output =new ObjectOutputStream(socket.getOutputStream());
			listener = new Listener(socket,this);
			listT= new Thread (listener);
			listT.start();
		} catch (IOException e) {
		System.out.print(" start server unreachble ");
		e.printStackTrace();
		System.out.print(" end server unreachble ");
	}

	}

	@Override
	public void sendMessage(Message message) {
		try {
			output.writeObject(message);
			output.flush();
		} catch (IOException e) {
			System.out.print("errore : impossibile contattare il server");
			e.printStackTrace();
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
	
	public void notify(InvalidAction message) {
		controller.setText(message.getError());
	}

	public void notify(NewBuilderUpdate update) {
		controller.addConstructor(update.getPosition()[0],update.getPosition()[1]);	
	}

	public void notify(BuildUpdate update) {
		controller.construction(update.getPosition()[0],update.getPosition()[1],update.getPosition()[2]);	
	}
	
	public void notify(MoveUpdate update) {
		controller.construction(update.getMovement()[0], update.getMovement()[1], update.getMovement()[2]);
		controller.construction(update.getMovement()[3], update.getMovement()[4], update.getMovement()[5]);
		controller.addConstructor(3, 4);
	}
	public void notify(Loser loser) {
		controller.endLoser(loser);
		
	}

	public void notify(Winner winner) {
		controller.endWinner(winner);
		
	}
	
	public void execute(BuilderRequest message) {
		controller.setText("posiziona il costruttore");
		controller.catchPosition();
	}

	public void execute(BuildRequest message) {
		controller.setText("scegli dove costruire");
		controller.catchDrag();
	}

	public void execute(ChoseCardRequest request) {
		controller.setText("scegli la tua carta");
		int cardID;
		try {
			 cardID = controller.catchSelection(request.getCardlist(),1).get(0);
		} catch (IOException e) {
			System.out.println("start impossibile caricare select image");
			e.printStackTrace();
			System.out.println("end impossibile caricare select image");
			return;
		}
		sendMessage(new ChoseCardResponse(cardID));
	}

	public void execute(MoveRequest request) {
		controller.setText("scegli dove muoverti");
		controller.catchDrag();
	}

	public void execute(PlayerNumberRequest request) {
		controller.setText("digita numerogiocatori");
		controller.playerNumber();
	}

	public void execute(SelectCardRequest request) {
		controller.setText("scegli le carte da usare");
		ArrayList<Integer> array;
		try {
			array = controller.catchSelection(request.getCardlist(),request.getNumber());
		} catch (IOException e) {
			System.out.println("start impossibile caricare select image");
			e.printStackTrace();
			System.out.println("end impossibile caricare select image");
			return;
		}
		sendMessage(new SelectCardResponse(array));
	}

	public void execute(UserNameRequest request) {
		
		Platform.runLater(()->{
		System.out.print("start run later");
		try {
			controller.login();
		} catch (IOException e) {
			System.out.println("start : impossibile aprire finestra login");
			e.printStackTrace();
			System.out.println("end : impossibile aprire finestra login");
		}
		});
	}

	public void execute(EffectRequest request){
		controller.setText("vuoi attivare il tuo potere?");
		try {
			controller.boolChoice("attivare il potere?");
		} catch (IOException e) {
			System.out.println("errore : impossibile richiedere scelta");
		}
	}

	public void execute(SwitchPositionUpdate update) {
		controller.construction(update.getPositions()[0], update.getPositions()[1], update.getPositions()[2]);
		controller.construction(update.getPositions()[3], update.getPositions()[4], update.getPositions()[5]);
		controller.addConstructor(0, 1);
		controller.addConstructor(3, 4);
		
	}
}
