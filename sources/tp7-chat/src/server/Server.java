package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	protected ServerSocket server;
	protected List<Socket> clients;
	protected boolean running;

	protected String lastMessage;
	protected List<String> discussion;

	public Server(ServerSocket server) {
		this.server = server;
		clients = new ArrayList<Socket>();
		running = true;

		lastMessage = "";
		discussion = new ArrayList<String>();

		connect();
		read();
	}

	protected void connect() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (running) {
					try {
						Socket socket = server.accept();
						clients.add(socket);

						Thread.sleep(50);
					} catch (Exception e) {
						System.err.println("Port already in use");
					}
				}
			}
		}).start();
	}

	protected void read() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (running) {
					for (Socket client : clients) {
						try {
							BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
							lastMessage = reader.readLine();

							discussion.add(lastMessage);
							write(lastMessage);

							if (!client.isConnected()) {
								clients.remove(client);
							}

							Thread.sleep(50);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();
	}

	protected void write(String message) {
		if (!message.isEmpty()) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (Socket client : clients) {
						try {
							DataOutputStream writer = new DataOutputStream(client.getOutputStream());
							writer.writeBytes(message + "\n");
							
							Thread.sleep(10);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}).start();
		}
	}

	public void edit(ServerSocket server) {
		try {
			this.server.close();
			this.server = server;
		} catch (Exception e) {
			System.err.println("Port already in use");
		}
	}

	public void terminate() {
		try {
			running = false;
			
			for (Socket client : clients) {
				client.close();
				clients.remove(client);
			}
			
			server.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ServerSocket getServer() {
		return server;
	}

	public List<Socket> getClients() {
		return clients;
	}

	public String getLastMessage() {
		String message = lastMessage;
		lastMessage = "";

		return message;
	}

	public List<String> getDiscussion() {
		return discussion;
	}
}
