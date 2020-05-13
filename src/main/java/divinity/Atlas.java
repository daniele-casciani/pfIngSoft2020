package divinity;

import game.Game;
import tower.BuilderAction;
import tower.Level;

public final class Atlas extends Divinity {
	final private  int cardID=4;
	
	public Atlas(Game game) {
		super(game);
	}
	
	@Override
	public void round() {
		
		startRound();
		
		lose();

		tryer(new Move());
		
		tryer(new BuildAtlas());
		
		endRound();
	}
	
	class BuildAtlas extends Build {
		@Override
		public boolean execute(Object start, Object end) {
			Level builderCell = (Level)start;
			Level whereBuild = (Level)end;
			BuilderAction nowbuild = new BuilderAction(game);
			
			if(game.getController().askEffect(game.getCurrentPlayer().getName())) {
				if(isPossibleBuild( builderCell, whereBuild ) && builderCell.getHeight() == -1 && nowbuild.builderName(builderCell).equals(game.getCurrentPlayer().getName()) ) {
					
					nowbuild.buildDome(whereBuild);
					game.getController().updateBuild(whereBuild.getPosition(), 4); 
					return true;
				}else {
					game.getController().invalidAction(game.getCurrentPlayer().getName(), "Invalid Build");
					return false;
				}
			}
			else return super.execute(start, end);
		}
	}
}
