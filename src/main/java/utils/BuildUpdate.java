package utils;

import client.Controller;

public class BuildUpdate implements MessageToClient {

	private static final long serialVersionUID = -1818718919740751126L;
	private int[] building;
	
	public BuildUpdate(int[] whereBuild, int heigth) {
	
		building =new int[]{whereBuild[0],whereBuild[1], heigth};
	}
		
	
	public int[] getPosition(){
		return building;
	}
	
	
	@Override
	public void accept(Controller clientLauncher) {
		clientLauncher.notify(this);
		
	}
}
