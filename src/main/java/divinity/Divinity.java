package divinity;

import java.io.IOException;

import java.util.ArrayList;

import game.Game;
import tower.BuilderAction;
import tower.Level;


public class Divinity {
	final private boolean threeplayer = true;
	final private boolean fourplayer = true;
	Game game;
	final private int cardID=0;
	
	

	
	public void round() {
		
		startRound();
		
		lose();

		tryer(new Move());
		
		tryer(new Build());
		
		endRound();
	}
	
	 void tryer(Action action) {
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
			BuilderAction nowbuild = new BuilderAction(game);
			
			if(isPossibleBuild(builderCell, whereBuild) && builderCell.getHeight() == -1 && nowbuild.builderName(builderCell).equals(game.getCurrentPlayer().getName())) {
				
				if(whereBuild.getHeight()==3) {
				
					
					nowbuild.buildDome(whereBuild);
					game.getController().updateBuild(whereBuild.getPosition(), whereBuild.getHeight());
				}
				
				else {
					
					nowbuild.buildTower(whereBuild);
					game.getController().updateBuild( whereBuild.getPosition(), whereBuild.getHeight());
				}
			}
			else {
				game.getController().invalidAction(game.getCurrentPlayer().getName(), "Invalid Build");
				return false;
			}
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
			BuilderAction nowmove = new BuilderAction(game);
			
			
			if(isNear(start, end) && start.getHeight() == -1 && nowmove.builderName(start).equals(game.getCurrentPlayer().getName())) { 
				if(end.getHeight()!=-1 && end.getHeight()!=4 ) {
					if(isNextLevel(nowmove.getLUnderB(start), end) || isPreviousLevel(nowmove.getLUnderB(start), end) || isSameLevel(nowmove.getLUnderB(start), end) ) {
						
						if(game.getEffectList().isEmpty()==false) {
							for (ActivePower x : game.getEffectList()) {
								if (x.move()==true && x.actionLimitation(nowmove.getLUnderB(start), end) ) {
									game.getController().invalidAction(game.getCurrentPlayer().getName(), "Invalid Move");
									return false;
								}
							}
						} 
						game.getController().updateMovement(start.getPosition(), nowmove.getLUnderB(start).getHeight(), end.getPosition(), end.getHeight());
						if(end.getHeight()==3) {
							nowmove.movement(start, end);
							game.winGame();
						}else nowmove.movement(start, end);
						return true;
					}
					else {
						game.getController().invalidAction(game.getCurrentPlayer().getName(), "Invalid Move");
						return false;
						}
				}else {
					game.getController().invalidAction(game.getCurrentPlayer().getName(), "Invalid Move");
					return false;
				}
			}
			game.getController().invalidAction(game.getCurrentPlayer().getName(), "Invalid Move");
			return false;
		}

		@Override
		public Level[] request() {
			ArrayList<Integer> client = game.getController().choseMovement(game.getCurrentPlayer().getName());
			Level[] clientRequest = { game.getMap().getCell(client.get(0), client.get(1)), game.getMap().getCell(client.get(2), client.get(3))};
			return clientRequest;
		}
	}
	
	void lose() {
		
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
		BuilderAction nowmove = new BuilderAction(game);
		for(int i=0; i<5; i++)
			for(int j=0; j<5;j++) {
				if(isNear(builderCell, game.getMap().getCell(i, j))) {
					if(isNextLevel(nowmove.getLUnderB(builderCell), game.getMap().getCell(i, j)) || isPreviousLevel(nowmove.getLUnderB(builderCell), game.getMap().getCell(i, j)) || isSameLevel(nowmove.getLUnderB(builderCell), game.getMap().getCell(i, j))) {
						
						return true;
					}
					 	
				}
				
			}
		return false;	
	}
	
	void endRound() {
		// nel gioco base non fa nulla
	}
	
	void startRound() {
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
		
	public boolean isNear(Level start, Level end) {
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
	
	boolean isSameLevel(Level start, Level end) { // start has to be the level under the builder
		
		if(start.getHeight() == end.getHeight()) return true;
		else return false;
	}
	
	boolean isNextLevel(Level start, Level end) { // start has to be the level under the builder
		
			if(start.getHeight()==0 && end.getHeight()==1) return true;
			if(start.getHeight()==1 && end.getHeight()==2) return true;
			if(start.getHeight()==2 && end.getHeight()==3) return true;
		
		return false;
	}
	
	boolean isPreviousLevel(Level start, Level end) {// start has to be the level under the builder

	
		if(end.getHeight()-start.getHeight()<0) return true;
		
		return false;
	}
	
	boolean isPossibleBuild(Level builderCell, Level whereBuild) {
		
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

