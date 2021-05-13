package fr.kevin.hs110;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientUDP implements Runnable {
	private InetAddress serveur;
	private int port;
	private int length;
	private String message;
	
	private DatagramSocket socket;
	private DatagramPacket dataSent;
	private DatagramPacket dataRecieved;
	
	public ClientUDP(String ip, int port, String message) throws IOException {
		this.serveur = InetAddress.getByName(ip);
		this.port = port;
		this.message = message;
		this.length = message.length(); 
		
		socket = new DatagramSocket();
	}
	
	public void finalize() throws IOException {
		socket.close();
	}
	
	@Override
	public void run() {
		try {
			byte buffer[] = message.getBytes();
			dataSent = new DatagramPacket(buffer, buffer.length, serveur, port);
			socket.send(dataSent);
			
			dataRecieved = new DatagramPacket(new byte[length],length);
			socket.receive(dataRecieved);
			
			System.out.println("Data recieved : " + new String(dataRecieved.getData()));
			System.out.println("From : " + dataRecieved.getAddress() + ":" + dataRecieved.getPort());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
