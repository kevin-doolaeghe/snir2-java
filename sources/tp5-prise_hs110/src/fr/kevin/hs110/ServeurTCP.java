package fr.kevin.hs110;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurTCP implements Runnable {
	private ServerSocket socketEcoute;
	private Socket socketDialogue;
	private BufferedReader inFromClient;
	private DataOutputStream outToClient;
	
	public ServeurTCP(int port) throws IOException {
		socketEcoute = new ServerSocket(port);
		inFromClient = new BufferedReader(new InputStreamReader(socketDialogue.getInputStream()));
		outToClient = new DataOutputStream(socketDialogue.getOutputStream());
		
	}
	
	public void finalize() throws IOException {
		outToClient.close();
		inFromClient.close();
		socketDialogue.close(); 
		socketEcoute.close();
	}
	
	@Override
	public void run() {
		try {
			socketDialogue = socketEcoute.accept();
			
			String messRecu = inFromClient.readLine();
			System.out.println("Reçu : " + messRecu);
			
			outToClient.writeBytes("Message reçu");
		} catch (IOException e) { e.printStackTrace(); }
	}
}
