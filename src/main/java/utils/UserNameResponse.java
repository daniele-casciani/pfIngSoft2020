package utils;

import client.ClientLauncher;

public class UserNameResponse implements MessageSystem{
	
	private static final long serialVersionUID = 1L;
	private String name;
	
	public String getName() {
		return name;
	}
	
	public UserNameResponse(String username) {
		username=name;
	}

	@Override
	public void accept(ClientLauncher clientLauncher) {}
}
