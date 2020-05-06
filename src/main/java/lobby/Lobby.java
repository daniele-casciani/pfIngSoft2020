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
	
	int[] createDeck(){
		int[] deck = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		return deck;
	 }
	
	@Override
	public int choseCard( int[] deck, User player) {
		// restituisce la divinity scelta dal giocatore x 
		ObjectInputStream input;
		ObjectOutputStream output;
		boolean state = false;
		SelectCardResponse response = null;
		while(state == false) {
			try {
				input = new ObjectInputStream(player.getSocket().getInputStream());
				output = new ObjectOutputStream(player.getSocket().getOutputStream());
				
				output.writeObject(new SelectCardRequest(deck));
				output.flush();
				
				try {
					response = (SelectCardResponse) input.readObject();
					if(response.getCard() < 1) {
						state = false;
						invalidAction(player.getUserID(), "Card not exists");
					}
					else state = true;
				} catch (ClassNotFoundException e) {
					state=false;
				}				
			} catch (IOException e) {
				state = false;
			}
		}
		
		return response.getCard();
	}
	
	@Override
	public int[] selectCard(int[] deck, User player) {
		
		boolean state = false;
		ObjectInputStream input;
		ObjectOutputStream output;
		ChoseCardResponse response = null;
		
		while(state == false) {
			try {
				input = new ObjectInputStream(player.getSocket().getInputStream());
				output = new ObjectOutputStream(player.getSocket().getOutputStream());
				
				output.writeObject(new ChoseCardRequest(deck));
				output.flush();
				
				try {
					response = (ChoseCardResponse) input.readObject();
					if(response.getCardlist() == null) {
						state = false;
						invalidAction(player.getUserID(), "Selection not valid");
					}
					else {
						for(int i = 0; i < userlist.size(); i++) {
							if(response.getCardlist()[i]<=0) {
								state = false;
								invalidAction(player.getUserID(), "Selection not valid");
								break;
							}
							state = true;
						}
					}
				} catch (ClassNotFoundException e) {
					state=false;
				}				
			} catch (IOException e) {
				state = false;
			}
		}
		
		return response.getCardlist();
	}
	
	@Override
	public Object[] choseMovement(String player) {
		
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
	public void invalidAction(String player, String message) {
		
		ObjectOutputStream output;
		
		for (User x : userlist) {
			if(player.equals(x.getUserID())) {
				try {
					output = new ObjectOutputStream(x.getSocket().getOutputStream());
					//send error
					output.writeObject(new InvalidAction(message));
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
