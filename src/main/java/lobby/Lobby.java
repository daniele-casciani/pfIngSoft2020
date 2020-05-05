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
