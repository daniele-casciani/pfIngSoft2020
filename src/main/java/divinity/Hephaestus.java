package divinity;

import game.Game;
import tower.BuilderAction;
import tower.Level;

public final class Hephaestus extends Divinity {

	final private  int cardID = 6;
	
	public Hephaestus(Game game) {
		super(game);
	}
	
	@Override
	public void round() {
		
		startRound();
		
		lose();

		tryer(new Move());
		
		tryer(new BuildHephaestus());
		
		endRound();
	}
	
	class BuildHephaestus extends Build {
		@Override
		public boolean execute(Object start, Object end) {
			
			Level builderCell = (Level)start;
			Level whereBuild = (Level)end;
			boolean done = false;
			BuilderAction nowbuild = new BuilderAction(game);
			
			if(isPossibleBuild(builderCell, whereBuild) && builderCell.getHeight() == -1 && nowbuild.builderName(builderCell).equals(game.getCurrentPlayer().getName())) {
				
				if(whereBuild.getHeight()==3) {
					nowbuild.buildDome(whereBuild);
					game.getController().updateBuild(whereBuild.getPosition(), whereBuild.getHeight());
					done = true;
				}
				
				else {
					nowbuild.buildTower(whereBuild);
					game.getController().updateBuild( whereBuild.getPosition(), whereBuild.getHeight());
					done = true;
				}
				// here done = true and i ask for the active power of the divinity
				if(game.getController().askEffect(game.getCurrentPlayer().getName(),"attivate potere?")) { // the activation of the effect does not imply a construction
					if(whereBuild.getHeight()<3) {		// i can't add a dome, It is important to note that
														// here whereBuild will have a height increased by 1 compared to the beginning
						nowbuild.buildTower(whereBuild);
						game.getController().updateBuild( whereBuild.getPosition(), whereBuild.getHeight());
					}
				}				
				return done; 
			}
			else {
				game.getController().invalidAction(game.getCurrentPlayer().getName(), "Invalid Build");
				return false;
			}
		}
	}
}
