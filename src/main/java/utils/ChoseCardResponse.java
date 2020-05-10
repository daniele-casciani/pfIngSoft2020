package utils;

	//getCardlist return array of divinity ID
public class ChoseCardResponse implements MessageToServer {

	private static final long serialVersionUID = 1L;
	private int card;
	
	public int getCard() {
		return card;
	}
	
	ChoseCardResponse(int card){
		this.card= card;
	}
}
