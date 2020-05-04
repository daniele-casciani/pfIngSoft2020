package lobby;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import utils.*;

class Server {
	ArrayList<Lobby> lobbyList =new ArrayList<Lobby>();
	ServerSocket serverSocket;
	ArrayList<User> userList =new ArrayList<User>();
	
	public void start() {
		new Thread(() -> {
			try {
				// Create a server socket
				serverSocket = new ServerSocket(5000);
				while (true) {
					// Listen for a connection request
					Socket socket = serverSocket.accept();
					//create a new thread
					new Thread(()->{

						try {//handling new User
							ObjectInputStream input;
							ObjectOutputStream output= new ObjectOutputStream(socket.getOutputStream());
							while (true) {
								output.writeObject(new UserNameRequest());
								output.flush();
								input = new ObjectInputStream(socket.getInputStream());

								// Get message from the client
								try {//creating new user
									String message = ((UserNameResponse)(MessageToServer) input).getName();
									userList.add(new User(message,socket));
									break;
								}catch(ClassCastException ex) {output.writeObject(new InvalidAction("UserName Expected"));};
							}
						}
						catch (IOException ex) {ex.printStackTrace();}
					}).start();
				}
			} catch (IOException ex) {System.out.println("start error");}
			finally {
				try {
				serverSocket.close();
				} catch (IOException ex) {System.out.println("server shutdown");}
				}
		}).start();
		}
	
	private void createLobby(){
		//TODO
	}

	public Object sendRequest(String message, User user) {
		Object clientResponse = null;
		try {
			DataInputStream input = new DataInputStream(user.getSocket().getInputStream());
			ObjectOutputStream output = new ObjectOutputStream(user.getSocket().getOutputStream());
			output.writeUTF(message);
			clientResponse = input.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return clientResponse;
	}
}
