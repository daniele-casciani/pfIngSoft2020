package client;

import utils.Message;

public interface Client {
	void sendMessage(Message message);
	void handle(Message message);
}
