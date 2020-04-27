package lobby;

import java.util.ArrayList;

import Game.Player;
import divinity.Divinity;

public interface ServerController {
	Divinity choseCard(ArrayList<Divinity> deck, User player);
	ArrayList<Divinity> selectCard(ArrayList<Divinity> deck, User player);
	void choseMovement();
	void wereBuild();
	void invalidAction();
	void loser(Player player);
	void winner(Player player);
	void positionBuilder();
}
