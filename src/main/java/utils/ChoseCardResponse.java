package utils;

	//getCardlist return array of divinity ID
public class ChoseCardResponse implements MessageToServer {

	private static final long serialVersionUID = 6081419984486093556L;
	private int card;
	
	public int getCard() {
		return card;
	}
	
	public ChoseCardResponse(int card){
		this.card= card;
	}
}
