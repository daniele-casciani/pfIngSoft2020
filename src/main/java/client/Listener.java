package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import utils.*;

public class Listener implements Runnable{
	ObjectInputStream input;
	Listener(Socket socket){
		try {
			input=new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(true) {
			try {
				MessageToClient message = (MessageToClient) input.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

}
