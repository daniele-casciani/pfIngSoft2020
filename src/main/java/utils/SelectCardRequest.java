package utils;

import java.util.ArrayList;

import client.ClientLauncher;

//getCardlist return array of divinity ID
public class SelectCardRequest implements MessageToClient {

	private static final long serialVersionUID = 1L;
	private ArrayList<Integer> cardlist;
	private int number;
	
	public ArrayList<Integer> getCardlist() {
		return cardlist;
	}
	
	public SelectCardRequest(ArrayList<Integer> cards, int number){
		this.cardlist = cards;
		this.number=number;
	}
	
	@Override
	public void accept(ClientLauncher clientLauncher) {
		clientLauncher.execute(this);
		
	}

	public int getNumber() {
		return number;
	}
}
