package lobby;

import game.Player;

public interface ServerController {
	int choseCard(int[] deck, User player);
	int[] selectCard(int[] deck, User player);
	void loser(Player player);
	void winner(Player player);
	Object[] positionBuilder(String player);
	Object[] choseMovement(String player);
	Object[] whereBuild(String player);
	void invalidAction(String player, String message);
}
