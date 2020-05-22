package utils;

import client.Controller;

public class NewBuilderUpdate implements MessageSystem{

	private static final long serialVersionUID = 5474537107765971824L;
	private int[] position;
	private String name;
	
	 public NewBuilderUpdate(int[] pos, String name){
		 position=new int[]{pos[0],pos[1] };
		 this.name=name;
	 }
	 
	 public  int[] getPosition(){
		 return position;
	 }

	 @Override
		public void accept(Controller clientLauncher) {
			clientLauncher.notify(this);
			
		}

	public String getName() {
		return name;
	}
}
