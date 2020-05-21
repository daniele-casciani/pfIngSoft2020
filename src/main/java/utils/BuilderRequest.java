package utils;
import client.Controller;

public class BuilderRequest implements MessageToClient {

	private static final long serialVersionUID = -7284297492118839087L;

	@Override
	public void accept(Controller clientLauncher) {
		clientLauncher.execute(this);
		
	}
	
}
