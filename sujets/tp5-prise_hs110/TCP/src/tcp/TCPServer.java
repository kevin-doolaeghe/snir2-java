package tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer implements Runnable {
	private int port;
	private ServerSocket tcpServer;

	private String message;

	private boolean running;

	public TCPServer(int port) {
		this.port = port;

		try {
			tcpServer = new ServerSocket(port);
		} catch (Exception e) {
			System.err.println("Port already in use");
		}

		message = "";

		running = true;
	}

	@Override
	public void run() {
		try {
			while (running) {
				Socket socket = tcpServer.accept();
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				String message = "Received from " + tcpServer.getInetAddress() + " : " + reader.readLine() + "\n";

				this.message += message;

				System.out.println(message);
			}
		} catch (Exception e) {

		}
	}

	public int getPort() {
		return port;
	}

	public String getMessage() {
		String message = this.message;
		this.message = "";

		return message;
	}

	public void stop() {
		running = false;
	}
}
