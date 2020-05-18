package client;

import javafx.application.Application;
import utils.*;

public class ClientLauncher implements Client {	
	public static void main( String[] args ) {
		 new ClientLauncher().main();
	 }
	public void main(){
			Application.launch(Controller.class);
		
	}
	@Override
	public void sendMessage(Message message) {

	}

	@Override
	public void handle(Message message) {
		// TODO Auto-generated method stub
		
	}
}
