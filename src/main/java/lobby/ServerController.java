package lobby;

import game.Player;

public interface ServerController {
	int choseCard(int[] deck, User player);
	int[] selectCard(int[] deck, User player);
	void loser(Player player);
	void winner(Player player);
	int[] positionBuilder(String player);
	int[] choseMovement(String player);
	int[] whereBuild(String player);
	void invalidAction(String player, String message);
}
