package lobby;

import java.util.ArrayList;

import divinity.Divinity;
import game.Player;

public interface ServerController {
	Divinity choseCard(ArrayList<Divinity> deck, User player);
	ArrayList<Divinity> selectCard(ArrayList<Divinity> deck, User player);
	void loser(Player player);
	void winner(Player player);
	Object[] positionBuilder(String player);
	Object[] choseMovement(String player);
	Object[] whereBuild(String player);
	void invalidAction(String player);
}
