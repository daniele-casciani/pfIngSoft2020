package divinity;

import java.io.IOException;

import game.Game;

public final class Demeter extends Divinity {
	final private  int cardID=5;
	public Demeter(Game game) {
		super(game);
	}
	@Override
	public void round() {
		
		startRound();
		
		lose();
		
		super.tryer(new Move());
				
		if(game.getController().askEffect(game.getCurrentPlayer().getName())) tryer(new Build());
		else super.tryer(new Build());
		
		endRound();
	}
	
	@Override 
	void tryer(Action action) {
		boolean done2 = false;
		boolean done1 = false;
		Object[] parameters;
		Object p1 = false;
		
		while (done1 == false) {
				try {
					parameters = action.request();
					p1 = parameters[1];
					try {
						done1 = action.execute(parameters[0],parameters[1]);
					}
					catch(ClassCastException e1){done1=false;}
				} catch (IOException e) {done1=false;}
			}
		while (done2 == false) {
			try {
				parameters = action.request();
				if(p1 != parameters[1]) {
					try {
						done2 = action.execute(parameters[0],parameters[1]);
					}
					catch(ClassCastException e1){done2=false;}
				}
			} catch (IOException e) {done2=false;}
		}
	
	}
}
