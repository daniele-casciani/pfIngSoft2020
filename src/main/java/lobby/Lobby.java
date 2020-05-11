package lobby;

import game.*;
import utils.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

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
	
	ArrayList<Integer> createDeck(){
		ArrayList<Integer> deck = (ArrayList<Integer>) Arrays.asList(1, 2, 3, 4, 5, 6, 8, 9, 10);
		return deck;
	 }
	
	@Override
	public int choseCard( ArrayList<Integer> deck, User player) {
		
		
		boolean state = false;
		ObjectInputStream input;
		ObjectOutputStream output;
		
		int selectedCard = 0;
		
		while(state == false) {
			try {
				
				input = new ObjectInputStream(player.getSocket().getInputStream());
				output = new ObjectOutputStream(player.getSocket().getOutputStream());
				
				output.writeObject(new ChoseCardRequest(deck));
				output.flush();
				
				selectedCard = ((ChoseCardResponse)(MessageToServer)input).getCard();
				state = true;
				
			} catch (IOException e) {
				state = false;
				invalidAction(player.getUserID(), "Selection not Valid");
			}
			
		}
		
		return selectedCard;
	}
	
	@Override
	public ArrayList<Integer> selectCard(ArrayList<Integer> deck, User player) {
		
		boolean state = false;
		ObjectInputStream input;
		ObjectOutputStream output;
		
		ArrayList<Integer> selectedCards = null;
		
		while(state == false) {
			try {
				
				input = new ObjectInputStream(player.getSocket().getInputStream());
				output = new ObjectOutputStream(player.getSocket().getOutputStream());
				
				output.writeObject(new SelectCardRequest(deck));
				output.flush();
				
				selectedCards = ((SelectCardResponse)(MessageToServer)input).getCard();
				state = true;
				
			} catch (IOException e) {
				state = false;
				invalidAction(player.getUserID(), "Selection not Valid");
			}
			
		}
		
		return selectedCards;
	}
	
	@Override
	public ArrayList<Integer> choseMovement(String player) {
		
		ObjectInputStream input;
		ObjectOutputStream output;
		ArrayList<Integer> response = null;
		
		for (User x : userlist) {
			if(player.equals(x.getUserID())) {
				while(true) {
					try {
						input = new ObjectInputStream(x.getSocket().getInputStream());
						output = new ObjectOutputStream(x.getSocket().getOutputStream());
						//move request
							
						output.writeObject(new MoveRequest());
						output.flush();
							try {
								response = ((MoveResponse)(MessageToServer)input).getMovement();
								break;
							}catch(ClassCastException ex) {invalidAction(player, "Move not Valid");};
							
						} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return response;
	}
	
	@Override
	public ArrayList<Integer> whereBuild(String player) {
		

		ObjectInputStream input;
		ObjectOutputStream output;
		ArrayList<Integer> response = null;
		
		for (User x : userlist) {
			if(player.equals(x.getUserID())) {
				while(true) {
					try {
						input = new ObjectInputStream(x.getSocket().getInputStream());
						output = new ObjectOutputStream(x.getSocket().getOutputStream());
						//build request
							
						output.writeObject(new BuildRequest());
						output.flush();
							try {
								response = ((BuildResponse)(MessageToServer)input).getBuilding();
								break;
							}catch(ClassCastException ex) {invalidAction(player, "Build not Valid");};
							
						} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return response;
	}
	
	@Override
	public void invalidAction(String player, String message) {
		
		ObjectOutputStream output;
		
		for (User x : userlist) {
			if(player.equals(x.getUserID())) {
				try {
					output = new ObjectOutputStream(x.getSocket().getOutputStream());
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
				output.writeObject(new Winner());
				output.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
		}
	}
	
	@Override
	public int[] positionBuilder(String player) {
		
		ObjectInputStream input;
		ObjectOutputStream output;
		int[] response = null;
		
		for (User x : userlist) {
			if(player.equals(x.getUserID())) {
				while(true) {
					try {
						input = new ObjectInputStream(x.getSocket().getInputStream());
						output = new ObjectOutputStream(x.getSocket().getOutputStream());
						//builder request
							
						output.writeObject(new BuilderRequest());
						output.flush();
							try {
								response = ((BuilderResponse)(MessageToServer)input).getPosition();
								break;
							}catch(ClassCastException ex) {invalidAction(player, "Position not valid");};
							
						} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return response;
	}
	
	@Override
	public void updateBuild(int[] position, int[] position2) {
		ObjectOutputStream output;
		boolean state = false;
		for(User x: userlist) {
			while(state == false){
				try {
					output = new ObjectOutputStream(x.getSocket().getOutputStream());
					output.writeObject(new BuildUpdate(position, position2));
					output.flush();
					
					state = true;
				} catch (IOException e) {
					state = false;
				}
			}
		}	
		
	}
	
	@Override
	public void updateMovement(int[] position, int[] position2) {
		
		ObjectOutputStream output;
		boolean state = false;
		for(User x: userlist) {
			while(state == false) {	
				try {
					output = new ObjectOutputStream(x.getSocket().getOutputStream());
					output.writeObject(new MoveUpdate(position, position2));
					output.flush();
					state = true;
				} catch (IOException e) {
					state = false;
				}
			}
		}	
					
	}
	
	@Override
	public void updateNewBuilder(int[] position) {
		ObjectOutputStream output;
		boolean state = false;
		for(User x: userlist) {
			while(state == false){
				try {
					output = new ObjectOutputStream(x.getSocket().getOutputStream());
					output.writeObject(new NewBuilderUpdate(position));
					output.flush();
					
					state = true;
				} catch (IOException e) {
					state = false;
				}
			}
		}	
	}
	
	 public void close(){
		 
		 for(User x : userlist) {
			while(true) { 
		        try {
		            x.getSocket().close();
		            break;
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
			}			
		 }
		 userlist.clear();
	 }
	 
	@Override
	public void run() {

		try {
			createGame();
			
			while(true) {
				
			}
			
		} finally {
			close();
		}
	}

	@Override
	public void updateSwitcBuilder(int[] position, int[] position2) {
		
		ObjectOutputStream output;
		boolean state = false;
		for(User x: userlist) {
			while(state == false){
				try {
					output = new ObjectOutputStream(x.getSocket().getOutputStream());
					output.writeObject(new SwitchPositionUpdate(position, position2));
					output.flush();
					
					state = true;
				} catch (IOException e) {
					state = false;
				}
			}
		}	
		
	}

	@Override
	public boolean askEffect(String user) {
		ObjectOutputStream output;
		ObjectInputStream input;
		
		boolean response = false;
		
		for (User x : userlist) {
			if(user.equals(x.getUserID())) {
				while(true) {
					try {
						input = new ObjectInputStream(x.getSocket().getInputStream());
						output = new ObjectOutputStream(x.getSocket().getOutputStream());
							
						output.writeObject(new EffectRequest());
						output.flush();
							try {
								response = ((EffectResponse)(MessageToServer)input).getBool();
								break;
							}catch(ClassCastException ex) {invalidAction(user, "Please retry");};
							
						} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return response;
		
	}

}
