package main;

import client.ClientLauncher;
import lobby.Server;
public class App 
{
    public static void main( String[] args )
    {
    	
    	new Server().start();
    	
    	ClientLauncher client = new ClientLauncher();
    	client.main();
    	
        System.out.println( "Hello World!" );
    }
}
