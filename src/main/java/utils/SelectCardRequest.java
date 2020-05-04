package utils;

	//getCardlist return array of divinity ID
public class SelectCardRequest implements MessageToClient {

	private static final long serialVersionUID = 1L;
	private int[] cardlist;
	
	public int[] getCardlist() {
		return cardlist;
	}
	
	SelectCardRequest(int[] card){
		cardlist= card;
	}
}
