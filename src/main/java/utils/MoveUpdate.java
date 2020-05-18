package utils;
import client.ClientLauncher;
import client.Controller;


// int [x,y], int z, int[x,y], int z, altezza senza builder altrimenti da errore
public class MoveUpdate implements MessageToClient {

	private static final long serialVersionUID = 1L;
	
	private int[] movement;
	
	public MoveUpdate(int[] start,int height1, int[] end, int height2) {
		movement[0]=start[0];
		movement[1]=start[1];
		movement[2]=height1;
		movement[3]=end[0];
		movement[4]=end[1];
		movement[5]=height2;
	}
	
	public int[] getMovement(){
		return this.movement;
	}
	@Override
	public void accept(Controller clientLauncher) {
		clientLauncher.notify(this);
		
	}
}
