package utils;

import client.Controller;

//int [x,y], int z, int[x,y], int z, altezza senza builder altrimenti da errore
//devono esserci lo scambio tra due builder altrimenti è ambiguo
public class SwitchPositionUpdate implements MessageSystem{

	private static final long serialVersionUID = -1100375534004296721L;
	private int[] positions;
	private String name1;
	private String name2;
	
	public int[] getPositions(){
		return positions;
	}
	
	public SwitchPositionUpdate(int[] p1,int height1,String name1, int[]p2,int height2,String name2) {
		positions = new int[] {p1[0],p1[1], height1,p2[0],p2[1], height2};
		this.name1=name1;
		this.name2=name2;
	}

	@Override
	public void accept(Controller clientLauncher) {
		clientLauncher.notify(this);
		
	}

	public String getName1() {
		return name1;
	}

	public String getName2() {
		return name2;
	}
}
