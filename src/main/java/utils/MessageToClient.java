package utils;

import client.Controller;

public interface MessageToClient extends Message{

	void accept(Controller controller);

}
