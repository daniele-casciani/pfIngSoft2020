package utils;

	//getPosition return {x.builder,y.builder}
public class BuilderResponse implements MessageToServer {

	private static final long serialVersionUID = 1L;
	private int[] position;
	
	public int[] getPosition() {
		return position;
	}
	
	public BuilderResponse(int[] position){
		this.position= position;
	}
}
