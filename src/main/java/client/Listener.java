package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import utils.*;

public class Listener implements Runnable{
	ObjectInputStream input;
	ClientLauncher launcher;
	Message message;
	Socket socket;
	
	
	Listener(Socket socket,ClientLauncher launcher){
		this.launcher=launcher;
		this.socket = socket;
		try {
			input=new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void  run() {
		Thread.currentThread().setName("listener");
		try {
		while(true) {
			try {
				
				message = (Message) input.readObject();
				launcher.handle(message);
				
			} catch (ClassNotFoundException e) {
				System.out.print(" start message cast error ");
				e.printStackTrace();
				System.out.print(" end message cast error ");
			}
			
		}
		
	
		}catch(IOException e) {
			System.out.print(" start socket error ");
			e.printStackTrace();
			System.out.print(" end socket error ");
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				System.out.print(" socket close error ");
			}
		}
	}

}
