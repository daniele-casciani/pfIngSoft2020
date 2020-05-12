package utils;

public class EffectResponse implements MessageToServer{

	private static final long serialVersionUID = 1L;
	private boolean bool;
	
	public EffectResponse(boolean bool){
		this.bool = bool;
	}
	
	public boolean getBool() {
		return bool;
	}
}
