package it.polimi.ingsw.pfIngSoft2020;

import static org.junit.Assert.*;
import lobby.*;

import org.junit.Test;

public class LobbyTest {
	
	
	@Test
	public void test() throws Exception {
		Thread server = new ServerProva();
		Thread client = new ClientProva();
		server.start();
		client.start();
		Lobby lobby = new Lobby(server.getArray());
	}

}
