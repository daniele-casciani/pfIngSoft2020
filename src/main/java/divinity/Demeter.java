package divinity;

import java.io.IOException;

import game.Game;
import tower.Level;

public final class Demeter extends Divinity {
	//final private  int cardID=5;
	public Demeter(Game game) {
		super(game);
	}
	@Override
	public void round() {
		
		startRound();
		
		if(lose()) {
			game.loseGame();
		}
		else {
			super.tryer(new Move());
					
			if(game.getController().askEffect(game.getCurrentPlayer().getName(),"attivate potere?")) tryer(new Build());
			else super.tryer(new Build());
			
			endRound();
		}
	}
	
	@Override
	public void setup() {
		super.tryer(new Setup());
		super.tryer(new Setup());
		game.getController().invalidAction(game.getCurrentPlayer().getName(),"attesa avversari");
	}
	
	@Override 
	void tryer(Action action) {
		boolean done2 = false;
		boolean done1 = false;
		Object[] parameters;
		Level p1 = null;
		
		while (done1 == false && game.getDisconnect() == false) {
				try {
					parameters = action.request();
					p1 = (Level) parameters[1];
					try {
						done1 = action.execute(parameters[0],parameters[1]);
					}
					catch(ClassCastException e1){done1=false;}
				} catch (IOException e) {done1=false;}
			}
		while (done2 == false && game.getDisconnect() == false) {
			try {
				parameters = action.request();
				Level p2 = (Level) parameters[1];
				if(p1.getPosition()[0] != p2.getPosition()[0] ||p1.getPosition()[1] != p2.getPosition()[1]) {
					try {
						done2 = action.execute(parameters[0],parameters[1]);
					}
					catch(ClassCastException e1){done2=false;}
				}
			} catch (IOException e) {done2=false;}
		}
	
	}
}
