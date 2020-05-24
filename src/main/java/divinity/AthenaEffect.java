package divinity;

import tower.Level;

public class AthenaEffect implements ActivePower{
	final private boolean build = false;
	final private boolean move = true;
	final private boolean win = false;

	@Override
	public boolean actionLimitation(Level start, Level end) {
		if(end.getHeight() - start.getHeight() == 1) return true;
	
		else return false;
	}

	@Override
	public boolean build() {
		return build;
	}

	@Override
	public boolean move() {
		return move;
	}

	@Override
	public boolean win() {
		return win;
	}

}
