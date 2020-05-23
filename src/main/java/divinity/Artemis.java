package divinity;

import java.io.IOException;

import divinity.Divinity.Setup;
import game.Game;
import tower.Level;

public final class Artemis extends Divinity {
	final private  int cardID=2;
	
	public Artemis(Game game) {
		super(game);
	}

	@Override
	public void round() {
		
		startRound();
		
		lose();
		
		if(game.getController().askEffect(game.getCurrentPlayer().getName(),"attivate potere?")) tryer(new Move());
		else super.tryer(new Move());
		
		super.tryer(new Build());
		
		endRound();
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
		Level start = null;
		
		while (done1 == false && game.getDisconnect() == false) {
				try {
					parameters = action.request();
					start = (Level) parameters[0]; // store start point
					try {
						done1 = action.execute(parameters[0],parameters[1]);
					}
					catch(ClassCastException e1){done1=false;}
				} catch (IOException e) {done1=false;}
			}
		while (done2 == false && game.getDisconnect() == false) {
			try {
				parameters = action.request();
				Level secondEnd = (Level) parameters[1];
				if(start.getPosition()[0] != secondEnd.getPosition()[0] || start.getPosition()[1] != secondEnd.getPosition()[1]) {
					try {
						done2 = action.execute(parameters[0],parameters[1]);
					}
					catch(ClassCastException e1){done2=false;}
				}
			} catch (IOException e) {done2=false;}
		}
	
	}
}
