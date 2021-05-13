package scanner;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Inet4Address;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class Window extends JFrame implements ActionListener {
	private Box ligne, colonne;

	private JLabel label_localIp, label_ip, label_mask, label_hostNb_title,
			label_networkAddr_title, label_broadcast_title, label_hostNb,
			label_networkAddr, label_broadcast;
	private JTextField textField_ip, textField_mask;
	private JTextArea textArea_result;
	private JScrollPane scrollPane_result;
	private JButton button_ping, button_displaySettings;

	public Window() throws IOException {
		this.setTitle("Ping");
		this.setSize(720, 480);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {

			}
		});

		this.getContentPane().setLayout(
				new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		// Button

		button_ping = new JButton("Ping");

		button_displaySettings = new JButton(
				"Afficher les caractéristiques du réseau");

		// TextField

		textField_ip = new JTextField("192.168.1.1");
		textField_ip.setMaximumSize(new Dimension(999999999, 20));

		textField_mask = new JTextField("255.255.255.0");
		textField_mask.setMaximumSize(new Dimension(999999999, 20));

		textArea_result = new JTextArea();

		// ScrollPane

		scrollPane_result = new JScrollPane(textArea_result);
		scrollPane_result
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane_result
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_result.setMaximumSize(new Dimension(999999999, 200));

		// Label

		label_localIp = new JLabel("IP locale : "
				+ Inet4Address.getLocalHost().getHostAddress());

		label_ip = new JLabel("Adresse IP :");

		label_mask = new JLabel("Masque :");

		label_hostNb_title = new JLabel("Nombre d'hôtes :");

		label_networkAddr_title = new JLabel("Adresse réseau :");

		label_broadcast_title = new JLabel("Broadcast :");

		label_hostNb = new JLabel();

		label_networkAddr = new JLabel();

		label_broadcast = new JLabel();

		// ****************************************

		// Ligne 1

		ligne = Box.createHorizontalBox();

		colonne = Box.createVerticalBox();
		colonne.add(label_localIp);
		ligne.add(colonne);

		this.getContentPane().add(ligne);

		// Ligne 2

		ligne = Box.createHorizontalBox();

		colonne = Box.createVerticalBox();
		colonne.add(label_ip);
		ligne.add(colonne);

		colonne = Box.createVerticalBox();
		colonne.add(textField_ip);
		ligne.add(colonne);

		this.getContentPane().add(ligne);

		// Ligne 3

		ligne = Box.createHorizontalBox();

		colonne = Box.createVerticalBox();
		colonne.add(label_mask);
		ligne.add(colonne);

		colonne = Box.createVerticalBox();
		colonne.add(textField_mask);
		ligne.add(colonne);

		this.getContentPane().add(ligne);

		// Ligne 4

		ligne = Box.createHorizontalBox();

		colonne = Box.createVerticalBox();
		colonne.add(label_hostNb_title);
		colonne.add(label_networkAddr_title);
		colonne.add(label_broadcast_title);
		ligne.add(colonne);

		colonne = Box.createVerticalBox();
		colonne.add(label_hostNb);
		colonne.add(label_networkAddr);
		colonne.add(label_broadcast);
		ligne.add(colonne);

		this.getContentPane().add(ligne);

		// Ligne 5

		ligne = Box.createHorizontalBox();

		colonne = Box.createVerticalBox();
		colonne.add(button_ping);
		colonne.add(button_displaySettings);
		ligne.add(colonne);

		colonne = Box.createVerticalBox();
		colonne.add(scrollPane_result);
		ligne.add(colonne);

		this.getContentPane().add(ligne);

		// ****************************************

		button_ping.addActionListener(this);
		button_displaySettings.addActionListener(this);

		this.setVisible(true);

		// ****************************************

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			if (event.getSource() == button_ping) {
				new Thread(new Ping(textField_ip.getText().toString(),
						textArea_result)).start();
			}

			if (event.getSource() == button_displaySettings) {
				int ip = parseIp(textField_ip.getText().toString());
				int mask = parseIp(textField_mask.getText().toString());

				int hostNb = ~mask - 1;
				int network = ip & mask;
				int broadcast = ip | ~mask;

				label_hostNb.setText(hostNb != -1 ? Integer.toString(hostNb) : "0");
				label_networkAddr.setText(toString(network));
				label_broadcast.setText(toString(broadcast));
			}
		} catch (Exception e) {

		}
	}

	private int parseIp(String address) {
		int result = 0;

		for (String part : address.split(Pattern.quote("."))) {
			result = result << 8;
			result |= Integer.parseInt(part);
		}

		return result;
	}

	private String toString(int address) {
		String result = "";

		int mask = 0xFF000000;

		for (int i = 0; i < 4; i++) {
			int addr = 0;
			addr = address & (mask >> (8 * i));
			addr = addr >> (8 * (3 - i));
			addr &= 0x000000FF;
			result += Integer.toString(addr);
			if (i < 3)
				result += ".";
		}

		return result;
	}
}
