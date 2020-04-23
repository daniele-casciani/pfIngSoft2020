package Game;
import lobby.User;
import divinity.*;
import java.util.ArrayList;

public class Game {
	private int round=0;
	private Player currentPlayer;
	private ArrayList<Player> playerList;
	private  Map map = new Map();
	private ArrayList<ActivePower> effectList;
	
	public Map getMap() {
		return map;
	}
	
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
			round = getRound() + 1;
			return playerList.get(0);
			}
				
		return playerList.get(playerList.indexOf(currentPlayer)+1);
		}
	public void startGame() {
		while (true) {
			rotation();
			// TODO verificate condizioni vittoria
		}
	}

	private Divinity choseCard(ArrayList<Divinity> deck, User player) {
		return null;
		//TODO sceglie carte tra la lista
	}
	private ArrayList<Divinity> selectCard(ArrayList<Divinity> deck, User player){
		return null;
		// TODO scegliere le carte da usare
	}

	public int getRound() {
		return round;
	}

	public ArrayList<ActivePower> getEffectList() {
		return effectList;
	}
}
