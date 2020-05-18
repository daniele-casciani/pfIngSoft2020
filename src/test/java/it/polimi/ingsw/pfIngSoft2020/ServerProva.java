package it.polimi.ingsw.pfIngSoft2020;


import java.net.*;
import java.util.ArrayList;
import lobby.*;
 
public class ServerProva extends Thread {
	private ServerSocket Server;
	private ArrayList<User> userlist;
	
	public ServerProva() throws Exception {
		Server = new ServerSocket(4000);
		System.out.println("Il Server è in attesa sulla porta 4000.");
		this.start();
	}
	
	public void run() {
		while(true) {
				try {
					System.out.println("In attesa di Connessione.");
					Socket client = Server.accept();
					System.out.println("Connessione accettata da: "+ client.getInetAddress());
					userlist.add(new User("clietprova", client));
				}
				catch(Exception e) {}
		}
	
	}
	
	public ArrayList<User> getArray(){
		return userlist;
	}
}
