package Game;

public class Game {
	private int participants;
	private Player[] playerList = new Player[participants];
	private Map map = new Map();
	
	public Game(int participants){
		this.participants = participants;
		
		for (int i = 0; i<participants; i++) {
			playerList[i] = new Player(null, null);
			// come inserire i nomi?
		}
		
	}
}
