package utils;
import java.util.ArrayList;

public class MoveUpdate implements MessageToClient {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<Integer> movement;
	
	public MoveUpdate(int[] start, int[] end) {
		for(int i = 0; i<2; i++) {
			movement.add(start[i]);
		}
		for(int i = 0; i<2; i++) {
			movement.add(end[i]);
		}
	}
	
	public ArrayList<Integer> getMovement(){
		return this.movement;
	}
	
}
