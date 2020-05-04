package utils;

public class InvalidAction implements MessageSystem{
	
		private static final long serialVersionUID = 1L;
		private String string;
		
		public String getError() {
			return string;
		}
		public InvalidAction(String error){
			string=error;
		}
}
