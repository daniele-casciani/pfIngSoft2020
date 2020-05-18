package utils;

import client.ClientLauncher;
import client.Controller;

public class Winner implements MessageSystem {

	private static final long serialVersionUID = 1L;

	//TODO statistiche partita
	
	@Override
	public void accept(Controller clientLauncher) {
		clientLauncher.notify(this);
		
	}
}
