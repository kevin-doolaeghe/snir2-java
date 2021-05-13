package scanner;

import java.net.InetAddress;

public class Ping implements Runnable {
	private String ip;

	public Ping(String ip) {
		this.ip = ip;
	}

	@Override
	public void run() {
		try {
			InetAddress inet = InetAddress.getByName(ip);
			boolean isReachable = inet.isReachable(5000);

			System.out.println("Sending Ping request to " + ip);
			System.out.println(isReachable ? "Host is reachable" : "Host is NOT reachable");
		} catch (Exception e) {
			System.err.println("Error");
		}
	}
}
