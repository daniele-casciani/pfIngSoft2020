package divinity;

import game.Game;
import tower.BuilderAction;
import tower.Level;

public final class Apollo extends Divinity {
	final private  int cardID=1; 
	
	public Apollo(Game game) {
		super(game);
	}
	
	@Override
	public void round() {
		
		startRound();
		
		lose();

		tryer(new MoveApollo());
		
		tryer(new Build());
		
		endRound();
	}
		
	class MoveApollo extends Move {
		
		@Override
		public boolean execute (Object arg0, Object arg1) {
		Level start = (Level)arg0;
		Level end = (Level)arg1;
		if(isNear(start, end)) { 
			if( end.getHeight()!=4) {
				if(isNextLevel(start, end) || isPreviousLevel(start, end) || isSameLevel(start, end) ) {
					BuilderAction nowmove = new BuilderAction(game);
					if(game.getEffectList().isEmpty()==false) {
						for (ActivePower x : game.getEffectList()) {
							if (x.move()==true && x.actionLimitation(start, end) ) {
								game.getController().invalidAction(game.getCurrentPlayer().getName(), "Invalid Move");
								return false;
							}
						}
					}
					if(end.getHeight()==-1) {
						game.getController().updateSwitcBuilder(start.getPosition(), end.getPosition());
						
						String nameBuilderend = nowmove.builderName(end);
						nowmove.killBuilder(end);
						nowmove.movement(start, end);
						nowmove.newBuilder(start, nameBuilderend);
						
						return true;
					}
					else {
						game.getController().updateMovement(start.getPosition(), end.getPosition());
						if(end.getHeight()==3) {
							nowmove.movement(start, end);
							game.winGame();
						}else nowmove.movement(start, end);
						return true;
					}
				}
				else {
					game.getController().invalidAction(game.getCurrentPlayer().getName(), "Invalid Move");
					return false;
					}
			}else {
				game.getController().invalidAction(game.getCurrentPlayer().getName(), "Invalid Move");
				return false;
				}
		}
		game.getController().invalidAction(game.getCurrentPlayer().getName(), "Invalid Move");
		return false;
		}
	}
}

