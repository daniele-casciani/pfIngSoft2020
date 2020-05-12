package utils;

import client.ClientLauncher;

public class PlayerNumberRequest implements MessageToClient {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void accept(ClientLauncher clientLauncher) {
		clientLauncher.execute(this);
		
	}

}
