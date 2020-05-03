package lobby;
import Game.*;
import divinity.*;
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
	public void choseMovement(Player player) {
		// HP: il model attende una azione e chiama il controller
		
		for (User x : userlist) {
			if(model.getCurrentPlayer().equals(x.getUserID())) {
				// richiesta selezione del builder 
				String builderR = server.sendRequest("Select Builder", x);
				// richiesta selezione della cella vicina 
				String cellR = server.sendRequest("Select Cell", x);
				//TODO passaggio delle selezioni al model
			}
		}
	}
	@Override
	public void wereBuild(Player player) {
		// HP: il model attende una azione e chiama il controller 
		for (User x : userlist) {
			if(model.getCurrentPlayer().equals(x.getUserID())) {
				// richiesta selezione del builder 
				String builderR = server.sendRequest("Select Builder", x);
				// richiesta selezione della cella vicina 
				String cellR = server.sendRequest("Select Cell", x);
				//TODO passaggio delle selezioni al model
			}
		}
	}
	@Override
	public void invalidAction() {
		// TODO Auto-generated method stub
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
