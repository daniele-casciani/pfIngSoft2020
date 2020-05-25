package divinity;

import game.Game;
import tower.BuilderAction;
import tower.Level;

public class Triton extends Divinity{
	
	// final static int cardID = 29;
	private boolean power = false;
	
	public Triton(Game game) {
		super(game);
	}
	
	private boolean getPower() {
		return this.power;
	}
	private void setPower(Boolean bool) {
		this.power = bool;
	}
	@Override
	public void round() {
		startRound();
		
		if(lose()) {
			game.loseGame();
		}
		else {
			tryer(new MoveTriton());
			
			while(getPower() == true){
				tryer(new MoveTriton());
			}
			
			tryer(new Build());
			
			endRound();
		}
	}
	
	class MoveTriton extends Move {
		
		@Override
		public boolean execute (Object arg0, Object arg1) {
			Level start = (Level)arg0;
			Level end = (Level)arg1;
			
			buildselect[0]= end.getPosition()[0];
			buildselect[1]=	end.getPosition()[1];
			
			BuilderAction nowmove = new BuilderAction(game);
			
			
			if(isNear(start, end) && start.getHeight() == -1 && nowmove.builderName(start).equals(game.getCurrentPlayer().getName())) { 
				
				if(end.getHeight()!=-1 && end.getHeight()!=4 ) {
					
					if(isNextLevel(nowmove.getLUnderB(start), end) || isPreviousLevel(nowmove.getLUnderB(start), end) || isSameLevel(nowmove.getLUnderB(start), end) ) {
						
						if(game.getEffectList().isEmpty()==false) {
							for (ActivePower x : game.getEffectList()) {
								if (x.move()==true && x.actionLimitation(nowmove.getLUnderB(start), end) ) {
									
									game.getController().invalidAction(game.getCurrentPlayer().getName(), "Mossa non valida");
									return false;
								}
							}
						} 
							game.getController().updateMovement(start.getPosition(), nowmove.getLUnderB(start).getHeight(), end.getPosition(), end.getHeight(),nowmove.builderName(start));
							
							//control for Triton effect
							if(isPerimeterCell(end) == true) {
								if(game.getController().askEffect(game.getCurrentPlayer().getName(), "Vuoi riattivare il potere?") == true) {
									setPower(true);
								}else {
									setPower(false);
								}
							}else {
								setPower(false);
							}
							
							if(end.getHeight()==3 && win(nowmove,start,end)) {
								nowmove.movement(start, end);
								game.winGame();
							}else nowmove.movement(start, end);
							return true;
					}
					else {
						game.getController().invalidAction(game.getCurrentPlayer().getName(), "Mossa non valida");
						return false;
						}
				}else {
					game.getController().invalidAction(game.getCurrentPlayer().getName(), "Mossa non valida");
					return false;
				}
			}
			game.getController().invalidAction(game.getCurrentPlayer().getName(), "Mossa non valida");
			return false;
		}
	}
}
