package utils;

import client.ClientLauncher;

public class InvalidAction implements MessageSystem{
	
		private static final long serialVersionUID = 1L;
		private String string;
		
		public String getError() {
			return string;
		}
		public InvalidAction(String error){
			string=error;
		}
		@Override
		public void accept(ClientLauncher clientLauncher) {
			clientLauncher.notify(this);
			
		}
}
