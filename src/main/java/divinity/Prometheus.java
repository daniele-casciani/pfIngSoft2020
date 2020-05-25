package divinity;

import tower.BuilderAction;
import tower.Level;

import game.Game;

public final class Prometheus extends Divinity {
	//final private  int cardID=10;
	
	public Prometheus(Game game) {
		super(game);
	}
	
	@Override
	public void round() {
		if(game.getController().askEffect(game.getCurrentPlayer().getName(),"attivate potere?")) {
			
			startRound();
			
			tryer(new BuildPrometheus());
			
			if(lose()) {
				game.loseGame();
			}
			else {
				tryer(new MovePrometheus());
				
				tryer(new Build());
				
				endRound();
			}
			
		}else super.round();
	}
	
	class MovePrometheus extends Move {
		@Override
		public boolean execute (Object arg0, Object arg1) {
			Level start = (Level)arg0;
			Level end = (Level)arg1;
			
			buildselect[0]= end.getPosition()[0];
			buildselect[1]=	end.getPosition()[1];
			
			BuilderAction nowmove = new BuilderAction(game);
			
			if(isNear(start, end) && start.getHeight() == -1 && nowmove.builderName(start).equals(game.getCurrentPlayer().getName())) { 
				if(end.getHeight()!=-1 && end.getHeight()!=4 ) {
					if(isPreviousLevel(nowmove.getLUnderB(start), end) || isSameLevel(nowmove.getLUnderB(start), end) ) {
						
						if(game.getEffectList().isEmpty()==false) {
							for (ActivePower x : game.getEffectList()) {
								if (x.move()==true && x.actionLimitation(nowmove.getLUnderB(start), end) ) {
									game.getController().invalidAction(game.getCurrentPlayer().getName(), "Mossa non valida");
									return false;
								}
							}
						} 
						game.getController().updateMovement(start.getPosition(), nowmove.getLUnderB(start).getHeight(), end.getPosition(), end.getHeight(), nowmove.builderName(start));
						if(end.getHeight()==3 && win(nowmove, start, end)) {
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
	class BuildPrometheus extends Build {
		@Override
		public boolean execute(Object start, Object end) {

			Level builderCell = (Level)start;
			Level whereBuild = (Level)end;
			BuilderAction buildAction = new BuilderAction(game);
			
			
			if(isPossibleBuild(builderCell, whereBuild) && builderCell.getHeight() == -1 && buildAction.builderName(builderCell).equals(game.getCurrentPlayer().getName())) {
				
				if(game.getEffectList().isEmpty()==false) {
					for (ActivePower x : game.getEffectList()) {
						if (x.build()==true && x.actionLimitation(builderCell, whereBuild) == true) {
							
							game.getController().invalidAction(game.getCurrentPlayer().getName(), "Costruzione non permessa");
							return false;
						}
					}
				} 
				
				if(whereBuild.getHeight()==3) {
					buildAction.buildDome(whereBuild);
					Level newCell = game.getMap().getCell(whereBuild.getPosition()[0], whereBuild.getPosition()[1]);
					game.getController().updateBuild( newCell.getPosition(),newCell.getHeight());
					
				}
				else {
					buildAction.buildTower(whereBuild);
					Level newCell = game.getMap().getCell(whereBuild.getPosition()[0], whereBuild.getPosition()[1]);
					game.getController().updateBuild( newCell.getPosition(),newCell.getHeight());
				}
			}
			else {
				game.getController().invalidAction(game.getCurrentPlayer().getName(), "Costruzione non permessa");
				return false;
			}
			return true;
		}
	}
}
