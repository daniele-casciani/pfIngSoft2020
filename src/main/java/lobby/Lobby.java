package lobby;
import Game.*;
import divinity.*;
import java.util.ArrayList;

class Lobby implements ServerController , Runnable {
	private Model model;
	private ArrayList<User> userlist;
	
	public Lobby(ArrayList<User> userlist) {
		this.userlist=userlist;
	}
	
	void createGame() {
		Model game = new Game(userlist,createDeck(), this);
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
	public void choseMovement() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void wereBuild() {
		// TODO Auto-generated method stub
		
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
