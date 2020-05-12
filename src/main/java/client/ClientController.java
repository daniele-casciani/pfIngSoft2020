package client;

import java.util.ArrayList;

import utils.Loser;
import utils.Winner;

public interface ClientController extends Runnable{
	
	void setText(String string);

	void addConstructor(int x, int y);

	void construction(int x, int y, int z);

	void endLoser(Loser loser);

	void endWinner(Winner winner);

	void login();

	void playerNumber();

	void catchDrag();

	void catchPosition();

	void catchSelection(ArrayList<Integer> cardlist, int i);

	void boolChoice(String string);
}
