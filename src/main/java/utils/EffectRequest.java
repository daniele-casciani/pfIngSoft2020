package utils;

import client.Controller;

public class EffectRequest implements MessageToClient {
	
	private String str;
	private static final long serialVersionUID = 1L;
	
	public EffectRequest(String text){
		this.str=text;
	}

	@Override
	public void accept(Controller clientLauncher) {
		clientLauncher.execute(this);
		
	}

	public String getStr() {
		return str;
	}

}
