package tower;
import Game.Map;

class BuilderAction implements Action {

	@Override
	public void movement(Level start, Level end) {
		Level newStart=((Builder)((Floor) start)).move(end);
		setCell(newStart);
		setCell(end);
		
	}
	
	@Override
	public void buildDome(Level level) {
		Dome newTower= new Dome(level);
		setCell(newTower);

	}
	
	@Override
	public void buildTower(Level level) {
		// TODO Auto-generated method stub

	}

	@Override
	public void newBuilder(Level level,String color) {
		Builder builder=new Builder(level, color);
		setCell(builder);
	}

	@Override
	public  void killBuilder(Level dead) {
		Level cleanfloor=((Builder)((Floor) dead)).kill();
		setCell(cleanfloor);
	}
	

}
