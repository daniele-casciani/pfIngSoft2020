package lobby;
import java.io.DataInputStream;
import java.io.IOException;
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
					// Listen for a connection request, add new connection to the list
					Socket socket = serverSocket.accept();
					//create a new thread
					new Thread(()->{
						try {
							DataInputStream input = new DataInputStream(socket.getInputStream());
							boolean created=false;
							while (created=false) {
								//TODO richiesta nome
								// Get message from the client
								String message = input.readUTF();
								//send message via server broadcast
								if(true) {//TODO tipomessaggio=nomeplayer
									userList.add(new User(message,socket));
									created=true;
								}
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
	
}
