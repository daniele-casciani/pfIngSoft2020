package utils;

import client.Controller;

public class InvalidAction implements MessageSystem{

		private static final long serialVersionUID = 774232641682688790L;
		private String string;
		
		public String getError() {
			return string;
		}
		public InvalidAction(String error){
			string=error;
		}
		@Override
		public void accept(Controller clientLauncher) {
			clientLauncher.notify(this);
			
		}
}
