package lobby;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import utils.*;

public class Server {
	ArrayList<Lobby> lobbyList =new ArrayList<Lobby>();
	ServerSocket serverSocket;
	List<User> synUserList = Collections.synchronizedList(new ArrayList<User>());
	
	public void start() {
		createLobby();
		new Thread(() -> {
			try {
				// Create a server socket
				serverSocket = new ServerSocket(7648);
				System.out.println("serverSocket created");
				while (true) {
					// Listen for a connection request
					Socket socket = serverSocket.accept();
					System.out.println("serverSocket listenig");
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
									synUserList.add(new User(message,socket));
									notifyAll();
									break;
								}catch(ClassCastException ex) {output.writeObject(new InvalidAction("UserName Expected"));};
							}
						}
						catch (IOException ex) {
							System.out.println("start connection to client error");
							ex.printStackTrace();
							System.out.println("end connection to client error");
							}
					}).start();
				}
			} catch (IOException ex) {
				System.out.println("start server main thread error");
				ex.printStackTrace();
				System.out.println("end server main thread error");
				}
			finally {
				try {
				serverSocket.close();
				System.out.println("server shutdown");
				} catch (IOException ex) {System.out.println("server not shutdown");}
				}
		}).start();
		}
	
	private void createLobby(){
			new Thread(() -> {
				System.out.println("thread create lobby started");
				while(true) {
					try {
						if (synUserList.size()==0) {try {wait();} catch (InterruptedException e) {
												System.out.println("start lobby wait error");
												e.printStackTrace();
												System.out.println("end lobby wait error");
												}}
					if (synUserList.size()>0) {
						int gameplayer;
						ObjectOutputStream output= new ObjectOutputStream(synUserList.get(0).getSocket().getOutputStream());
						ObjectInputStream input = new ObjectInputStream(synUserList.get(0).getSocket().getInputStream());
						while (true) {
							output.writeObject(new PlayerNumberRequest());
							output.flush();
							try {
								gameplayer = ((PlayerNumberResponse)(MessageToServer) input).getNumber();
								break;
							}catch(ClassCastException ex) {output.writeObject(new InvalidAction("player number Expected"));};
						}
						while (true) {
							if (synUserList.size()>=gameplayer) {
								ArrayList<User> userList =new ArrayList<User>();
								for (int i=0; i<gameplayer;i++) {
									userList.add(synUserList.remove(0));
								}
								Lobby lobby=new Lobby(userList);
								lobbyList.add(lobby);
								break;
							}
							else {try {wait();} catch (InterruptedException e) {
								System.out.println("start wait lobby error");
								e.printStackTrace();
								System.out.println("end wait lobby error");
								}}
						}		
					}
					} catch (IOException ex) {
						System.out.println("start lobby creation error");
						ex.printStackTrace();
						System.out.println("end lobby creation error");
					}
				}	
			}).start();
			}
}
