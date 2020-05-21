package utils;

	//getPosition return {x.builder,y.builder}
public class BuilderResponse implements MessageToServer {

	private static final long serialVersionUID = -2814300533086819128L;
	private int[] position;
	
	public int[] getPosition() {
		return position;
	}
	
	public BuilderResponse(int[] pos){
		position=new int[]{pos[0],pos[1] };
	}
}
