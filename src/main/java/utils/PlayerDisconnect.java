package utils;

import client.Controller;

public class PlayerDisconnect implements MessageSystem {

	private static final long serialVersionUID = -1136551572793716013L;
	private String player;
	
	public PlayerDisconnect(String playerId) {
		this.player = playerId;
	}
	
	public String getPlayer() {
		return this.player;
	}

	@Override
	public void accept(Controller controller) {
		controller.notify(this);
	}

}
