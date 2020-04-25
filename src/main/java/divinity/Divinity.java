package divinity;

import Game.Game;
import tower.BuilderAction;
import tower.Level;


public class Divinity {
	final private boolean threeplayer = true;
	final private boolean fourplayer = true;
	private Game game;
	
	public boolean isFourplayer() {
		return fourplayer;
	}
	public boolean isThreeplayer() {
		return threeplayer;
	}

	public Divinity(Game game) {
		this.game = game;
	}
	
	public void round() {
		
		startRound();
		
		lose();
		
		//selezione del costruttore e cella movimento 
		move(start, end);
		
		//selezione cella dove costruire
		build(builder, whereBuild);
		
		endRound();
	}
	
	public void build(Level builderCell, Level whereBuild) {
		
		if(isPossibleBuild(builderCell, whereBuild)) {
			if(whereBuild.getHeight()==3) {
				
				BuilderAction nowbuild = new BuilderAction(game);
				nowbuild.buildDome(whereBuild);
			}
			//altriemnti costruisco pezzi di torre
			else {
				BuilderAction nowbuild = new BuilderAction(game);
				nowbuild.buildTower(whereBuild);
			}
		}
		// mossa non valida
	}
	
	public void move(Level start, Level end) {
		
		
		if(isNear(start, end)) { // controllo prima la vicinanza 
			
			// controllo movimento di livello 
			if(isNextLevel(start, end) || isPreviousLevel(start, end) || isSameLevel(start, end) ) {
				BuilderAction nowmove = new BuilderAction(game);
				//controllo se sono al terzo livello
				if(end.getHeight()==3) {
					nowmove.movement(start, end);				
					win();
				}
				else {
					// se entro qui faccio solo il movimento
					nowmove.movement(start, end);
				}
			}
			//mossa non valida 
		}
		//mossa non valida
	}
	
	public void win() {
		// dichiara il vincitore
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
									if(otherBuilder.getHeight() == -1 && action.builderName(otherBuilder).equals(game.getCurrentPlayer().getName()) {
										if(!canBuilderMove(otherBuilder)) {
											action.killBuilder(otherBuilder);
											action.killBuilder(firstBuilder);
											//metodo che elimina dalla lista dei giocatori
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
	
	public void setup() {
		Level selecetedLevel;
		int i=0;
		// si richide al client una cella 
		// si controlla che non ci siano altri builder
		while(i<2) {
			if(selecetedLevel.getHeight()==0) {
				BuilderAction newBuilder = new BuilderAction(game);
				newBuilder.newBuilder(selectedLevel, game.getCurrentPlayer().getName());
				i++;
			}
			else {
				// mossa non valida altrimenti
			}
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
			return true;
		}
		else return false;
	}
}
