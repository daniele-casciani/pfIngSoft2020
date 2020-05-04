package utils;

	//getCard return selected divinity ID
public class SelectCardResponse implements MessageToServer {

	private static final long serialVersionUID = 1L;
	private int card;
	
	public int getCard() {
		return card;
	}
	
	SelectCardResponse(int card){
		this.card= card;
	}
}
