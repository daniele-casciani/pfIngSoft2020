package client;

import utils.Message;

public interface Client {
	void sendMessage();
	void handle(Message message);
}
