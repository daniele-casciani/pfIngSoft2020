package utils;

public class UserNameResponse implements MessageSystem{
	
	private static final long serialVersionUID = 1L;
	private String name;
	
	public String getName() {
		return name;
	}
	
	UserNameResponse(String username) {
		username=name;
	}
}
