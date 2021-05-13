package scanner;

import java.util.regex.Pattern;

public class NetworkScanner implements Runnable {
	private String ip, mask;
	
	public NetworkScanner(String ip, String mask) {
		this.ip = ip;
		this.mask = mask;
	}

	@Override
	public void run() {
		int network = parseIp(ip) & parseIp(mask);
		int cidr = getCidrFromMask(mask);
		int broadcast = (int) (network + Math.pow(2, (32 - cidr)));
		
		for(int address = network; address < broadcast + 1; address++) {
			new Thread(new Ping()).start();
		}
	}
	
	private int parseIp(String address) {
		int result = 0;
		
		for(String part : address.split(Pattern.quote("."))) {
			result = result << 8;
			result |= Integer.parseInt(part);
		}
		
		return result;
	}
	
	private String toString(int address) {
		String result = "";
		
		int mask = 0xFF000000;
		
		for(int i = 0; i < 4; i++) {
			result += Integer.toString((address & mask) >> (8 * i));
			if(i < 4) result += ".";
		}
		
		return result;
	}
	
	private int getCidrFromMask(String mask) {
		int cidr = 0;
		
		for(String part : mask.split(Pattern.quote("."))) {
			int partInt = Integer.parseInt(part);
			for(int i = 0; i < 8; i++) {
				if((partInt & (0x80000000 >> (i * 8))) == 1) cidr++;
			}
		}
		
		return cidr;
	}
}
