package utils;

import client.ClientLauncher;

public class UserNameRequest implements MessageSystem{

	private static final long serialVersionUID = 1L;
	
	@Override
	public void accept(ClientLauncher clientLauncher) {
		clientLauncher.execute(this);
		
	}

}
