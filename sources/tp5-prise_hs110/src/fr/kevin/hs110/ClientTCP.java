package fr.kevin.hs110;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientTCP implements Runnable {
	private Socket clientSocket;
	private DataOutputStream outToServer;
	private BufferedReader inFromServer;
	
	public ClientTCP(String ip, int port) throws IOException {
		clientSocket = new Socket(ip, port);
		outToServer = new DataOutputStream(clientSocket.getOutputStream());
		inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}
	
	public void finalize() throws IOException {
		outToServer.close();
		inFromServer.close();
		clientSocket.close();
	}
	
	@Override
	public void run() {
		try {
			outToServer.writeBytes("Bonjour \n");
			
			String reponse = "";
			while(inFromServer.read() != -1) reponse += inFromServer.readLine();
			
			System.out.println("Réponse : " + reponse);
		} catch (IOException e) { e.printStackTrace(); }
	}
}
