package lobby;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class User {
	private String userID;
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	
	public User(String userID,Socket socket, ObjectInputStream input2, ObjectOutputStream output2) {
		this.userID=userID;
		this.socket=socket;
		this.input=input2;
		this.output=output2;
	}

	public String getUserID() {
		return userID;
	}

	public Socket getSocket() {
		return socket;
	}

	public ObjectOutputStream getOutput() {
		return output;
	}

	public ObjectInputStream getInput() {
		return input;
	}
	
}
