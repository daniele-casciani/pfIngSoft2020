package divinity;

import game.Game;
import tower.BuilderAction;
import tower.Level;

public final class Athena extends Divinity {
	// final private  int cardID=3;
	
	private boolean power = false;
	
	private void setBool(boolean bool) {
		this.power = bool;
	}
	private boolean getBool() {
		return this.power;
	}

	public Athena(Game game) {
		super(game);
	}
	
	@Override
	public void round() {
		
		
		startRound();
		
		if(lose()) {
			game.loseGame();
		}
		else {
			tryer(new MoveAthena());
			
			tryer(new Build());
			
			endRound();
		}
	}
	
	@Override
	void endRound() {
		if(getBool() == true) {
			AthenaEffect active = new AthenaEffect();
			game.getEffectList().add(active);
		}
	}
	
	@Override
	void startRound() {
			if(game.getEffectList().isEmpty()==false && getBool() == true) {
				if(game.getEffectList().removeIf(x->x instanceof AthenaEffect)) {
					setBool(false);
				}
			}

	}
	
	class MoveAthena extends Move {
		@Override
		public boolean execute(Object arg0, Object arg1) {
			Level start = (Level)arg0;
			Level end = (Level)arg1;
			
			buildselect[0]= end.getPosition()[0];
			buildselect[1]=	end.getPosition()[1];
			
			BuilderAction nowmove = new BuilderAction(game);
			
			if(isNear(start, end) && start.getHeight()==-1 && nowmove.builderName(start).equals(game.getCurrentPlayer().getName())) {
				if( isPreviousLevel(nowmove.getLUnderB(start), end) || isSameLevel(nowmove.getLUnderB(start), end) ) {
					
					if(game.getEffectList().isEmpty()==false) {
						for (ActivePower x : game.getEffectList()) {
							if (x.move()==true && x.actionLimitation(nowmove.getLUnderB(start), end) == true ) {
								game.getController().invalidAction(game.getCurrentPlayer().getName(), "Mossa non valida");
								return false;
							}
						}
					} 
					game.getController().updateMovement(start.getPosition(), nowmove.getLUnderB(start).getHeight(), end.getPosition(), end.getHeight(), nowmove.builderName(start));
					if(end.getHeight()==3 && win(nowmove,start,end)) {
						nowmove.movement(start, end);
						game.winGame();
					}
					else nowmove.movement(start, end);
					return true;
				} else {
					if(isNextLevel(nowmove.getLUnderB(start), end)) { 
						
						if(game.getEffectList().isEmpty()==false) {
							for (ActivePower x : game.getEffectList()) {
								if (x.move() == true && x.actionLimitation(nowmove.getLUnderB(start), end) == true) {
									game.getController().invalidAction(game.getCurrentPlayer().getName(), "Mossa non valida");
									return false;
								}
							}
						}

						// a different here where effect is active 
						setBool(true);
						
						game.getController().updateMovement(start.getPosition(), nowmove.getLUnderB(start).getHeight(), 
								end.getPosition(), end.getHeight(), nowmove.builderName(start));
						if(end.getHeight()==3 && win(nowmove,start,end) ) {
							nowmove.movement(start, end);
							game.winGame();
						}else nowmove.movement(start, end);
						return true;
					}
					
					else {
						game.getController().invalidAction(game.getCurrentPlayer().getName(), "Mossa non valida");
						return false;
					}
				}
			}
			else {
				game.getController().invalidAction(game.getCurrentPlayer().getName(), "Mossa non valida");
				return false;
			}
		}
	}
}

