package lobby;

import game.*;
import utils.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;

public class Lobby implements ServerController , Runnable {
	private ArrayList<User> userlist;
	Model game;
	
	public Lobby(ArrayList<User> userlist) {
		this.userlist=userlist;
	}
	
	void createGame() {
		game = new Game(userlist, createDeck(), this);
	 }
	
	ArrayList<Integer> createDeck(){
		boolean b;
		ArrayList<Integer> deck;
		b = askEffect(userlist.get(0).getUserID(), "usare divinità?");
		if (b) {
			deck = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,8,9,10));
			
			return deck;	 
		}
		else return deck = new ArrayList<Integer>();
	 }
	
	@Override
	public int choseCard( ArrayList<Integer> deck, User player) {
		
		
		boolean state = false;
		ObjectInputStream input;
		ObjectOutputStream output;
		
		int selectedCard = 0;
		
		while(state == false) {
			try {
				try {
					output = player.getOutput();
					input = player.getInput();
					
					output.writeObject(new ChoseCardRequest(deck));
					output.flush();
					
					selectedCard = ((ChoseCardResponse)(MessageToServer)(Message)input.readObject()).getCard();
					state = true;
				}catch(SocketException e) {
					System.out.println("SE chosecard");
					break;
				}
			} catch (IOException | ClassNotFoundException e) {
				state = false;
				invalidAction(player.getUserID(), "please retry selection");
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
				
				try {
					output = player.getOutput();
					input = player.getInput();
					
					output.writeObject(new SelectCardRequest(deck,userlist.size()));
					output.flush();
					
					selectedCards = ((SelectCardResponse)(MessageToServer)(Message)input.readObject()).getCard();
					state = true;
				}catch(SocketException e) {
					System.out.println("SE aselectCard");
					break;
				}
				
			} catch (IOException | ClassNotFoundException e) {
				state = false;
				invalidAction(player.getUserID(), "Please retry selection");
			}
			
		}
		
		return selectedCards;
	}
	
	@Override
	public int[] choseMovement(String player) {
		
		ObjectInputStream input;
		ObjectOutputStream output;
		int[] response = null;
		
		for (User x : userlist) {
			if(player.equals(x.getUserID())) {
				while(true) {
					try {
						try {
							output =x.getOutput();
							input = x.getInput();
							//move request
								
							output.writeObject(new MoveRequest());
							output.flush();
								try {
									response = ((MoveResponse)(MessageToServer)(Message)input.readObject()).getMovement();
									break;
								}catch(ClassCastException | ClassNotFoundException ex) {invalidAction(player, "plase retry to move");};
						}catch(SocketException e) {
							System.out.println("SE choseMove");
							break;
						}
							
						} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return response;
	}
	
	@Override
	public int[] whereBuild(String player) {
		

		ObjectInputStream input;
		ObjectOutputStream output;
		int[] response = null;
		
		for (User x : userlist) {
			if(player.equals(x.getUserID())) {
				while(true) {
					try {
						try {
							output = x.getOutput();
							input = x.getInput();
							//build request
								
							output.writeObject(new BuildRequest());
							output.flush();
								try {
									response = ((BuildResponse)(MessageToServer)(Message)input.readObject()).getBuilding();
									break;
								}catch(ClassCastException | ClassNotFoundException ex) {invalidAction(player, "plaese retry to build");};
							}catch(SocketException e) {
								System.out.println("SE whereBuild");
								break;
							}
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
				while(true) {
					try {
						try {
							output = x.getOutput();
							output.writeObject(new InvalidAction(message));
							output.flush();
							break;
						}catch(SocketException e) {
							System.out.println("SE invalid Act");
							break;
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	@Override
	public void loser(Player player) {
		
		
		ObjectOutputStream output;
		
		for (User x : userlist) {
			while(true) {			
				try {
					try {
						output = x.getOutput();
						output.writeObject(new Loser());
						output.flush();
						break;
					}catch(SocketException e) {
						System.out.println("SE lose");
						break;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void winner(Player player) {
		
		ObjectOutputStream output;
		
		for (User x : userlist) {
			while(true) {
				try {
					try {
						output = x.getOutput();
						output.writeObject(new Winner());
						output.flush();
						break;
					}catch(SocketException e) {
						System.out.println("SE winner");
						break;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
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
						try {
							output = x.getOutput();
							input = x.getInput();
							
							//builder request
								
							output.writeObject(new BuilderRequest());
							output.flush();
								try {
									response = ((BuilderResponse)(MessageToServer)(Message)input.readObject()).getPosition();
									break;
								}catch(ClassCastException | ClassNotFoundException ex) {invalidAction(player, "Position not valid");};
						}catch(SocketException e) {
							System.out.println("SE positionbuilder");
							break;
						}	
								
						} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return response;
	}
	
	@Override
	public void updateBuild(int[] position, int position2) {
		ObjectOutputStream output;
		boolean state = false;
		for(User x: userlist) {
			while(state == false){
				try {
					try {
						output = x.getOutput();
						output.writeObject(new BuildUpdate(position, position2));
						output.flush();
						
						state = true;
					}catch(SocketException e) {
						System.out.println("SE ubuild");
						break;
					}
				} catch (IOException e) {
					state = false;
				}
			}
		}	
		
	}
	
	@Override
	public void updateMovement(int[] position, int height1, int[] position2, int height2) {
		
		ObjectOutputStream output;
		boolean state = false;
		for(User x: userlist) {
			while(state == false) {	
				try {
					try {
						output = x.getOutput();
						output.writeObject(new MoveUpdate(position,height1, position2, height2));
						output.flush();
						state = true;
					}catch(SocketException e) {
						System.out.println("SE uMove");
						break;
					}
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
					try {
						output = x.getOutput();
						output.writeObject(new NewBuilderUpdate(position));
						output.flush();
						
						state = true;
					}catch(SocketException e) {
						System.out.println("SE uNewBuild");
						break;
					}
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
		        	System.out.println("socket closed "+x.getUserID());
		        }
			}			
		 }
		 userlist.clear();
	 }
	 
	@Override
	public void run() {

		Thread.currentThread().setName(Thread.currentThread().getName()+ " lobby");
		try {
			createGame();
			game.startGame();
			System.out.println("end game");
		} finally {
			close();
			System.out.println("lobby closed");
		}

	}

	@Override
	public void updateSwitcBuilder(int[] position,int height1, int[] position2, int height2) {
		
		ObjectOutputStream output;
		boolean state = false;
		for(User x: userlist) {
			while(state == false){
				try {
					try {
						output = x.getOutput();
						output.writeObject(new SwitchPositionUpdate(position,height1, position2, height2));
						output.flush();
						
						state = true;
					}catch(SocketException e) {
						System.out.println("SE u switch");
						break;
					}
				} catch (IOException e) {
					state = false;
				}
			}
		}	
		
	}

	@Override
	public boolean askEffect(String user, String text) {
		ObjectOutputStream output;
		ObjectInputStream input;
		
		boolean response = false;
		
		for (User x : userlist) {
			if(user.equals(x.getUserID())) {
				while(true) {
					try {
						try {
							input = x.getInput();
							output = x.getOutput();
								
							output.writeObject(new BooleanRequest(text));
							output.flush();
								try {
									response = ((BooleanResponse)(MessageToServer)(Message)input.readObject()).getBool();
									break;
								}catch(ClassCastException | ClassNotFoundException ex) {invalidAction(user, "Please retry");};
						}catch(SocketException e) {
							System.out.println("SE askBool");
							break;
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return response;
		
	}

}
