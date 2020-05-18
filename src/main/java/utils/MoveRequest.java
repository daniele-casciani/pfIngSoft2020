package utils;

import client.ClientLauncher;
import client.Controller;

public class MoveRequest implements MessageToClient{

	private static final long serialVersionUID = 1L;
	
	@Override
	public void accept(Controller clientLauncher) {
		clientLauncher.execute(this);
		
	}
	
}
