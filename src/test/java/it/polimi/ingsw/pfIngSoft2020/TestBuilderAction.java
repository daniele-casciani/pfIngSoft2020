package it.polimi.ingsw.pfIngSoft2020;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Game.Game;
import lobby.User;
import tower.BuilderAction;
import tower.Cell;

class TestBuilderAction {
	Game game = new Game((new ArrayList<User>() {new User("ciao",localhost)}), null, null);
	BuilderAction build=new BuilderAction(game);
	
	@Test
	void buildDome() {
		
		build.buildDome(game.getMap().getCell(1,2));
		assertEquals(game.getMap().getCell(1,2).getHeight(),4);
	}

}
