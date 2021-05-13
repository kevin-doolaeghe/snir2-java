package scanner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
//import java.net.InetAddress;

import javax.swing.JTextArea;

public class Ping implements Runnable {
	private String ip;
	private JTextArea textArea;

	public Ping(String ip, JTextArea textArea) {
		this.ip = ip;
		this.textArea = textArea;
	}

	@Override
	public void run() {
		try {
			long pastTime = System.currentTimeMillis();

			Process proc = Runtime.getRuntime().exec("ping " + ip);
			BufferedReader inputStream = new BufferedReader(
					new InputStreamReader(proc.getInputStream()));

			String s = "";
			boolean isReachable = false;

			while ((s = inputStream.readLine()) != null) {
				System.out.println(s);

				if (s.indexOf("perte") != -1) {
					if (s.indexOf("perte 100%") == -1) {
						isReachable = true;
					}
				}
			}

			double elapsedTime = (double) (System.currentTimeMillis() - pastTime) / 1000.0;
			
			textArea.setText(isReachable ? textArea.getText() + ip
					+ " répond. Temps écoulé " + elapsedTime + " sec\n" : textArea
					.getText()
					+ ip
					+ " ne répond PAS. Temps écoulé "
					+ elapsedTime + "\n");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
