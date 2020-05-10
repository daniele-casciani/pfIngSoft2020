package utils;

import java.util.ArrayList;

//getMovement return {x.start,y.start,x.end,y.end}
public class MoveResponse implements MessageToServer{

	private static final long serialVersionUID = 1L;
	private  ArrayList<Integer> movement;
	
	public ArrayList<Integer> getMovement(){
		return movement;
	}
	
	void movementResponse(int[] start, int[] end){
		for(int i = 0; i<2; i++) {
			movement.add(start[i]);
		}
		for(int i = 0; i<2; i++) {
			movement.add(end[i]);
		}	
	}
}
