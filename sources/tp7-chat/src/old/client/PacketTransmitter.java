package old.client;

import java.io.DataOutputStream;
import java.net.Socket;

public class PacketTransmitter extends Thread {
	private Socket client;
	private String message;

	// ============================

	public PacketTransmitter(Socket client, String message) {
		this.client = client;
		this.message = message;
	}

	// ============================

	@Override
	public void run() {
		if (!message.isEmpty()) {
			try {
				DataOutputStream writer = new DataOutputStream(
						client.getOutputStream());
				writer.writeBytes(message + "\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
