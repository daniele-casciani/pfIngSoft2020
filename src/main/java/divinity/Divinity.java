package divinity;

import Game.*;
import tower.*;

<<<<<<< HEAD
public class Divinity {
=======
class Divinity {
>>>>>>> 0ff997eb564303cea540472870501f21957aa93c
	final private boolean threeplayer=true;
	final private boolean fourplayer=true;
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
		
	}
	
	public void build() {
		
	}
	
	public void move(Cell start, Cell end) {
		Divinity god = new Divinity(gameMap);
		
<<<<<<< HEAD
		if((this.isNear(start, end))==true); //controllo vicinanza della cella 
		//controllo sui livelli 
=======
		if(god.isNear(start, end)) { // controllo prima la vicinanza 
			
			// controllo movimento di livello 
			if(god.isNextLevel(start, end) || god.isPreviousLevel(start, end)) {
				// se entro qui posso chiamare lo spostamento
			}
			//mossa non valida 
		}
		//mossa non valida
>>>>>>> 0ff997eb564303cea540472870501f21957aa93c
	}
	
	public void win() {
		
	}
	
	public void lose() {
		
	}
	
	public void endRound() {
		
	}
	
	public void startRound() {
		
	}
	
	public void setup() {
		
	}
// TODO da rivedere dopo creazione coordinate in cell	
	private boolean isNear(Cell start, Cell end) {
		int xs=-1;
		int xe=-1;
		int ye=-1;
		int ys=-1;
		
		//salvo le coordinate delle celle 
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
<<<<<<< HEAD
				if(start.equals(game.getMap().getCell(i,j))){
=======
				if(start.equals(gameMap.getCell(i,j))){ // getCell in map da implementare
>>>>>>> 0ff997eb564303cea540472870501f21957aa93c
					xs = i;
					ys = j;
				}
				if(end.equals(game.getMap().getCell(i,j))) {
					xe = i;
					ye = j;
				}
			}
		}
		 
		//segue un controllo sulla vicinanza degli indici
		
		if(xs>=0 && xe>=0 && ys>=0 && ye>=0) {
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
		else return false;
	}
	
	public boolean isSameLevel(Level start, Level end) {
		if(start.getClass() == end.getClass()) return true;
	}
	
	public boolean isNextLevel(Level start, Level end) { //controllo sul movimento verso l'alto
		
		if(end instanceof Dome) return false;
		
		else {
			if(start instanceof Cell && end instanceof TowerL0) return true;
			if(start instanceof TowerL0 && end instanceof TowerL1) return true;
			if(start instanceof TowerL1 && end instanceof TowerL2) return true;
		}
		
		return false;
	}
	
	public boolean isPreviousLevel(Level start, Level end) {// controllo il movimento verso il basso 
		
		if(end instanceof Dome) return false;
		
		else {
			if(start instanceof TowerL2 && end instanceof TowerL1) return true;
			if(start instanceof TowerL1&& end instanceof TowerL0) return true;
			if(start instanceof TowerL0 && end instanceof Cell) return true;
		}
		
		return false;
	}
}
