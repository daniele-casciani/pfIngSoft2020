package utils;

import client.ClientLauncher;
import client.Controller;

public class NewBuilderUpdate implements MessageSystem{
	private static final long serialVersionUID = 1L;
	private int[] position;
	
	 public NewBuilderUpdate(int[] position){
			this.position=position;
	 }
	 
	 public  int[] getPosition(){
		 return position;
	 }

	 @Override
		public void accept(Controller clientLauncher) {
			clientLauncher.notify(this);
			
		}
}
