package Game;
import lobby.User;
import divinity.*;
import java.util.ArrayList;

public class Game {
	private int round=0;
	private Player currentPlayer;
	private ArrayList<Player> playerList;
	private Map map = new Map();
	ArrayList<ActivePower> effectList;
	
	public Game(ArrayList<User> participants, ArrayList<Divinity> deck){
		ArrayList<Divinity> list = selectCard(deck, participants.get(0));
		Divinity chosenCard;
		for (int i=1; i<participants.size(); i++) {
			chosenCard=choseCard(deck,participants.get(i));
			playerList.add(new Player(participants.get(i).getUserID(),chosenCard));
			list.remove(chosenCard);
		}
		for (Player x : playerList) {
			x.getDivinity().setup();
		}
	}
	
	private Player rotation() {
		if (playerList.indexOf(currentPlayer)==(playerList.size()-1)){
			round++;
			return playerList.get(0);
			}
				
		return playerList.get(playerList.indexOf(currentPlayer)+1);
		}
	private void startGame() {
		while (true) {
			rotation();
			if (win()==true) {
				break;
			}
		}
	}
	boolean win(){
		//verificate condizioni vittoria
		}
	private Divinity choseCard(ArrayList<Divinity> deck, User player) {
		//sceglie carte tra la lista
	}
	private ArrayList<Divinity> selectCard(ArrayList<Divinity> deck, User player){
		// scegliere le carte da usare
	}
}
