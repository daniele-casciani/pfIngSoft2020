package utils;
import java.util.ArrayList;

import client.ClientLauncher;

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
	@Override
	public void accept(ClientLauncher clientLauncher) {
		clientLauncher.notify(this);
		
	}
}
