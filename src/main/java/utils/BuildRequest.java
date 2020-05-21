package utils;

import client.Controller;

public class BuildRequest implements MessageToClient {

	private static final long serialVersionUID = 544639304742747257L;

	@Override
	public void accept(Controller clientLauncher) {
		clientLauncher.execute(this);
		
	}
}
