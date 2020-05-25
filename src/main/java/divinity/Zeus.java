package divinity;

import game.Game;
import tower.BuilderAction;
import tower.Level;

public class Zeus extends Divinity{
	//final static int cardID = 30;
	
	public Zeus(Game game) {
		super(game);
	}
	
	public void round() {
		startRound();
		
		if(lose()) {
			game.loseGame();
		}else {
			tryer(new Move());
			tryer(new BuildZeus());			
			endRound();
		}
	}
	
	class BuildZeus extends Build {
		
		@Override
		public boolean execute(Object start, Object end) {

			Level builderCell = (Level)start;
			Level whereBuild = (Level)end;
			
			BuilderAction buildAction = new BuilderAction(game);

			if(!(builderCell.getPosition()[0]==buildselect[0] && builderCell.getPosition()[1]==buildselect[1])){
				game.getController().invalidAction(game.getCurrentPlayer().getName(), "usa lo stesso builder");
				return false;
			}
			
			
			if(buildAction.getLUnderB(builderCell).getHeight() < 3) { // if true is possible active the effect of zeus 
				if (game.getController().askEffect(game.getCurrentPlayer().getName(), "Vuoi attivare il potere?") == true) {
					
					String nameBuilderend = buildAction.builderName(builderCell);
					buildAction.killBuilder(builderCell);
					Level newCell = game.getMap().getCell(builderCell.getPosition()[0], builderCell.getPosition()[1]);
					buildAction.buildTower(newCell);
					Level newCell2 = game.getMap().getCell(newCell.getPosition()[0],newCell.getPosition()[1]);
					buildAction.newBuilder(newCell2, nameBuilderend);
					Level newBuildCell = game.getMap().getCell(newCell2.getPosition()[0], newCell2.getPosition()[1]);
					
					game.getController().updateBuild( newBuildCell.getPosition(),buildAction.getLUnderB(newBuildCell).getHeight());
					return true;
				}
								
			}
			
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
