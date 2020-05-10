package utils;

import java.util.ArrayList;

public class BuildUpdate implements MessageToClient {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<Integer> building;
	
	public BuildUpdate(int[] builderCell, int[] whereBuild) {
		for(int i = 0; i<2; i++) {
			building.add(builderCell[i]);
		}
		for(int i = 0; i<2; i++) {
			building.add(whereBuild[i]);
		}
		
	}
	
	public ArrayList<Integer> getMovement(){
		return this.building;
	}
}
