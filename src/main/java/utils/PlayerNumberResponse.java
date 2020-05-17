package utils;

public class PlayerNumberResponse implements MessageToServer {

	private static final long serialVersionUID = 1L;
	private int number;
	
	public int getNumber() {
		return number;
	}
	public PlayerNumberResponse(int number){
		this.number=number;
	}

}
