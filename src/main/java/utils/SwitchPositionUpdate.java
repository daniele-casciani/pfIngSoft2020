package utils;

import java.util.ArrayList;

public class SwitchPositionUpdate implements MessageSystem{

	private static final long serialVersionUID = 1L;
	
	private ArrayList<Integer> positions;
	
	public ArrayList<Integer> getPositions(){
		return positions;
	}
	
	public SwitchPositionUpdate(int[] p1, int[]p2) {
		for(int i = 0; i<2; i++) {
			positions.add(p1[i]);
		}
		for(int i = 0; i<2; i++) {
			positions.add(p2[i]);
		}
	}
}
