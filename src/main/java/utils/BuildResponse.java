package utils;

import java.util.ArrayList;

//getBuilding return {x.builder,y.builder,x.bulding,y.building}
public class BuildResponse implements MessageToServer {

	private static final long serialVersionUID = 1L;
	private  ArrayList<Integer> building;
	
	public ArrayList<Integer> getBuilding(){
		return building;
	}
	
	void buildResponse(int[] start, int[] end){
		for(int i = 0; i<2; i++) {
			building.add(start[i]);
		}
		for(int i = 0; i<2; i++) {
			building.add(end[i]);
		}	
	}
}
