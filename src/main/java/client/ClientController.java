package client;

import java.io.IOException;
import java.util.ArrayList;

import utils.Loser;
import utils.Winner;

public interface ClientController{
	
	void setText(String string);

	void addConstructor(int x, int y);

	void construction(int x, int y, int z);

	void endLoser(Loser loser);

	void endWinner(Winner winner);

	void login() throws IOException;

	void playerNumber();

	void catchDrag();

	void catchPosition();

	ArrayList<Integer> catchSelection(ArrayList<Integer> cardlist, int i) throws IOException;

	void boolChoice(String string) throws IOException;
}
