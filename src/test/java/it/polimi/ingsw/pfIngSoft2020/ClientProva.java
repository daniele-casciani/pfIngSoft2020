package it.polimi.ingsw.pfIngSoft2020;


	import java.io.*;
	import java.net.*;
	import utils.*;
	 
	public class ClientProva extends Thread{
		
		public void run() {
			
			ObjectInputStream in = null;
			ObjectOutputStream out = null;
			Socket socket = null;
			Object message;
			
			try {
				// open a socket connection
				socket = new Socket("localhost", 4000);
				// Apre i canali I/O
				in = new ObjectInputStream(socket.getInputStream());
				out = new ObjectOutputStream(socket.getOutputStream());
				// Legge dal server
				message = in.readObject();
				sendresponse(message, out);
				out.close();
				in.close();
			}
				catch(Exception e) { System.out.println(e.getMessage());}
		}
	
		public void sendresponse(Object message, ObjectOutputStream out) throws IOException {
			if(message instanceof EffectRequest) {
				out.writeObject(new EffectResponse(true));
				out.flush();
			}
		}
	}

