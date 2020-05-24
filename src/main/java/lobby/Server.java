package lobby;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import utils.*;

public class Server {
	ArrayList<Thread> lobbyList =new ArrayList<Thread>();
	ServerSocket serverSocket;
	List<User> synUserList = Collections.synchronizedList(new ArrayList<User>());
	Thread lt = new LobbyT();
	Thread mt = new MainT();
	Object obj = new Object();
	
	public static void main( String[] args ) {
		 new Server().start();
	}
	
	public void start() {
		lt.setDaemon(true);
		lt.setName("lobbyCreator");
		lt.start();
		mt.setName("acceptClient");
		mt.start();
	}
	private class LobbyT extends Thread {
		
		@Override
		public void run() {
			System.out.println("thread create lobby started");
			while(true) {
				try {
					if (synUserList.size()==0) {synchronized(obj) {try {obj.wait();} catch (InterruptedException e) {
											System.out.println("start lobby wait error");
											e.printStackTrace();
											System.out.println("end lobby wait error");
											}}}
					if (synUserList.size()>0) {
						int gameplayer;
						ObjectOutputStream output= (synUserList.get(0).getOutput());
						ObjectInputStream input = (synUserList.get(0).getInput());
						while (true) {
							output.writeObject(new PlayerNumberRequest());
							output.flush();
							System.out.println("player number request sent");
							try {
								gameplayer = ((PlayerNumberResponse)(MessageToServer)(Message) input.readObject()).getNumber();
								break;
							}catch(ClassCastException | ClassNotFoundException ex) {output.writeObject(new InvalidAction("player number Expected"));};
						}
					while (true) {
						if (synUserList.size()>=gameplayer) {
							ArrayList<User> userList =new ArrayList<User>();
							for (int i=0; i<gameplayer;i++) {
								userList.add(synUserList.remove(0));
							}
							Thread t = new Thread(new Lobby(userList));
							t.start();
							lobbyList.add(t);
							System.out.println("lobby created");
							break;
						}
						else {synchronized(obj){try {obj.wait();} catch (InterruptedException e) {
							System.out.println("start wait lobby error");
							e.printStackTrace();
							System.out.println("end wait lobby error");
							}}}
					}		
				}
				} catch (IOException ex) {
					System.out.println("start lobby creation error");
					ex.printStackTrace();
					synUserList.remove(0);
					System.out.println("end lobby creation error");
				}
			}
		}
	}
	
	private class MainT extends Thread{
		
		@Override
		public void run() {
			try {
				// Create a server socket
				serverSocket = new ServerSocket(51344);
				System.out.println("serverSocket created");
				while (true) {
					// Listen for a connection request
					System.out.println("serverSocket listenig");
					Socket socket = serverSocket.accept();
					//create a new thread
					Thread userT = new Thread(()->{
						try {//handling new User
							ObjectInputStream input;
							ObjectOutputStream output= new ObjectOutputStream(socket.getOutputStream());
							input = new ObjectInputStream(socket.getInputStream());
							boolean unique = false;
							while (!unique) {
								output.writeObject(new UserNameRequest());
								output.flush();
								System.out.println("name request send");
								
								// Get message from the client
								try {//creating new user									
									String message = ((UserNameResponse)(Message) input.readObject()).getName();
									unique = true;									
									for(User x : synUserList) {
										if(x.getUserID().equals(message)) {
											unique =  false;
											output.writeObject(new InvalidAction("username già in uso"));
											output.flush();
										}
									}
									if (unique) {
										synUserList.add(new User(message,socket,input,output));
										System.out.println("new user: "+message);
										synchronized(obj) {obj.notify();}
									}
								}catch(ClassCastException | ClassNotFoundException ex) {
									System.out.println("Invalid name");
									ex.printStackTrace();}
								catch(SocketException | EOFException e ) {
									System.out.println("client disconnected");
									break;
								}
							}
						}
						catch (IOException ex) {
							System.out.println("start connection to client error");
							ex.printStackTrace();
							System.out.println("end connection to client error");
							}
					});
					userT.setName("userHandler");
					userT.setDaemon(true);
					userT.start();
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
		}
	}
}
