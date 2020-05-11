package utils;

import client.ClientLauncher;

public class Loser implements MessageSystem {

	private static final long serialVersionUID = 1L;

	//TODO statistiche partita
	
	@Override
	public void accept(ClientLauncher clientLauncher) {
		clientLauncher.notify(this);
		
	}
}
