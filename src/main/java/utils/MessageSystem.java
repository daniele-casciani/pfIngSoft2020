package utils;

import client.ClientLauncher;
import client.Controller;

public interface MessageSystem extends Message{

	void accept(Controller controller);

}
