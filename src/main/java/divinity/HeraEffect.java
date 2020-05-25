package divinity;

import tower.Level;

public class HeraEffect implements ActivePower {

	@Override
	public boolean build() {
		return false;
	}

	@Override
	public boolean move() {
		return false;
	}

	@Override
	public boolean win() {
		return true;
	}

	@Override
	public boolean actionLimitation(Level param1, Level param2) { // i need param2 only 
		
		int x = param2.getPosition()[0];
		int y = param2.getPosition()[1];
		
		if(x == 0 || x== 4) return true; // is a perimeter cell
		if(y == 0 || y== 4) return true; // is a perimeter cell 
		
		return false; // no perimeter cell
	}

}
