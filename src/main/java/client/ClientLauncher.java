package client;

import java.io.IOException;
import java.net.Socket;

import utils.*;

public class ClientLauncher implements Client {
	private ClientController controller ;
	private Socket socket;
	private Listener listener;
	private Thread listT;
	private Thread contT;
	
	public void main(){
		try {
			socket= new Socket("127.0.0.1", 5000);	
			listener = new Listener(socket,this);
			listT= new Thread (listener);
			listT.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			controller = new Controller();
			contT = new Thread (controller);
			contT.start();
	}

	@Override
	public void sendMessage() {
		// TODO Auto-generated method stub

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
	
	public void notify(MoveUpdate moveUpdate) {
		// TODO Auto-generated method stub
		
	}
	public void notify(Loser loser) {
		// TODO Auto-generated method stub
		
	}

	public void notify(Winner winner) {
		// TODO Auto-generated method stub
		
	}
	
	public void execute(BuilderRequest message) {
		//TODO
	}

	public void execute(BuildRequest message) {
		//TODO
	}
}
