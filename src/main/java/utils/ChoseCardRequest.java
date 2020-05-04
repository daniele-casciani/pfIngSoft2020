package utils;

	//getCardlist return array of divinity ID	
public class ChoseCardRequest implements MessageToClient {

	private static final long serialVersionUID = 1L;
	private int[] cardlist;
	
	public int[] getCardlist() {
		return cardlist;
	}
	
	ChoseCardRequest(int[] card){
		cardlist= card;
	}
}
