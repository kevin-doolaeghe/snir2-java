package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PacketCollector extends Thread {
	private Socket client;
	private boolean running;
	private List<String> messages;

	// ============================

	public PacketCollector(String ip, int port) {
		try {
			client = new Socket(ip, port);
			running = true;
			messages = new ArrayList<String>();

			start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ============================

	@Override
	public void run() {
		while (running) {
			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
				String message = reader.readLine();
				messages.add(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// ============================

	public void send(String message) {
		if (running) {
			new PacketTransmitter(client, message).start();
		}
	}

	public List<String> getMessages() {
		return messages;
	}

	// ============================

	public void update(String ip, int port) {
		try {
			running = false;
			client.close();
			client = new Socket(ip, port);
			running = true;

			start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void finalize() {
		try {
			running = false;
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
