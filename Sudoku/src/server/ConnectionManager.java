package server;

import java.net.Socket;

// Gere la connexion d'un client 

public class ConnectionManager implements Runnable {
	
	private Socket client;
	private SudokuServer server;
	
	public ConnectionManager(Socket client, SudokuServer server){
		this.client = client;
		this.server = server;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	
	

}
