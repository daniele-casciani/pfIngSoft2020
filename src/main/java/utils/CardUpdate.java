package utils;

import client.Controller;

public class CardUpdate implements MessageUpdate{

	private static final long serialVersionUID = -3025783156564443885L;
	private String player;
	private int card;
	public String getPlayer() {
		return player;
	}
	public int getCard() {
		return card;
	}
	@Override
	public void accept(Controller controller) {
		controller.update(this);
		
	}
	CardUpdate(String player,int card){
		this.player=player;
		this.card=card;
	}
}
