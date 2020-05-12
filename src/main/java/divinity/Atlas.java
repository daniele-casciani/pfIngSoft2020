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
			
			if(game.getController().askEffect(game.getCurrentPlayer().getName())) {
				if(isPossibleBuild( builderCell, whereBuild )) {
					BuilderAction nowbuild = new BuilderAction(game);
					nowbuild.buildDome(whereBuild);
					game.getController().updateBuild(builderCell.getPosition(), whereBuild.getPosition());
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
