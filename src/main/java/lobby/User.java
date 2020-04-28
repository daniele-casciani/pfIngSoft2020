package lobby;

import java.net.Socket;

public class User {
	private String userID;
	private Socket socket;
	
	public User(String userID,Socket socket) {
		this.userID=userID;
		this.socket=socket;
	}

	public String getUserID() {
		return userID;
	}

	public Socket getSocket() {
		return socket;
	}
	
}
