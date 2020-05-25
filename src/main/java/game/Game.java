package game;
import lobby.ServerController;
import lobby.User;
import divinity.*;
import java.util.ArrayList;

public class Game implements Model {
	private boolean disconect = false;
	private String discUser = null;
	private int round=0;
	private Player currentPlayer;
	private ArrayList<Player> playerList = new ArrayList<Player>();
	private Map map = new Map();
	private ArrayList<ActivePower> effectList = new ArrayList<ActivePower>();
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
			for (int i=1; i<participants.size(); i++) {
				final int chosenCard = serverController.choseCard(selectedCards, participants.get(i));
				playerList.add(new Player(participants.get(i).getUserID(), chosenCard, this));
				serverController.updateCard(participants.get(i).getUserID(),chosenCard);
				selectedCards.removeIf(card-> card==chosenCard);
			}
			playerList.add(0,new Player(participants.get(0).getUserID(), selectedCards.get(0), this));
			serverController.updateCard(participants.get(0).getUserID(),selectedCards.get(0));
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
		boolean endgame = false;
		
		for (Player x : playerList) {
			currentPlayer=x;
			x.getDivinity().setup();
		}
		currentPlayer= playerList.get(0);
		while (endgame == false) {
			if (playerList.size()<=1) {
				if (playerList.size()==1) {
					winGame();
					}
				endgame = true;
			}
			serverController.invalidAction(currentPlayer.getName(), "inizio del turno");
			currentPlayer.getDivinity().round();
			
			if(disconect == true) {
				if(this.discUser != null) {
					System.out.println("player: " +getCurrentPlayer().getName()+" has quit ");
					getController().sendDisconnection(this.discUser);
				}
				endgame = true;				
			}
			else {
				serverController.invalidAction(currentPlayer.getName(), "attesa degli avversari");
				currentPlayer = rotation();
			}
		}
	}

	public void loseGame() {
		
		serverController.loser(currentPlayer);
		Player loser = currentPlayer;
		playerList.remove(currentPlayer);
		
		for ( Player x : playerList) {
			getController().invalidAction(x.getName(), "Player: "+ loser.getName()+" has lost"); // notify the other players
		}
		
		
	}	
	
	public void winGame() {
		serverController.winner(currentPlayer);
		playerList.remove(currentPlayer);
			for (Player x : playerList) {
				serverController.loser(x);		
			}
		playerList.clear();
		setDisconect();
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
	@Override	
	public void setDisconect() {
		this.disconect = true;
	}
	public boolean getDisconnect() {
		return this.disconect;
	}
	@Override
	public void setDiscUser(String username) {
		this.discUser = username;		
	}
}
