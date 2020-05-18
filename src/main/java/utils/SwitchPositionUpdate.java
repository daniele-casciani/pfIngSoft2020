package utils;

import client.ClientLauncher;
import client.Controller;

//int [x,y], int z, int[x,y], int z, altezza senza builder altrimenti da errore
//devono esserci lo scambio tra due builder altrimenti è ambiguo
public class SwitchPositionUpdate implements MessageSystem{

	private static final long serialVersionUID = 1L;
	
	private int[] positions;
	
	public int[] getPositions(){
		return positions;
	}
	
	public SwitchPositionUpdate(int[] p1,int height1, int[]p2,int height2) {
		positions[0]=p1[0];
		positions[1]=p1[1];
		positions[2]=height1;
		positions[3]=p2[0];
		positions[4]=p2[1];
		positions[5]=height2;
	}

	@Override
	public void accept(Controller clientLauncher) {
		clientLauncher.notify(this);
		
	}
}
