package old.server;

import java.io.BufferedReader;
import java.io.IOException;
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
			launch();
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
				clients.add(socket);
			} catch (Exception e) {
				System.err.println("Port already in use");
			}
		}
	}

	// ============================

	public void launch() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (running) {
					for (Socket client : clients) {
						try {
							BufferedReader reader = new BufferedReader(
									new InputStreamReader(
											client.getInputStream()));
							String message = reader.readLine();

							new PacketTransmitter(clients, message).start();

							if (!client.isConnected()) {
								clients.remove(client);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}

	public List<Socket> getClients() {
		return clients;
	}

	// ============================

	public void update(int port) {
		try {
			running = false;
			server.close();
			server = new ServerSocket(port);
			running = true;

			start();
			launch();
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
