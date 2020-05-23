package divinity;

import tower.BuilderAction;
import tower.Level;
import divinity.Divinity.Build;
import divinity.Divinity.Move;
import game.Game;

public final class Minotaur extends Divinity {
	final private  int cardID=8;
	
	public Minotaur(Game game) {
		super(game);
	}
	
	@Override
	public void round() {
		
		startRound();
				
		lose();

		super.tryer(new MoveMinotaur());
				
		super.tryer(new Build());
				
		endRound();
	}
	
	private Level sameDirection(Level start, Level end) {
		int dx = end.getPosition()[0]-start.getPosition()[0];
		int dy = end.getPosition()[1]-start.getPosition()[1];
		if(dx+end.getPosition()[0] >=0 && dx+end.getPosition()[0]<5 && dy+end.getPosition()[1] >=0 && dy+end.getPosition()[1]<5)
			return (Level) game.getMap().getCell(dx+end.getPosition()[0], dy+end.getPosition()[1]);
		else return null;
	}
	
	class MoveMinotaur extends Move {
		@Override
		public boolean execute (Object arg0, Object arg1) {
			Level start = (Level)arg0;
			Level end = (Level)arg1;
			BuilderAction nowmove = new BuilderAction(game);
			
			if(isNear(start, end) && start.getHeight() == -1 && nowmove.builderName(start).equals(game.getCurrentPlayer().getName())) {
				if(end.getHeight()!= 4) {
					if(end.getHeight()!= -1) {
						if(isNextLevel(nowmove.getLUnderB(start), end) || isPreviousLevel(nowmove.getLUnderB(start), end) || isSameLevel(nowmove.getLUnderB(start), end) ) {
							
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
							}else nowmove.movement(start, end);
							return true;
						}
						else {
							game.getController().invalidAction(game.getCurrentPlayer().getName(), "Invalid Move");
							return false;
							}
						
					}else { // effect minotaur get.height == -1 NB no control on level and not control on effect because force movement is different to do a movement
						if(nowmove.builderName(end).equals(game.getCurrentPlayer().getName())== false) {
							Level nextCell = sameDirection(start, end);
							if(nextCell != null && nextCell.getHeight() != -1 && nextCell.getHeight() != 4) {
								nowmove.movement(end, nextCell);
								game.getController().updateMovement(end.getPosition(), nowmove.getLUnderB(end).getHeight(), nextCell.getPosition(), nextCell.getHeight(), nowmove.builderName(end));
								Level newend = game.getMap().getCell(end.getPosition()[0], end.getPosition()[1]);
								if(newend.getHeight() == 3) {
									nowmove.movement(start, newend);
									game.winGame();
								}
								else nowmove.movement(start, newend);
								game.getController().updateMovement(start.getPosition(), nowmove.getLUnderB(start).getHeight(), newend.getPosition(), newend.getHeight(), nowmove.builderName(start));
								return true;
							}else {
								game.getController().invalidAction(game.getCurrentPlayer().getName(), "Invalid Move");
								return false;
							}
						}else {
							game.getController().invalidAction(game.getCurrentPlayer().getName(), "Invalid Move");
							return false;
							}
					}
				}else {
				game.getController().invalidAction(game.getCurrentPlayer().getName(), "Invalid Move");
				return false;
				}
			}else {
			game.getController().invalidAction(game.getCurrentPlayer().getName(), "Invalid Move");
			return false;
			}
		}
	}
}
