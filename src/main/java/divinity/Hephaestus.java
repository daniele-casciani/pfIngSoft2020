package divinity;

import game.Game;

public final class Hephaestus extends Divinity {

	final private  int cardID = 6;
	
	public Hephaestus(Game game) {
		super(game);
	}
	
	public void round() {
		
		startRound();
		
		lose();

		tryer(new Move());
		
		tryer(new Build());
		
		endRound();
	}
}

