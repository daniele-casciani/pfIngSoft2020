package utils;

import client.Controller;

public class MoveRequest implements MessageToClient{

	private static final long serialVersionUID = -5299982018320354278L;

	@Override
	public void accept(Controller clientLauncher) {
		clientLauncher.execute(this);
		
	}
	
}
