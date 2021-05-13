package tcp;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.Inet4Address;

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

	private JLabel label_titre, label_ipLocale, label_serveur, label_client, label_portEcoute, label_ip,
			label_portReception, label_message, label_discussion;
	private JTextField textField_portEcoute, textField_ip, textField_portReception, textField_message;
	private JTextArea textArea_discussion;
	private JScrollPane scrollPane_discussion;
	private JButton button_modifPortEcoute, button_envoi;

	private TCPClient client;
	private TCPServer serveur;
	private Thread tServeurTCP, tClientTCP;

	public Window() throws IOException {
		this.setTitle("TCP client / server");
		this.setSize(720, 480);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				serveur.stop();
			}
		});

		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

		// Button

		button_modifPortEcoute = new JButton("Modifier le port d'écoute");

		button_envoi = new JButton("Envoyer");

		// TextField

		textField_portEcoute = new JTextField("9999");
		textField_portEcoute.setMaximumSize(new Dimension(999999999, 20));

		textField_ip = new JTextField("192.168.1.1");
		textField_ip.setMaximumSize(new Dimension(999999999, 20));

		textField_portReception = new JTextField("8888");
		textField_portReception.setMaximumSize(new Dimension(999999999, 20));

		textField_message = new JTextField("Bonjour !");
		textField_message.setMaximumSize(new Dimension(999999999, 20));

		textArea_discussion = new JTextArea();

		// ScrollPane

		scrollPane_discussion = new JScrollPane(textArea_discussion);
		scrollPane_discussion.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane_discussion.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_discussion.setMaximumSize(new Dimension(999999999, 200));

		// Label

		label_titre = new JLabel("TCP client / server");

		label_ipLocale = new JLabel(Inet4Address.getLocalHost().getHostAddress());

		label_serveur = new JLabel("Serveur TCP :");

		label_client = new JLabel("Client TCP :");

		label_portEcoute = new JLabel("Port d'écoute :");

		label_ip = new JLabel("Adresse IP :");

		label_portReception = new JLabel("Port de réception :");

		label_message = new JLabel("Message :");

		label_discussion = new JLabel("Discussion :");

		// ****************************************

		// Ligne 1

		ligne = Box.createHorizontalBox();

		colonne = Box.createVerticalBox();
		colonne.add(label_titre);
		ligne.add(colonne);

		this.getContentPane().add(ligne);

		// Ligne 2

		ligne = Box.createHorizontalBox();

		colonne = Box.createVerticalBox();
		colonne.add(label_ipLocale);
		ligne.add(colonne);

		this.getContentPane().add(ligne);

		// Ligne 3

		ligne = Box.createHorizontalBox();

		colonne = Box.createVerticalBox();
		colonne.add(label_serveur);
		ligne.add(colonne);

		colonne = Box.createVerticalBox();
		colonne.add(label_portEcoute);
		ligne.add(colonne);

		colonne = Box.createVerticalBox();
		colonne.add(textField_portEcoute);
		ligne.add(colonne);

		colonne = Box.createVerticalBox();
		colonne.add(button_modifPortEcoute);
		ligne.add(colonne);

		this.getContentPane().add(ligne);

		// Ligne 4

		ligne = Box.createHorizontalBox();

		colonne = Box.createVerticalBox();
		colonne.add(label_client);
		ligne.add(colonne);

		colonne = Box.createVerticalBox();
		colonne.add(label_ip);
		colonne.add(label_portReception);
		ligne.add(colonne);

		colonne = Box.createVerticalBox();
		colonne.add(textField_ip);
		colonne.add(textField_portReception);
		ligne.add(colonne);

		this.getContentPane().add(ligne);

		// Ligne 5

		ligne = Box.createHorizontalBox();

		colonne = Box.createVerticalBox();
		colonne.add(label_message);
		ligne.add(colonne);

		colonne = Box.createVerticalBox();
		colonne.add(textField_message);
		ligne.add(colonne);

		colonne = Box.createVerticalBox();
		colonne.add(button_envoi);
		ligne.add(colonne);

		this.getContentPane().add(ligne);

		// Ligne 6

		ligne = Box.createHorizontalBox();

		colonne = Box.createVerticalBox();
		colonne.add(label_discussion);
		colonne.add(scrollPane_discussion);
		ligne.add(colonne);

		this.getContentPane().add(ligne);

		// ****************************************

		button_modifPortEcoute.addActionListener(this);
		button_envoi.addActionListener(this);

		this.setVisible(true);

		// ****************************************

		serveur = new TCPServer(9999);
		tServeurTCP = new Thread(serveur);
		tServeurTCP.start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					textArea_discussion.setText(textArea_discussion.getText() + serveur.getMessage());
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			if (event.getSource() == button_modifPortEcoute) {
				int port = Integer.parseInt(textField_portEcoute.getText().toString());

				if (tServeurTCP.isAlive()) {
					serveur.stop();
				}

				serveur = new TCPServer(port);
				tServeurTCP = new Thread(serveur);
				tServeurTCP.start();
			}

			if (event.getSource() == button_envoi) {
				String ip = textField_ip.getText().toString();
				int port = Integer.parseInt(textField_portReception.getText().toString());

				String message = textField_message.getText().toString();

				client = new TCPClient(ip, port, message);
				tClientTCP = new Thread(client);
				tClientTCP.start();

				textArea_discussion.setText(textArea_discussion.getText().toString() + "Me : " + message + "\n");
			}
		} catch (Exception e) {

		}
	}
}
