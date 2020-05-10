package utils;

import java.util.ArrayList;

//getCard return selected divinity ID
public class SelectCardResponse implements MessageToServer {

	private static final long serialVersionUID = 1L;
	private ArrayList<Integer> cards;
	
	public ArrayList<Integer> getCard() {
		return cards;
	}
	
	public SelectCardResponse(ArrayList<Integer> card){
		for(Integer x : card) {
			cards.add(x);
		}
	}
}
