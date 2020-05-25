package divinity;

import game.Game;
import tower.BuilderAction;
import tower.Level;

public class Hestia extends Divinity{
	// final private int cardID = 21;
	
	public Hestia(Game game) {
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
			if(game.getController().askEffect(game.getCurrentPlayer().getName(), "Vuoi attivare il potere?")) {
				tryer(new BuildHestia());
			}
				
			endRound();
		}
	}
	
	class BuildHestia extends Build {
		
		@Override
		public boolean execute(Object start, Object end) {
			Level builderCell = (Level)start;
			Level whereBuild = (Level)end;
			BuilderAction buildAction = new BuilderAction(game);

			if(!(builderCell.getPosition()[0]==buildselect[0] && builderCell.getPosition()[1]==buildselect[1])){ // control on builder 
				game.getController().invalidAction(game.getCurrentPlayer().getName(), "usa lo stesso builder");
				return false;
			}
			
			
			
			if(isPossibleBuild(builderCell, whereBuild) && builderCell.getHeight() == -1 && buildAction.builderName(builderCell).equals(game.getCurrentPlayer().getName())) {
				
				if(game.getEffectList().isEmpty()==false) { // control effectlist 
					for (ActivePower x : game.getEffectList()) {
						if (x.build()==true && x.actionLimitation(builderCell, whereBuild) == true) {
							
							game.getController().invalidAction(game.getCurrentPlayer().getName(), "Costruzione non permessa");
							return false;
						}
					}
				} 
				
				if(isPerimeterCell(whereBuild)) return false; // control to respect the power of Hestia 
				
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
