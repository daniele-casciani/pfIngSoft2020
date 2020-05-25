package divinity;

import game.Game;
import tower.BuilderAction;
import tower.Level;

public class Poseidon extends Divinity{
	// final private int cardID = 27;
	private int count;
	
	public Poseidon (Game game) {
		super(game);
	}
	private int getCount() {
		return this.count;
	}
	private void setCount(int n) {
		this.count = n;
	}
	@Override
	public void round() {
		startRound();
		if(lose()) {
			game.loseGame();
		}else {
			tryer(new Move());
			tryer(new Build());
			endRound();
		}
	}
	
	@Override
	public void endRound() {
		
		while (getCount() != 0 && game.getController().askEffect(game.getCurrentPlayer().getName(), "Vuoi attivare il potere?")) {
			tryer(new BuildPoseidon());
		}
	}
	
	class BuildPoseidon extends Build {

		@Override
		public boolean execute(Object start, Object end) {

			Level builderCell = (Level)start;
			Level whereBuild = (Level)end;
			
			BuilderAction buildAction = new BuilderAction(game);

			if(buildAction.builderName(builderCell).equals(game.getCurrentPlayer().getName())) { // is the builder of the player
				Level Bmoved = game.getMap().getCell(buildselect[0], buildselect[1]);
				if(Bmoved != builderCell) {
					// here i have the unmoved builder 
					if(buildAction.getLUnderB(builderCell).getHeight() == 0) {
						// active the power
						if(isPossibleBuild(builderCell, whereBuild)) {
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
							
							setCount(getCount()-1);
							return true;
						
						}else {
							game.getController().invalidAction(game.getCurrentPlayer().getName(), "Mossa non valida");
							return false;
						}
						
					}else {
						setCount(0); // to exit from the endround
						return true; // to exit from the tryer
					}
					
				}else {
					game.getController().invalidAction(game.getCurrentPlayer().getName(), "Mossa non valida");
					return false;
				}
				
			}else {
				game.getController().invalidAction(game.getCurrentPlayer().getName(), "Mossa non valida");
				return false;
			}
		}
	}
}
