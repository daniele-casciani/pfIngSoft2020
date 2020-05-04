package lobby;

import java.util.ArrayList;

import divinity.Divinity;
import game.Player;

public interface ServerController {
	Divinity choseCard(ArrayList<Divinity> deck, User player);
	ArrayList<Divinity> selectCard(ArrayList<Divinity> deck, User player);
	void loser(Player player);
	void winner(Player player);
	void positionBuilder();
	void choseMovement(String player);
	void wereBuild(String player);
	void invalidAction(String player);
}
