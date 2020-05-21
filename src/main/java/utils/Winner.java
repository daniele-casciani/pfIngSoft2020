package utils;

import client.Controller;

public class Winner implements MessageSystem {

	private static final long serialVersionUID = -5045788844465693181L;

	//TODO statistiche partita
	
	@Override
	public void accept(Controller clientLauncher) {
		clientLauncher.notify(this);
		
	}
}
