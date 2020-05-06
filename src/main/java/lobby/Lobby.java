package lobby;
import divinity.*;
import game.*;
import utils.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

class Lobby implements ServerController , Runnable {
	private Model model;
	private ArrayList<User> userlist;
	
	public Lobby(ArrayList<User> userlist) {
		this.userlist=userlist;
	}
	
	void createGame() {
		Model game = new Game(userlist, createDeck(), this);
		this.model=game;
	 }
	
	ArrayList<Divinity> createDeck(){
		//aggiunge in un array tutte le divinity nel db 
		 return null ;
	 }
	@Override
	public Divinity choseCard(ArrayList<Divinity> deck, User player) {
		// restituisce la divinity scelta dal giocatore x 
		ObjectInputStream input;
		ObjectOutputStream output;
		
		
		return null;
	}
	@Override
	public ArrayList<Divinity> selectCard(ArrayList<Divinity> deck, User player) {
		// fa scegliere le carte al player 0 restituisce le carte selezionate
		boolean state = false;
		ObjectInputStream input;
		ObjectOutputStream output;
		int[] oldDeck = {1, 2, 3, 4, 5};
		ChoseCardResponse response = null;
		
		while(state == false) {
			try {
				input = new ObjectInputStream(player.getSocket().getInputStream());
				output = new ObjectOutputStream(player.getSocket().getOutputStream());
				
				output.writeObject(new ChoseCardRequest(oldDeck));
				output.flush();
				
				try {
					response = (ChoseCardResponse) input.readObject();
					if(response.getCardlist() == null) {
						state = false;
						invalidAction(player.getUserID());
					}
					else state = true;
				} catch (ClassNotFoundException e) {
					state=false;
				}				
			} catch (IOException e) {
				state = false;
			}
		}
		
		for(Divinity x : deck){
			boolean found = false;
			int i = 0;
			while(found == false && i < 10){
				if(x.getCardID() == response.getCardlist()[i]) found = true;
				else i++;
			}
			if (found == false) {
				deck.remove(x);
			}
		}
		return deck;
	}
	@Override
	public Object[] choseMovement(String player) {
		// HP: il model attende una azione e chiama il controller
		Object[] parametres = null;
		ObjectInputStream input;
		ObjectOutputStream output;
		
		for (User x : userlist) {
			if(player.equals(x.getUserID())) {
				try {
					input = new ObjectInputStream(x.getSocket().getInputStream());
					output = new ObjectOutputStream(x.getSocket().getOutputStream());
					//move request
					for(int i = 0; i<2; i++) {	
						output.writeObject(new MoveRequest());
						output.flush();
						try {
							parametres[i] = input.readObject();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return parametres;
	}
	@Override
	public Object[] whereBuild(String player) {
		// HP: il model attende una azione e chiama il controller 
		Object[] parametres = null;
		ObjectInputStream input;
		ObjectOutputStream output;
		
		for (User x : userlist) {
			if(player.equals(x.getUserID())) {
				try {
					input = new ObjectInputStream(x.getSocket().getInputStream());
					output = new ObjectOutputStream(x.getSocket().getOutputStream());
					//build request
					for(int i = 0; i<2; i++) {	
						output.writeObject(new BuildRequest());
						output.flush();
						try {
							parametres[i] = input.readObject();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return parametres;
	}
	@Override
	public void invalidAction(String player) {
		// TODO Auto-generated method stub
		ObjectInputStream input;
		ObjectOutputStream output;
		
		for (User x : userlist) {
			if(player.equals(x.getUserID())) {
				try {
					input = new ObjectInputStream(x.getSocket().getInputStream());
					output = new ObjectOutputStream(x.getSocket().getOutputStream());
					//send error
					output.writeObject(new InvalidAction("Invalid Action"));
					output.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		}
	}
	@Override
	public void loser(Player player) {
		
		
		ObjectOutputStream output;
		
		for (User x : userlist) {
			try {
				output = new ObjectOutputStream(x.getSocket().getOutputStream());
				//send loser message
				output.writeObject(new Loser());
				output.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
		}
	}
	@Override
	public void winner(Player player) {
		
		ObjectOutputStream output;
		
		for (User x : userlist) {
			try {
				
				output = new ObjectOutputStream(x.getSocket().getOutputStream());
				//send loser message
				output.writeObject(new Winner());
				output.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
		}
	}
	
	@Override
	public Object[] positionBuilder(String player) {
		
		Object[] parametres = null;
		ObjectInputStream input;
		ObjectOutputStream output;
		
		for (User x : userlist) {
			if(player.equals(x.getUserID())) {
				try {
					input = new ObjectInputStream(x.getSocket().getInputStream());
					output = new ObjectOutputStream(x.getSocket().getOutputStream());
					//builders request
					for(int i = 0; i<2; i++) {	
						output.writeObject(new BuilderRequest());
						output.flush();
						try {
							parametres[i] = input.readObject();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return parametres;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
