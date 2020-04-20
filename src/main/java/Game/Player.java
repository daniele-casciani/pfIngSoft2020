package Game;
import divinity.Divinity;

public class Player {
	private String nickname;
	private Divinity god;
	
	public Player(String nickname, Divinity god) {
		this.nickname = nickname;
		this.god = god;
	}
	
	public String getName() {
		return nickname;
	}
	
	public Divinity getDivinity() {
		return god;
	}
}
