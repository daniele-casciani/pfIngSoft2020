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
			case 7:
				System.out.println("Divinity 7 not supported");
				Thread.currentThread().interrupt();	
			case 8:
				this.god = new Minotaur(game);
				break;
			case 9:
				this.god = new Pan(game);
				break;
			case 10:
				this.god = new Prometheus(game);
				break;
			// from 11 to 19 not supported
			case 20:
				this.god = new Hera(game);
				break;	
			case 21:
				this.god = new Hestia(game);
				break;
			//from 22 to 26 not supported
			case 27:
				this.god = new Poseidon(game);
				break;
			case 28:
				System.out.println("Divinity 28 not supported");
				Thread.currentThread().interrupt();					
			case 29:
				this.god = new Triton(game);
				break;
			case 30:
				this.god = new Zeus(game);
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
