package fr.kevin.hs110;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class HS110 {
	private Socket clientSocket;
	private DataOutputStream outToServer;
	private BufferedReader inFromServer;
	
	private double i, u, p, total;
	private String trame;
	
	public HS110(String ip) throws IOException {
		clientSocket = new Socket(ip, 9999);
		outToServer = new DataOutputStream(clientSocket.getOutputStream());
		inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		i = 0;
		u = 0;
		p = 0;
		total = 0;
		
		trame = "";
	}
	
	public void finalize() throws IOException {
		outToServer.close();
		inFromServer.close();
		clientSocket.close();
	}
	
	private byte[] encrypt(String message) {
		byte[] data = message.getBytes(StandardCharsets.US_ASCII);
        byte[] enc = new byte[data.length + 4];
        
        ByteBuffer.wrap(enc).putInt(data.length);
        System.arraycopy(data, 0, enc, 4, data.length);
        byte key = (byte) 0xAB;
        
        for (int i = 4; i < enc.length; i++) {
            enc[i] = (byte) (enc[i] ^ key);
            key = enc[i];
        }
        
        return enc;
	}
	
	private String decrypt(byte[] data) {
        if (data == null) {
            return null;
        }
        
        byte key = (byte) 0xAB;
        byte nextKey = 0;
        
        for (int i = 4; i < data.length; i++) {
            nextKey = data[i];
            data[i] = (byte) (data[i] ^ key);
            key = nextKey;
        }
        
        return new String(data, 4, data.length - 4, StandardCharsets.US_ASCII);
    }
	
	private String getHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }
	
	private void updateValues() {
		i = getValue("current");
		u = getValue("voltage");
		p = getValue("power");
		total = getValue("total");
	}
	
	private double getValue(String balise) {
		if(!trame.isEmpty()) {
			double value = 0;
			String temp;
			int debut, fin, longueur;
			
			debut = trame.indexOf(balise) + 2 + balise.length();
			fin = trame.indexOf(",", debut) - 1;

			if(debut != -1 && fin != -1) {
				longueur = fin - debut + 1;System.out.println("long="+longueur+",debut="+debut+"fin="+fin);
				
				temp = trame.substring(debut, fin);
				if(!temp.isEmpty())
					value = Double.parseDouble(temp);
				else value = 0;
				System.out.println("val="+value);
				return value;
			} else return 0;
		} else
			return 0;
	}
	
	public void relayOn() throws IOException {
		outToServer.write(encrypt("{\"system\":{\"set_relay_state\":{\"state\":1}}}"));
	}
	
	public void relayOff() throws IOException {
		outToServer.write(encrypt("{\"system\":{\"set_relay_state\":{\"state\":0}}}"));
	}
	
	public String getInfos() throws IOException {
		outToServer.write(encrypt("{\"system\":{\"get_sysinfo\":{}}}"));
		
		String reponse = "";
		while(inFromServer.read() != -1) reponse += inFromServer.readLine();
		reponse = decrypt(reponse.getBytes());
		System.out.println("Réponse : " + reponse);
		
		return reponse;
	}
	
	public String getMeasures() throws IOException {
		outToServer.write(encrypt("{\"emeter\":{\"get_realtime\":null}}"));
		
		String reponse = "";
		while(inFromServer.read() != -1) reponse += inFromServer.readLine();
		reponse = decrypt(reponse.getBytes());
		System.out.println("Réponse : " + reponse);
		
		trame = reponse;
		//updateValues();
		
		return trame;
	}

	public double getI() {
		return i;
	}

	public double getU() {
		return u;
	}

	public double getP() {
		return p;
	}

	public double getTotal() {
		return total;
	}
}
