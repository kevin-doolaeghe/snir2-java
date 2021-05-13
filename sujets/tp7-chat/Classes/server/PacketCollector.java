package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PacketCollector extends Thread {
	private ServerSocket server;
	private boolean running;
	private List<Socket> clients;

	// ============================

	public PacketCollector(int port) {
		try {
			server = new ServerSocket(port);
			running = true;
			clients = new ArrayList<Socket>();

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
				Socket socket = server.accept();
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String message = reader.readLine();

				clients.add(socket);
				new PacketTransmitter(clients, message).start();

				for (Socket client : clients) {
					if (!client.isConnected()) {
						clients.remove(client);
					}
				}
			} catch (Exception e) {
				System.err.println("Port already in use");
			}
		}
	}

	// ============================

	public void update(int port) {
		try {
			running = false;
			server.close();
			server = new ServerSocket(port);
			running = true;

			start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void finalize() {
		try {
			running = false;
			server.close();

			for (Socket client : clients) {
				clients.remove(client);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
