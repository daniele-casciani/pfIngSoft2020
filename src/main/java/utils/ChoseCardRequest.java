package utils;

import java.util.ArrayList;

import client.ClientLauncher;
import client.Controller;

//getCardlist return array of divinity ID	
public class ChoseCardRequest implements MessageToClient {

	private static final long serialVersionUID = 1L;
	private ArrayList<Integer> cards;
	
	public ArrayList<Integer> getCardlist() {
		return cards;
	}
	
	public ChoseCardRequest(ArrayList<Integer> card){
		this.cards= card;
	}
	
	@Override
	public void accept(Controller clientLauncher) {
		clientLauncher.execute(this);
		
	}
}

