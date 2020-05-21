package utils;

//getMovement return {x.start,y.start,x.end,y.end}
public class MoveResponse implements MessageToServer{

	private static final long serialVersionUID = 1L;
	private  int[] movement;
	
	public int[] getMovement(){
		return movement;
	}
	
	public MoveResponse(int[] start, int[] end){
		movement = new int[] {start[0],start[1],end[0],end[1]};
	}
}
