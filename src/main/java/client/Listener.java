package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import utils.*;

public class Listener implements Runnable{
	ObjectInputStream input;
	ClientLauncher launcher;
	Message message;
	
	
	Listener(Socket socket,ClientLauncher launcher){
		this.launcher=launcher;
		try {
			input=new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while(true) {
			try {
				message = (Message) input.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				launcher.handle(message);
			}
			
		}
		
	}

}
