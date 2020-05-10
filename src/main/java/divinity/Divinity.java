package divinity;

import java.io.IOException;
import java.util.ArrayList;

import game.Game;
import tower.BuilderAction;
import tower.Level;


public class Divinity {
	final private boolean threeplayer = true;
	final private boolean fourplayer = true;
	private Game game;
	final private int cardID=0;

	
	public void round() {
		
		startRound();
		
		lose();

		tryer(new Move());
		
		tryer(new Build());
		
		endRound();
	}
	
	private void tryer(Action action) {
		boolean state= false;
		while (state==false) {
			Object[] parameters;
			try {
				parameters = action.request();
				try {
				state = action.execute(parameters[0],parameters[1]);
				}
				catch(ClassCastException e1){state=false;}
			} catch (IOException e) {state=false;}
		}
	}
	
	class Build implements Action {
		public boolean execute(Object start, Object end) {
			Level builderCell = (Level)start;
			Level whereBuild = (Level)end;
			if(isPossibleBuild(builderCell, whereBuild)) {
				if(whereBuild.getHeight()==3) {
				
					BuilderAction nowbuild = new BuilderAction(game);
					nowbuild.buildDome(whereBuild);
					game.getController().updateBuild(builderCell.getPosition(), whereBuild.getPosition());
				}
				//altriemnti costruisco pezzi di torre
				else {
					BuilderAction nowbuild = new BuilderAction(game);
					nowbuild.buildTower(whereBuild);
					game.getController().updateBuild(builderCell.getPosition(), whereBuild.getPosition());
				}
			}
			else return false;
			return true;
		}

		@Override
		public Level[] request() {
			ArrayList<Integer> client = game.getController().whereBuild(game.getCurrentPlayer().getName());
			Level[] clientRequest = { game.getMap().getCell(client.get(0), client.get(1)), game.getMap().getCell(client.get(2), client.get(3))};
			return clientRequest;			
		}
		
	}
	
	class Move implements Action {
		public boolean execute (Object arg0, Object arg1) {
			Level start = (Level)arg0;
			Level end = (Level)arg1;
			if(isNear(start, end)) { // controllo prima la vicinanza 
				//controllo movimento di livello 
				if(isNextLevel(start, end) || isPreviousLevel(start, end) || isSameLevel(start, end) ) {
					BuilderAction nowmove = new BuilderAction(game);
					if(game.getEffectList().isEmpty()==false) {
						for (ActivePower x : game.getEffectList()) {
							if (x.move()==true && x.actionLimitation(start, end) ) return false;
						}
					} 
					else { //effect list is empty
						nowmove.movement(start, end);
						game.getController().updateMovement(start.getPosition(), end.getPosition());
						if(end.getHeight()==3) {
							game.winGame();
						}
						return true;
					}
				}
				else return false;
			}
			return false;
		}

		@Override
		public Level[] request() {
			ArrayList<Integer> client = game.getController().choseMovement(game.getCurrentPlayer().getName());
			Level[] clientRequest = { game.getMap().getCell(client.get(0), client.get(1)), game.getMap().getCell(client.get(2), client.get(3))};
			return clientRequest;
		}
	}
	
	public void lose() {
		
		for(int i = 0; i<5; i++)
			for(int j=0; j<5; j++) {
				Level firstBuilder = game.getMap().getCell(i, j);
				if(firstBuilder.getHeight() == -1) {
					BuilderAction action = new BuilderAction(game);
					if(action.builderName(firstBuilder).equals(game.getCurrentPlayer().getName())) {
						if(!canBuilderMove(firstBuilder)) {
							for(int k=i;k<5;k++)
								for(int l=0;l<5;l++) {
									Level otherBuilder = game.getMap().getCell(k, l);
									if(otherBuilder.getHeight() == -1 && action.builderName(otherBuilder).equals(game.getCurrentPlayer().getName())) {
										if(!canBuilderMove(otherBuilder)) {
											action.killBuilder(otherBuilder);
											action.killBuilder(firstBuilder);
											game.loseGame();
										}
										k=5;l=5;//trovati i due builder esco dalla ricerca
									}
								}
						}
						i=5;j=5;
					}
				}
			}
	}
	
	private boolean canBuilderMove(Level builderCell) {
		
		for(int i=0; i<5; i++)
			for(int j=0; j<5;j++) {
				if(isNear(builderCell, game.getMap().getCell(i, j))) {
					if(isNextLevel(builderCell, game.getMap().getCell(i, j)) || isPreviousLevel(builderCell, game.getMap().getCell(i, j)) || isSameLevel(builderCell, game.getMap().getCell(i, j))) {
						//se entro qui il mio costruttore ha almeno una mossa disponibile
						return true;
					}
					// al contrario continua a scorrere 	
				}
				// al contrario continua a scorrere
			}
		return false;	//se non ho mosse possibili		
	}
	
	public void endRound() {
		// nel gioco base non fa nulla
	}
	
	public void startRound() {
		// nel gioco base non fa nulla 
	}
	
	public void setup(){
		tryer(new Setup());
		tryer(new Setup());
	}
	
	class Setup implements Action{
		public boolean execute(Object arg0, Object arg1) {
			Level selectedLevel = (Level)arg0;
			
				if(selectedLevel.getHeight()==0) {
					BuilderAction newBuilder = new BuilderAction(game);
					newBuilder.newBuilder(selectedLevel,game.getCurrentPlayer().getName());
					game.getController().updateNewBuilder(selectedLevel.getPosition());
				}
				else return false;
			return true;
		}

		@Override
		public Level[] request() throws IOException {
			int[] client = game.getController().positionBuilder(game.getCurrentPlayer().getName());
			Level[] clientRequest = { game.getMap().getCell(client[0], client[1]), null};
			return clientRequest;
		}
	}
		
	private boolean isNear(Level start, Level end) {
		int xs, ys, xe, ye;
		
		
		xs = start.getPosition()[0];
		ys = start.getPosition()[1];
		
		xe = end.getPosition()[0];
		ye = end.getPosition()[1];		
		 
		
		if(xe == xs) {
				if(ye == ys + 1) return true;
				if(ye == ys - 1) return true;
				return false;
		}
			
		if(xe == xs + 1) {
				if(ye == ys + 1) return true;
				if(ye == ys - 1) return true;
				if(ye == ys) return true;
				return false;
		}
			
		if(xe == xs - 1) {
				if(ye == ys + 1) return true;
				if(ye == ys - 1) return true;
				if(ye == ys) return true;
				return false;
		}
		return false;
	}
	
	private boolean isSameLevel(Level start, Level end) {
		
		if(end.getHeight()==4) return false;
		if(end.getHeight()==-1) return false;
		
		if(start.getHeight() == end.getHeight()) return true;
		else return false;
	}
	
	private boolean isNextLevel(Level start, Level end) { //controllo sul movimento verso l'alto
		
		if(end.getHeight()==4) return false;
		if(end.getHeight()==-1) return false;
		
		else {
			if(start.getHeight()==0 && end.getHeight()==1) return true;
			if(start.getHeight()==1 && end.getHeight()==2) return true;
			if(start.getHeight()==2 && end.getHeight()==3) return true;
		}
		
		return false;
	}
	
	private boolean isPreviousLevel(Level start, Level end) {// controllo il movimento verso il basso 
		if(end.getHeight()==4) return false;
		if(end.getHeight()==-1) return false;
		else {
			if(end.getHeight()-start.getHeight()<0) return true;
		}
		
		return false;
	}
	
	private boolean isPossibleBuild(Level builderCell, Level whereBuild) {
		
		if(isNear(builderCell, whereBuild)) {
			if(whereBuild.getHeight()==4) return false;
			if(whereBuild.getHeight()==-1)return false;
			if(game.getEffectList().isEmpty()==false) {
				for (ActivePower x : game.getEffectList()) {
					if (x.build()==true && x.actionLimitation(builderCell, whereBuild) ) return false;
				}
			}
			return true;
		}
		else return false;
	}

	public boolean isFourplayer() {
		return fourplayer;
	}
	public boolean isThreeplayer() {
		return threeplayer;
	}
	public int getCardID() {
		return cardID;
	}
	public Divinity(Game game) {
		this.game = game;
	}
}

