package engine.com;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class PacketCollector implements Runnable {
	private int port;

	private String message;

	public PacketCollector() {
		this.message = "";
	}
	
	public PacketCollector(int port) {
		this.port = port;

		this.message = "";
	}

	@Override
	public void run() {
		read();
	}

	private void read() {
		try {
			ServerSocket server = new ServerSocket(port);

			Socket socket = server.accept();
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String message = reader.readLine();

			this.message += message;

			socket.close();
			server.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Port already in use", "Warning !", JOptionPane.WARNING_MESSAGE);
			System.err.println("Port already in use");
		}
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getMessage() {
		String message = this.message;
		this.message = "";

		return message;
	}
}
