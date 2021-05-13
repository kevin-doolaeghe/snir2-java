package fr.kevin.hs110;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ServeurUDP implements Runnable {
	private int port;
	
	private DatagramSocket socket;
	private DatagramPacket data;
	
	public ServeurUDP(int port) throws IOException {
		this.port = port;
		socket = new DatagramSocket(port);
	}
	
	public void finalize() throws IOException {
		socket.close();
	}
	
	@Override
	public void run() {
		try {
			byte buffer[] = new byte[1000];
			DatagramPacket data = new DatagramPacket(buffer,buffer.length);
			
			socket.receive(data);
			System.out.println(data.getAddress());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
