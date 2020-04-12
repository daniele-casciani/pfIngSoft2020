package Game;


public class Player {
	private String nickname;
	private String god;
	
	public Player(String nickname, String god) {
		this.nickname = nickname;
		this.god = god;
	}
	
	public String getName() {
		return nickname;
	}
	
	public String getDivinity() {
		return god;
	}
}
