package engine.com;

import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

public class PacketTransmitter implements Runnable {
	private String ip;
	private int port;

	private String message;

	public PacketTransmitter() {
		this.message = "";
	}
	
	public PacketTransmitter(String ip, int port) {
		this.ip = ip;
		this.port = port;

		this.message = "";
	}

	@Override
	public void run() {
		write();
	}
	
	private void write() {
		try {
			Socket client = new Socket(this.ip, this.port);

			if (client.isConnected() && !message.isEmpty()) {
				DataOutputStream writer = new DataOutputStream(client.getOutputStream());
				writer.writeBytes(message + "\n");

				client.close();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Host " + ip + " is unreachable", "Warning !",
					JOptionPane.WARNING_MESSAGE);
			System.err.println("Host " + ip + " is unreachable");
		}
	}

	public void setIP(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
