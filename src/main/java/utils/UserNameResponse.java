package utils;

public class UserNameResponse implements MessageToServer{

	private static final long serialVersionUID = -7655771874834669004L;
	private String name;
	
	public String getName() {
		return name;
	}
	
	public UserNameResponse(String username) {
		name=username;
	}
}
