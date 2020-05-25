package lobby;

import java.util.ArrayList;

import game.Player;

public interface ServerController {
	
	void loser(Player player);
	void winner(Player player);
	int[] positionBuilder(String player);
	int[] choseMovement(String player);
	int[] whereBuild(String player);
	void invalidAction(String player, String message);
	
	void updateBuild(int[] position, int height);
	void updateNewBuilder(int[] position, String name);
	void updateMovement(int[] position, int height1, int[] position2, int height2, String name);
	void updateSwitcBuilder(int[] position, int height1, String string, int[] is, int i, String name2);
	
	ArrayList<Integer> selectCard(ArrayList<Integer> deck, User player);
	int choseCard(ArrayList<Integer> deck, User player);
	boolean askEffect(String user, String text);
	
	public void sendDisconnection(String playerD);
	boolean askDivinityMode(String user, String text);

	
	
	
	
	
	
	
}
