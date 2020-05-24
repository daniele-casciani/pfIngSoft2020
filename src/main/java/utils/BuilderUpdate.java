package utils;

import client.Controller;

public class BuilderUpdate implements MessageUpdate{

	private static final long serialVersionUID = 5474537107765971824L;
	private int[] position;
	private String name;
	
	 public BuilderUpdate(int[] pos, String name){
		 position=new int[]{pos[0],pos[1] };
		 this.name=name;
	 }
	 
	 public  int[] getPosition(){
		 return position;
	 }

	 @Override
		public void accept(Controller clientLauncher) {
			clientLauncher.update(this);
			
		}

	public String getName() {
		return name;
	}
}
