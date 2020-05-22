package divinity;

import game.Game;
import tower.BuilderAction;
import tower.Level;

public final class Athena extends Divinity {
	final private  int cardID=3;
	public Athena(Game game) {
		super(game);
	}
	
	@Override
	public void round() {
		
		startRound();
		
		lose();

		tryer(new Move());
		
		tryer(new Build());
		
		endRound();
	}
	
	@Override
	void startRound() {
		if(game.getEffectList().isEmpty()==false) {
			for(ActivePower x : game.getEffectList()) {
				if(x instanceof AthenaEffect) game.getEffectList().remove(x);
			}
		}
	}
	
	class MoveAthena extends Move {
		@Override
		public boolean execute(Object arg0, Object arg1) {
			Level start = (Level)arg0;
			Level end = (Level)arg1;
			BuilderAction nowmove = new BuilderAction(game);
			
			if(isNear(start, end) && start.getHeight()==-1 && nowmove.builderName(start).equals(game.getCurrentPlayer().getName())) {
				if( isPreviousLevel(nowmove.getLUnderB(start), end) || isSameLevel(nowmove.getLUnderB(start), end) ) {
					
					if(game.getEffectList().isEmpty()==false) {
						for (ActivePower x : game.getEffectList()) {
							if (x.move()==true && x.actionLimitation(nowmove.getLUnderB(start), end) ) {
								game.getController().invalidAction(game.getCurrentPlayer().getName(), "Invalid Move");
								return false;
							}
						}
					} 
					game.getController().updateMovement(start.getPosition(), nowmove.getLUnderB(start).getHeight(), end.getPosition(), end.getHeight(), nowmove.builderName(start));
					if(end.getHeight()==3) {
						nowmove.movement(start, end);
						game.winGame();
					}
					else nowmove.movement(start, end);
					return true;
				} else {
					if(isNextLevel(nowmove.getLUnderB(start), end)) { 
						
						if(game.getEffectList().isEmpty()==false) {
							for (ActivePower x : game.getEffectList()) {
								if (x.move()==true && x.actionLimitation(nowmove.getLUnderB(start), end) ) {
									game.getController().invalidAction(game.getCurrentPlayer().getName(), "Invalid Move");
									return false;
								}
							}
						}

						// a different here where effect is active 
						AthenaEffect active = new AthenaEffect();
						game.getEffectList().add(active);
						
						game.getController().updateMovement(start.getPosition(), nowmove.getLUnderB(start).getHeight(), end.getPosition(), end.getHeight(), nowmove.builderName(start));
						if(end.getHeight()==3) {
							nowmove.movement(start, end);
							game.winGame();
						}else nowmove.movement(start, end);
						return true;
					}
					
					else {
						game.getController().invalidAction(game.getCurrentPlayer().getName(), "Invalid Move");
						return false;
					}
				}
			}
			else {
				game.getController().invalidAction(game.getCurrentPlayer().getName(), "Invalid Move");
				return false;
			}
		}
	}
}

