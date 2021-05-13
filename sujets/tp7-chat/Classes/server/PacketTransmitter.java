package server;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.List;

public class PacketTransmitter extends Thread {
	private List<Socket> clients;
	private String message;

	// ============================

	public PacketTransmitter(List<Socket> clients, String message) {
		this.clients = clients;
		this.message = message;
	}

	// ============================

	@Override
	public void run() {
		if (!message.isEmpty()) {
			for (Socket client : clients) {
				try {
					DataOutputStream writer = new DataOutputStream(client.getOutputStream());
					writer.writeBytes(message + "\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
