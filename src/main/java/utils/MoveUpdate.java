package utils;
import client.Controller;


// int [x,y], int z, int[x,y], int z, altezza senza builder altrimenti da errore
public class MoveUpdate implements MessageToClient {

	private static final long serialVersionUID = 5154450566211399609L;
	private int[] movement;
	
	public MoveUpdate(int[] start,int height1, int[] end, int height2) {
		
		movement = new int[] {start[0],start[1], height1, end[0],end[1], height2};
	}
	
	public int[] getMovement(){
		return this.movement;
	}
	@Override
	public void accept(Controller clientLauncher) {
		clientLauncher.notify(this);
		
	}
}
