package utils;

import client.Controller;

public class UserNameResponse implements MessageSystem{
	
	private static final long serialVersionUID = 1L;
	private String name;
	
	public String getName() {
		return name;
	}
	
	public UserNameResponse(String username) {
		name=username;
	}

	@Override
	public void accept(Controller clientLauncher) {}
}
