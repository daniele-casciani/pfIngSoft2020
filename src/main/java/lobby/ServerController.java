package lobby;

import java.util.ArrayList;

import game.Player;

public interface ServerController {
	
	void loser(Player player);
	void winner(Player player);
	int[] positionBuilder(String player);
	ArrayList<Integer> choseMovement(String player);
	ArrayList<Integer> whereBuild(String player);
	void invalidAction(String player, String message);
	
	void updateMovement(int[] position, int[] position2);	
	void updateBuild(int[] position, int[] position2);
	void updateNewBuilder(int[] position);
	
	ArrayList<Integer> selectCard(ArrayList<Integer> deck, User player);
	int choseCard(ArrayList<Integer> deck, User player);
}
