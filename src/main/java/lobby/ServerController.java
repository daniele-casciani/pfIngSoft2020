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
	
	void updateBuild(int[] position, int height);
	void updateNewBuilder(int[] position);
	void updateMovement(int[] position, int height1, int[] position2, int height2);
	void updateSwitcBuilder(int[] position, int height1, int[] position2, int height2);
	
	ArrayList<Integer> selectCard(ArrayList<Integer> deck, User player);
	int choseCard(ArrayList<Integer> deck, User player);
	boolean askEffect(String user);
	
	
	
	
	
}
