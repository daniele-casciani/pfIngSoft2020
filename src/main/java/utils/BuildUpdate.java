package utils;

import client.ClientLauncher;

// int[] return x y z

public class BuildUpdate implements MessageToClient {

	private static final long serialVersionUID = 1L;
	
	private int[] building;
	
	public BuildUpdate(int[] whereBuild) {
		building=whereBuild;
		}
		
	
	public int[] getPosition(){
		return building;
	}
	
	@Override
	public void accept(ClientLauncher clientLauncher) {
		clientLauncher.notify(this);
		
	}
}
