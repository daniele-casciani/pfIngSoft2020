package utils;

import client.Controller;

public class NewBuilderUpdate implements MessageSystem{

	private static final long serialVersionUID = 5474537107765971824L;
	private int[] position;
	
	 public NewBuilderUpdate(int[] pos){
		 position=new int[]{pos[0],pos[1] };
	 }
	 
	 public  int[] getPosition(){
		 return position;
	 }

	 @Override
		public void accept(Controller clientLauncher) {
			clientLauncher.notify(this);
			
		}
}
