package client;

import utils.*;

public interface ClientController{
	
	void sendMessage(Message message);
	void handle(Message message);
	
}
