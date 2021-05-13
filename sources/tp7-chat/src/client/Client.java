package client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
	protected Socket client;
	protected boolean running;

	protected String lastMessage;
	protected List<String> discussion;

	public Client(Socket client) {
		this.client = client;
		running = true;

		lastMessage = "";
		discussion = new ArrayList<String>();

		read();
	}

	protected void read() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (running) {
					try {
						BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
						lastMessage = reader.readLine();

						discussion.add(lastMessage);
						
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
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
					try {
						DataOutputStream writer = new DataOutputStream(client.getOutputStream());
						writer.writeBytes(message + "\n");
						
						Thread.sleep(50);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	public void edit(Socket client) {
		try {
			this.client.close();
			this.client = client;
		} catch (Exception e) {
			System.err.println("Port already in use");
		}
	}

	public void terminate() {
		try {
			running = false;
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Socket getClient() {
		return client;
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
