package divinity;

import java.io.IOException;

import divinity.Divinity.Build;
import divinity.Divinity.Move;
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
