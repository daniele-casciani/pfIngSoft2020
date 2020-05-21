package utils;

import client.Controller;

public class PlayerNumberRequest implements MessageToClient {

	private static final long serialVersionUID = -844760108252260939L;

	@Override
	public void accept(Controller clientLauncher) {
		clientLauncher.execute(this);
		
	}

}
