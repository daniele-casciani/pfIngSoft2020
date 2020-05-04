package utils;

	//getCardlist return array of divinity ID
public class ChoseCardResponse implements MessageToServer {

	private static final long serialVersionUID = 1L;
	private int[] cardlist;
	
	public int[] getCardlist() {
		return cardlist;
	}
	
	ChoseCardResponse(int[] card){
		cardlist= card;
	}
}
