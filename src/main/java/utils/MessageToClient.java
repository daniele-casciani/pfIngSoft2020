package utils;

import client.ClientLauncher;

public interface MessageToClient extends Message{

	void accept(ClientLauncher clientLauncher);

}
