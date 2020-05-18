package utils;

import client.ClientLauncher;
import client.Controller;

public interface MessageToClient extends Message{

	void accept(Controller controller);

}
