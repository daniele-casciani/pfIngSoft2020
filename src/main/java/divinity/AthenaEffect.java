package divinity;

import tower.Level;

public class AthenaEffect implements ActivePower{
	final private boolean build = false;
	final private boolean move = true;
	final private boolean win = false;

	@Override
	public boolean actionLimitation(Level start, Level end) {
		if(start.getHeight()==0 && end.getHeight()==1) return true;
		if(start.getHeight()==1 && end.getHeight()==2) return true;
		if(start.getHeight()==2 && end.getHeight()==3) return true;
	
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
