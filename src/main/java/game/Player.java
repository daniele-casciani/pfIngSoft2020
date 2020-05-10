package game;
import divinity.*;

public class Player {
	private String nickname;
	private Divinity god;
	
	public Player(String nickname, int god, Game game) {
		this.nickname = nickname;
		switch(god) {
			case 0:
				this.god = new Divinity(game);
				break;
			case 1:
				this.god = new Apollo(game);
				break;
			case 2:
				this.god = new Artemis(game);
				break;
			case 3:
				this.god = new Athena(game);
				break;
			case 4:
				this.god = new Atlas(game);
				break;
			case 5:
				this.god = new Demeter(game);
				break;
			case 6:
				this.god = new Hephaestus(game);
				break;
			case 8:
				this.god = new Minotaur(game);
				break;
			case 9:
				this.god = new Pan(game);
				break;
			case 10:
				this.god = new Prometheus(game);
				break;
		}
	}
	
	public String getName() {
		return nickname;
	}
	
	public Divinity getDivinity() {
		return god;
	}
}
