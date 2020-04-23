package divinity;

import Game.Game;
import tower.BuilderAction;
import tower.Level;
import tower.Cell;

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
		//inizio turno giocatore x
		//lancio startRound
		//lancio lose sui costruttori di x
		//selezione del costruttore
		//lancio di move 
		//selezione cella dove costruire 
		//lancio di build
		//lancio di win 
		//lancio di endRound
		
	}
	
	public void build(Level builderCell, Level whereBuild) {
		
		if(isPossibleBuild(builderCell, whereBuild)) {
			if(whereBuild instanceof TowerL3) {
				// nel caso base costruisco la cupola sulla torrel2
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
		
		
		if(isNear((Cell)start, (Cell)end)) { // controllo prima la vicinanza 
			
			// controllo movimento di livello 
			if(isNextLevel(start, end) || isPreviousLevel(start, end) || isSameLevel(start, end) ) {
				// se entro qui posso chiamare lo spostamento
			}
			//mossa non valida 
		}
		//mossa non valida
	}
	
	public void win() {
		
	}
	public void lose(Level builderCell) {
		if(!isThereMove(builderCell)) {
			//costruttore è bloccato 
		}
		//altrimenti non faccio nulla e procedo con il round
	}
	
	private boolean isThereMove(Level builderCell) {
		
		for(int i=0; i<5; i++)
			for(int j=0; j<5;j++) {
				if(isNear((Cell)builderCell, (Cell)game.getMap().getCell(i, j))) {
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
		// per il gioco base si tratta solo di scegliere due celle per i costruttori
		// si richide al client una cella 
		// si controlla che non ci siano altri builder
		// se non ci sono chiamo il costruttore di builder
		// mossa non valida altrimenti
	}

	private boolean isNear(Cell start, Cell end) {
		int xs, ys, xe, ye;
		
		//salvo le coordinate delle celle 
		xs = start.getPosition()[0];
		ys = start.getPosition()[1];
		
		xe = end.getPosition()[0];
		ye = end.getPosition()[1];		
		 
		//segue un controllo sulla vicinanza degli indici
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
		
		if(end instanceof Dome) return false;
		if(end instanceof Builder) return false;
		
		if(start.getClass() == end.getClass()) return true;
		else return false;
	}
	
	private boolean isNextLevel(Level start, Level end) { //controllo sul movimento verso l'alto
		
		if(end instanceof Dome) return false;
		if(end instanceof Builder) return false;
		
		else {
			if(start instanceof Cell && end instanceof TowerL1) return true;
			if(start instanceof TowerL1 && end instanceof TowerL2) return true;
			if(start instanceof TowerL2 && end instanceof TowerL3) return true;
		}
		
		return false;
	}
	
	private boolean isPreviousLevel(Level start, Level end) {// controllo il movimento verso il basso 
		
		if(end instanceof Dome) return false;
		if(end instanceof Builder) return false;
		
		else {
			if(start instanceof TowerL3 && end instanceof TowerL2) return true;
			if(start instanceof TowerL2 && end instanceof TowerL1) return true;
			if(start instanceof TowerL1 && end instanceof Cell) return true;
		}
		
		return false;
	}
	
	private boolean isPossibleBuild(Level builderCell, Level whereBuild) {
		Divinity god = new Divinity(game);
		
		if(god.isNear((Cell)builderCell, (Cell)whereBuild)) { // controllo vicinanza
			if(end instanceof Dome) return false;			// controllo no cupola 
			if(end instanceof Builder) return false;		// controllo no costruttore
			return true;
			}
		else return false;
	}
}
