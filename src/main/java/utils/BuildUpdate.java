package utils;

import client.ClientLauncher;

public class BuildUpdate implements MessageToClient {

	private static final long serialVersionUID = 1L;
	
	private int[] building;
	
	public BuildUpdate(int[] whereBuild, int heigth) {
		building[0] = whereBuild[0]; //x
		building[1] = whereBuild[1]; //y
		building[2] = heigth;	//z= height of the new object
	}
		
	
	public int[] getPosition(){
		return building;
	}
	
	@Override
	public void accept(ClientLauncher clientLauncher) {
		clientLauncher.notify(this);
		
	}
}
