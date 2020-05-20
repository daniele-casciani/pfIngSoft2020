package utils;

//getBuilding return {x.builder,y.builder,x.bulding,y.building}
public class BuildResponse implements MessageToServer {

	private static final long serialVersionUID = 1L;
	private  int[] building;
	
	public int[] getBuilding(){
		return building;
	}
	
	public BuildResponse(int[] start, int[] end){
		building[0] = start[0];
		building[1] = start[1];
		building[2] = end[0];
		building[3] = end[1];	
	}
}
