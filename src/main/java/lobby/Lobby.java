package lobby;
import Game.*;
import divinity.*;
import java.util.ArrayList;

class Lobby {
	
	 Game createGame(ArrayList<User> userlist) {
		Game game = new Game(userlist,createDeck());
		return game;
	 }
	 ArrayList<Divinity> createDeck(){
		 
	 }
}
