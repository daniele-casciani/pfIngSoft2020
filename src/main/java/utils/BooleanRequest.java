package utils;

import client.Controller;

public class BooleanRequest implements MessageToClient {
	private static final long serialVersionUID = 2326625599851146546L;
	private String str;
	
	public BooleanRequest(String text){
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
