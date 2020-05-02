package divinity;

import tower.Level;

public class AthenaEffect implements ActivePower{
	final private boolean build = false;
	final private boolean move = true;
	final private boolean win = false;

	@Override
	public boolean actionLimitation(Level param1, Level param2) {
		// TODO Auto-generated method stub
		return false;
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
