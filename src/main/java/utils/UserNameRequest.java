package utils;

import client.Controller;

public class UserNameRequest implements MessageToClient{

	private static final long serialVersionUID = 6205892167326886800L;

	@Override
	public void accept(Controller clientLauncher) {
		clientLauncher.execute(this);
		
	}
}
