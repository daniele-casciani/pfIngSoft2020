package utils;

import java.util.ArrayList;

//getCardlist return array of divinity ID
public class SelectCardRequest implements MessageToClient {

	private static final long serialVersionUID = 1L;
	private ArrayList<Integer> cardlist;
	
	public ArrayList<Integer> getCardlist() {
		return cardlist;
	}
	
	public SelectCardRequest(ArrayList<Integer> cards){
		this.cardlist = cards;
	}
}
