package divinity;
import Game.*;
import tower.*;

public class Divinity {
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
		
		if((this.isNear(start, end))==true); //controllo vicinanza della cella 
		//controllo sui livelli 
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
				if(start.equals(game.getMap().getCell(i,j))){
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
	
}
