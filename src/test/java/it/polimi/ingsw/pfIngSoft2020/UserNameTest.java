package it.polimi.ingsw.pfIngSoft2020;



import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import lobby.MainServer;
import utils.InvalidAction;
import utils.Message;
import utils.PlayerNumberRequest;
import utils.PlayerNumberResponse;
import utils.UserNameRequest;
import utils.UserNameResponse;

public class UserNameTest {

	static Socket socket;
	static ObjectOutputStream output;
	static ObjectInputStream input;
	static MainServer server = new MainServer();
	static Message message;
	
	@BeforeAll
	static void initialize(){
		server.start();
		try {
			socket = new Socket("127.0.0.1",51344);
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {}
		try {
			message = (Message)input.readObject();
		} catch (ClassNotFoundException | IOException e) {}
	}
	
	@Test	
	public void NameTest() {
		assertTrue(message instanceof UserNameRequest);
		try {
			output.writeObject(new UserNameResponse("player0"));
			message = (Message) input.readObject();
		} catch (IOException | ClassNotFoundException e) {}
		assertTrue(message instanceof PlayerNumberRequest);
	}
	
}

