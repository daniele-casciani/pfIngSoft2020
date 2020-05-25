package divinity;

import tower.Level;

public interface ActivePower {
	 boolean build();
	 boolean move();
	 boolean win();
	
	public boolean actionLimitation(Level param1, Level param2);
	// Return true if not feasible
}
