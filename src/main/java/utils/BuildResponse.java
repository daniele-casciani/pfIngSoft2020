package utils;

//getBuilding return {x.builder,y.builder,x.bulding,y.building}
public class BuildResponse implements MessageToServer {

	private static final long serialVersionUID = 1L;
	private  int[] building;
	
	public int[] getBuilding(){
		return building;
	}
	
	public BuildResponse(int[] start, int[] end){
	
		building =new int[]{start[0],start[1], end[0], end[1]};
	}
}
