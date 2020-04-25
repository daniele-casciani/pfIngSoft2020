package tower;
import Game.*;

 public class BuilderAction implements Action {
	private Game game;
	
	public BuilderAction(Game game) {
		this.game=game;
	}
	
	@Override
	public void movement(Level start, Level end) {
		Level newStart=((Builder)((Floor) start)).move(end);
		game.getMap().setCell(newStart);
		game.getMap().setCell(end);
		
	}
	
	@Override
	public void buildDome(Level level) {
		Dome newTower= new Dome(level);
		game.getMap().setCell((Level)(Floor)(newTower));

	}
	
	@Override
	public void buildTower(Level level) {

		if (level.getClass()==Cell.class){
			game.getMap().setCell( new TowerL1(level));
		}
		else if (level.getClass()==TowerL1.class){
			game.getMap().setCell( new TowerL2(level));
		}
		else if (level.getClass()==TowerL2.class){
			game.getMap().setCell( new TowerL3(level));
		}
	}

	@Override
	public void newBuilder(Level level,String name) {
		Builder builder=new Builder(level, name);
		game.getMap().setCell(builder);
	}

	@Override
	public  void killBuilder(Level dead) {
		Level cleanfloor=((Builder)((Floor) dead)).kill();
		game.getMap().setCell(cleanfloor);
	}
	
	@Override
	public int getHeight(Level level) {
		return level.getHeight();
	}

	@Override
	public String builderName(Level builder) {
		return ((Builder)(Floor)builder).getName();
		
	}
	

}
