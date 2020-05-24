package it.polimi.ingsw.pfIngSoft2020;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.Test;
import divinity.AthenaEffect;
import tower.Cell;
import tower.Level;

public class AtenaEffectTest {

	
	@Test
	public void effectTest() {
		AthenaEffect atef= new AthenaEffect();
		Level start = new Cell(0,0);
		Level end = new Cell(0,0);
		assumeTrue(atef.actionLimitation(start,end));
	}
}
