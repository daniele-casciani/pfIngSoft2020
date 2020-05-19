package utils;

public class BooleanResponse implements MessageToServer{

	private static final long serialVersionUID = 1L;
	private boolean bool;
	
	public BooleanResponse(boolean bool){
		this.bool = bool;
	}
	
	public boolean getBool() {
		return bool;
	}
}
