package game;
import lobby.ServerController;
import lobby.User;
import divinity.*;
import java.util.ArrayList;

public class Game implements Model {
	private int round=0;
	private Player currentPlayer;
	private ArrayList<Player> playerList;
	private Map map = new Map();
	private ArrayList<ActivePower> effectList;
	private ServerController serverController;
	
	public Game(ArrayList<User> participants, ArrayList<Integer> deck, ServerController serverController){
		this.serverController=serverController;
		if(deck.size()==0) {
			for(User x: participants) {
				playerList.add(new Player(x.getUserID(),0, this));
			}
		}
		else {
			ArrayList<Integer> selectedCards = serverController.selectCard(deck, participants.get(0));
			int chosenCard;
			for (int i=1; i<participants.size(); i++) {
				chosenCard = serverController.choseCard(selectedCards, participants.get(i));
				playerList.add(new Player(participants.get(i).getUserID(), chosenCard, this));
				selectedCards.remove(chosenCard);
			}
		}
		for (Player x : playerList) {
			currentPlayer=x;
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
			if (playerList.size()<=1) {
				if (playerList.size()==1) {
					serverController.winner(playerList.get(0));
					}
				break;
			}
			currentPlayer.getDivinity().round();
			rotation();
		}
	}

	public void loseGame() {
		serverController.loser(currentPlayer);
		playerList.remove(currentPlayer);
		
	}	
	
	public void winGame() {
		serverController.winner(currentPlayer);
		playerList.remove(currentPlayer);
		for (Player x : playerList) {
			serverController.loser(x);
		playerList.clear();
		}
	}

	public int getRound() {
		return round;
	}
	public ArrayList<ActivePower> getEffectList() {
		return effectList;
	}
	public ServerController getController() {
		return serverController;
	}
	@Override
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public Map getMap() {
		return map;
	}
}
