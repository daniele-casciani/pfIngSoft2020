package utils;

//getMovement return {x.start,y.start,x.end,y.end}
public class MoveResponse implements MessageToServer{

	private static final long serialVersionUID = 1L;
	private  int[] movement;
	
	public int[] getMovement(){
		return movement;
	}
	
	public MoveResponse(int[] start, int[] end){
		movement[0] = start[0];
		movement[1] = start[1];
		movement[2] = end[0];
		movement[3] = end[1];
	}
}
