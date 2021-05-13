package tcp;

import java.io.DataOutputStream;
import java.net.Socket;

public class TCPClient implements Runnable {
	private String ip;
	private int port;

	private Socket tcpClient;

	private String message;

	public TCPClient(String ip, int port, String message) {
		this.ip = ip;
		this.port = port;

		this.message = message;
	}

	@Override
	public void run() {
		try {
			tcpClient = new Socket(this.ip, this.port);

			if (tcpClient.isConnected()) {
				DataOutputStream writer = new DataOutputStream(tcpClient.getOutputStream());
				writer.writeBytes(message + "\n");

				System.out.println("Me : " + message);

				tcpClient.close();
			}
		} catch (Exception e) {
			System.err.println("Impossible de joindre l'hôte");
		}
	}
}
