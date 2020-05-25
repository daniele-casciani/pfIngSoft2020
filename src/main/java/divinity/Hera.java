package divinity;

import game.Game;

public class Hera extends Divinity {
	
	// final private  int cardID=20;
	
	public Hera(Game game) {
		super(game);
	}
	
	@Override
	public void round() {
		
		startRound();
		
		if (lose()) {
			game.loseGame();
		}
		else {
			tryer(new Move());
			tryer(new Build());
			endRound();
		}
	}
	
	@Override
	void startRound() {
			if(game.getEffectList().isEmpty()==false ) {
				for(ActivePower x : game.getEffectList()) {
					if(x instanceof HeraEffect) game.getEffectList().remove(x);
				}
			}

	}

	@Override
	void endRound() {
		
		HeraEffect active = new HeraEffect();
		game.getEffectList().add(active);
	
	}
}
