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
		
		if(userlist.size() == 2) b = askDivinityMode(userlist.get(0).getUserID(), "usare divinità?");
		else b = true;
		
		if (b) {
			deck = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,8,9,10));
			b = askDivinityMode(userlist.get(0).getUserID(), "usare divinità avanzate?");
			if(b) {
				deck.addAll(Arrays.asList(20,21,27,29,30));
				return deck;
			}
			else return deck;	 
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
					System.out.println("S.E. chosecard");
					sendDisconnection(player.getUserID());
					Thread.currentThread().interrupt();			
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
					sendDisconnection(player.getUserID());
					Thread.currentThread().interrupt();		
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
							System.out.println("(lobby-chosemove)S.E.");
							game.setDisconect();
							game.setDiscUser(player);
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
								System.out.println("(lobby-wherebuild)S.E.");
								game.setDisconect();
								game.setDiscUser(player);
								break;
							}
						} catch (IOException e) {
							System.out.println("(lobby-wherebuild)I.O.E.");
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
						}catch (SocketException e) {
							System.out.println("(lobby-invAct)S.E");
							game.setDisconect();
							game.setDiscUser(player);
							break;
						}
					} catch (IOException e) {
						System.out.println("(lobby-invAct)I.O.E.");
						break;
					}
				}
			}
		}
	}
	
	@Override
	public void loser(Player player) {
		User y = null;
		
		ObjectOutputStream output;
		
		for (User x : userlist) {
			if(x.getUserID().equals(player.getName())) {
				while(true) {			
					try {
						try {
							output = x.getOutput();
							output.writeObject(new Loser());
							output.flush();
							y = x;
							break;
						}catch(SocketException e) {
							System.out.println("(lobby-lose)S.E.");
							
						}
					} catch (IOException e) {
						System.out.println("(lobby-lose)I.O.E.");
					}
				}
			}
		}
		userlist.remove(y);
	}
	
	@Override
	public void winner(Player player) {
		
		ObjectOutputStream output;
		
		for (User x : userlist) {
			if(x.getUserID().equals(player.getName())){
				while(true) {
					try {
						try {
							output = x.getOutput();
							output.writeObject(new Winner());
							output.flush();
							break;
						}catch(SocketException e) {
							System.out.println("(lobby-winner)S.E.");
							break;
						}
					} catch (IOException e) {
						System.out.println("(lobby-winner)I.O.E.");
					}
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
							sendDisconnection(player);
							Thread.currentThread().interrupt();		
							break;
						}	
								
						} catch (IOException e) {
							System.out.println("(lobby-posbuilder)I.O.E.");
					}
				}
			}
		}
		return response;
	}
	
	@Override
	public void updateCard(String userID, int chosenCard) {
		ObjectOutputStream output;
		ObjectInputStream input;
		for(User x: userlist) {
			while(true){
				try {
					try {
						output = x.getOutput();
						input = x.getInput();
						output.writeObject(new CardUpdate(userID, chosenCard));
						output.flush();
						@SuppressWarnings("unused")
						Message message= ((Ack)( MessageSystem)(Message)input.readObject());		
						break;
					}catch(SocketException e) {
						sendDisconnection(x.getUserID());
						Thread.currentThread().interrupt();		
						break;
					}
					catch(ClassNotFoundException e) {}
				} catch (IOException e) {
					System.out.println("(lobby-updcard) I.O.E.");
				}
			}
		}	
	}
	
	@Override
	public void updateBuild(int[] position, int height) {
		ObjectOutputStream output;
		ObjectInputStream input;
		for(User x: userlist) {
			while(true){
				try {
					try {
						output = x.getOutput();
						input = x.getInput();
						output.writeObject(new BuildUpdate(position,height));
						output.flush();
						@SuppressWarnings("unused")
						Message message= ((Ack)( MessageSystem)(Message)input.readObject());
						break;
					}catch(SocketException  e) {
						System.out.println("(lobby-updbuild)S.E.");
						game.setDisconect();
						game.setDiscUser(x.getUserID());
						break;
					}
					catch(ClassNotFoundException e) {}
				} catch (IOException e) {
					System.out.println("(lobby-updbuild)I.O.E.");
				}
			}
		}	
		
	}
	
	@Override
	public void updateMovement(int[] position, int height1, int[] position2, int height2, String name) {
		
		ObjectOutputStream output;
		ObjectInputStream input;
		for(User x: userlist) {
			while(true) {	
				try {
					try {
						output = x.getOutput();
						input = x.getInput();
						output.writeObject(new MoveUpdate(position,height1, position2, height2, name));
						output.flush();
						@SuppressWarnings("unused")
						Message message= ((Ack)( MessageSystem)(Message)input.readObject());
						break;
					}catch(SocketException e) {
						System.out.println("(lobby-updmove)S.E.");
						game.setDisconect();
						game.setDiscUser(x.getUserID());
						break;
					}
					catch(ClassNotFoundException e) {}
				} catch (IOException e) {
					System.out.println("(lobby-updmove)I.O.E.");
				}
			}
		}	
					
	}
	
	@Override
	public void updateNewBuilder(int[] position, String name) {
		ObjectOutputStream output;
		ObjectInputStream input;
		for(User x: userlist) {
			while(true){
				try {
					try {
						output = x.getOutput();
						input = x.getInput();
						output.writeObject(new BuilderUpdate(position, name));
						output.flush();
						@SuppressWarnings("unused")
						Message message= ((Ack)( MessageSystem)(Message)input.readObject());		
						break;
					}catch(SocketException e) {
						sendDisconnection(x.getUserID());
						Thread.currentThread().interrupt();		
						break;
					}
					catch(ClassNotFoundException e) {}
				} catch (IOException e) {
					System.out.println("(lobby-updbuilder) I.O.E.");
				}
			}
		}	
	}
	
	 public void close(){
		 
		 for(User x : userlist) {
			while(true) { 
		        try {
		        	try {
			            x.getSocket().close();
			            break;
		        	}catch(SocketException e) {
		        		System.out.println("S.E close ");
		        		break;
		        	}
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
		} catch (Exception e) {
			System.out.println("(lobbyrun) start error");
			e.printStackTrace();
			System.out.println("(lobbyrun) end error");
		}
		finally {
			close();
			System.out.println("lobby closed");
		}
		return;
	}

	@Override
	public void updateSwitcBuilder(int[] position,int height1, String name1, int[] position2, int height2, String name2) {
		
		ObjectOutputStream output;
		ObjectInputStream input;
		for(User x: userlist) {
			while(true){
				try {
					try {
						output = x.getOutput();
						input = x.getInput();
						output.writeObject(new SwitchPositionUpdate(position,height1, name1, position2, height2, name2));
						output.flush();
						@SuppressWarnings("unused")
						Message message= ((Ack)( MessageSystem)(Message)input.readObject());
						break;
					}catch(SocketException e) {
						System.out.println("(lobby-upswitch)S.E.");
						game.setDisconect();
						game.setDiscUser(x.getUserID());
						break;
					}
					catch(ClassNotFoundException e) {}
				} catch (IOException e) {
					System.out.println("(lobby-upswitch)I.O.E.");
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
							
							output = x.getOutput();
							input = x.getInput();
								
							output.writeObject(new BooleanRequest(text));
							output.flush();
								try {
									response = ((BooleanResponse)(MessageToServer)(Message)input.readObject()).getBool();
									break;
								}catch(ClassCastException | ClassNotFoundException ex) {invalidAction(user, "Please retry");};
						}catch(SocketException e) {
							System.out.println("(lobby-askbool)S.E.");
							game.setDisconect() ;
							game.setDiscUser(user);
							sendDisconnection(user);
							break;
						}
					} catch (IOException e) {
						System.out.println("(lobby-askbool)I.O.E.");
					}
				}
			}
		}
		return response;
		
	}
	
	@Override
	public void sendDisconnection(String playerD) {
		ObjectOutputStream output;
		
		for(User x : userlist) {
			if(x.getUserID().equals(playerD) == false){ // playerD is already disconnected
				try {
					
					output = x.getOutput();
					
					output.writeObject(new PlayerDisconnect(playerD));
					output.flush();
					
				}catch(IOException e) {
					System.out.println("I.O.E in sendDisconnect");
				}
			}
		}
	}
	
	@Override
	public boolean askDivinityMode(String user, String text) {
		ObjectOutputStream output;
		ObjectInputStream input;
		
		boolean response = false;
		
		for (User x : userlist) {
			if(user.equals(x.getUserID())) {
				while(true) {
					try {
						try {
							
							output = x.getOutput();
							input = x.getInput();
								
							output.writeObject(new BooleanRequest(text));
							output.flush();
								try {
									response = ((BooleanResponse)(MessageToServer)(Message)input.readObject()).getBool();
									break;
								}catch(ClassCastException | ClassNotFoundException ex) {invalidAction(user, "Please retry");};
						}catch(SocketException e) {
							System.out.println("(lobby-askDivinity)S.E.");
							sendDisconnection(user);
							Thread.currentThread().interrupt();		
							break;
						}
					} catch (IOException e) {
						System.out.println("(lobby-askDivinity)I.O.E.");
					}
				}
			}
		}
		return response;
		
	}
}
