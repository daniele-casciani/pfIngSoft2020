package divinity;

import game.Game;
import tower.BuilderAction;
import tower.Level;

public final class Hephaestus extends Divinity {

	//final private  int cardID = 6;
	
	public Hephaestus(Game game) {
		super(game);
	}
	
	@Override
	public void round() {
		
		startRound();
		
		if(lose()) {
			game.loseGame();
		}
		else {
			tryer(new Move());
			
			tryer(new BuildHephaestus());
			
			endRound();
		}
	}
	
	class BuildHephaestus extends Build {
		@Override
		public boolean execute(Object start, Object end) {
			
			Level builderCell = (Level)start;
			Level whereBuild = (Level)end;
			boolean done = false;
			BuilderAction nowbuild = new BuilderAction(game);
			
			if(!(builderCell.getPosition()[0]==buildselect[0] && builderCell.getPosition()[1]==buildselect[1])){
				game.getController().invalidAction(game.getCurrentPlayer().getName(), "usa lo stesso builder");
				return false;
			}
			
			if(isPossibleBuild(builderCell, whereBuild) && builderCell.getHeight() == -1 && nowbuild.builderName(builderCell).equals(game.getCurrentPlayer().getName())) {
				
				if(game.getEffectList().isEmpty()==false) {
					for (ActivePower x : game.getEffectList()) {
						if (x.build()==true && x.actionLimitation(builderCell, whereBuild) == true ) {
							
							game.getController().invalidAction(game.getCurrentPlayer().getName(), "Costruzione non permessa");
							return false;
						}
					}
				} 
				
				if(whereBuild.getHeight()==3) {
					nowbuild.buildDome(whereBuild);
					Level newCell = game.getMap().getCell(whereBuild.getPosition()[0], whereBuild.getPosition()[1]);
					game.getController().updateBuild( newCell.getPosition(),newCell.getHeight());
					done = true;
				}
				
				else {
					nowbuild.buildTower(whereBuild);
					Level newCell = game.getMap().getCell(whereBuild.getPosition()[0], whereBuild.getPosition()[1]);
					game.getController().updateBuild( newCell.getPosition(),newCell.getHeight());
					done = true;
				}
				// here done = true and i ask for the active power of the divinity
				if(game.getController().askEffect(game.getCurrentPlayer().getName(),"attivate potere?")) { // the activation of the effect does not imply a construction
					Level newCell = game.getMap().getCell(whereBuild.getPosition()[0], whereBuild.getPosition()[1]);
					
					if(newCell.getHeight()<3) {		// i can't add a dome, It is important to note that
														// here whereBuild will have a height increased by 1 compared to the beginning
						nowbuild.buildTower(newCell);
						Level newCell2 = game.getMap().getCell(newCell.getPosition()[0], newCell.getPosition()[1]);
						game.getController().updateBuild(newCell2.getPosition(), newCell2.getHeight());
					}
				}				
				return done; 
			}
			else {
				game.getController().invalidAction(game.getCurrentPlayer().getName(), "Costruzione non permessa");
				return false;
			}
		}
	}
}
