package lobby;
import divinity.*;
import game.*;
import utils.*;

import java.util.ArrayList;

class Lobby implements ServerController , Runnable {
	private Model model;
	private ArrayList<User> userlist;
	private Server server;
	
	public Lobby(ArrayList<User> userlist, Server server) {
		this.userlist=userlist;
		this.server = server;
	}
	
	void createGame() {
		Model game = new Game(userlist, createDeck(), this);
		this.model=game;
	 }
	
	ArrayList<Divinity> createDeck(){
		// TODO
		 return null ;
	 }
	@Override
	public Divinity choseCard(ArrayList<Divinity> deck, User player) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<Divinity> selectCard(ArrayList<Divinity> deck, User player) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void choseMovement(String player) {
		// HP: il model attende una azione e chiama il controller
		
		for (User x : userlist) {
			if(player.equals(x.getUserID())) {
				// richiesta selezione del builder 
				Object builderR = server.sendRequest("Select Builder", x);
				// richiesta selezione della cella vicina 
				Object cellR = server.sendRequest("Select Cell", x);
				//TODO passaggio delle selezioni al model
			}
		}
	}
	@Override
	public Object[] wereBuild(String player) {
		// HP: il model attende una azione e chiama il controller 
		Object[] parametres;
		for (User x : userlist) {
			if(player.equals(x.getUserID())) {
				// richiesta selezione del builder 
				Object builderR = server.sendRequest("Select Builder", x);
				// richiesta selezione della cella vicina 
				Object cellR = server.sendRequest("Select Cell", x);
				//TODO passaggio delle selezioni al model
			}
		}
	}
	@Override
	public void invalidAction(String player) {
		// TODO Auto-generated method stub
		for (User x : userlist) {
			if(player.equals(x.getUserID())) {
				
			}
		}
	}
	@Override
	public void loser(Player player) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void winner(Player player) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void positionBuilder() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
