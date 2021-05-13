package engine.chat;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import engine.com.PacketCollector;
import engine.com.PacketTransmitter;

@SuppressWarnings("serial")
public class Client extends JPanel implements Runnable, ActionListener, FocusListener {
	private PacketCollector collector;
	private PacketTransmitter transmitter;

	private boolean running;

	private String name;

	// ====================

	private String ip;
	private int port;

	private String username;
	private String message;
	private String discussion;
	
	private String recievedMessage;

	// ====================

	private GridBagConstraints gridBagConstraints;

	// ======== JPanel

	private JLabel label_localIP;

	private JButton button_sendMessage, button_changeSettings;

	private JTextArea textArea_message;
	private JTextArea textArea_discussion;

	private JScrollPane scrollPane_message;
	private JScrollPane scrollPane_discussion;

	// ======== JDialog

	private JDialog dialog_settings;

	private JTextField textField_name;
	private JTextField textField_ip;
	private JTextField textField_port;
	private JTextField textField_username;

	private JButton button_applySettings;
	
	// ======================================

	public Client() {
		collector = new PacketCollector();
		transmitter = new PacketTransmitter();
		
		running = true;

		name = "";

		username = "";

		message = "";
		discussion = "";
		
		recievedMessage = "";

		initComponents();
		dialog_settings.setVisible(true);
	}

	// ======================================

	private void initComponents() {
		initPanel();
		initDialog();
	}
	
	private void initPanel() {
		gridBagConstraints = new GridBagConstraints();

		label_localIP = new JLabel();

		button_sendMessage = new JButton();
		button_changeSettings = new JButton();

		textArea_message = new JTextArea();
		textArea_discussion = new JTextArea();

		scrollPane_message = new JScrollPane(textArea_message);
		scrollPane_discussion = new JScrollPane(textArea_discussion);

		// ====================

		this.setLayout(new GridBagLayout());

		try {
			label_localIP.setText("IP locale : " + InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			label_localIP.setText("IP locale introuvable...");
		}

		button_sendMessage.setText("Envoyer");
		button_changeSettings.setText("Paramètres");

		textArea_discussion.setEditable(false);

		scrollPane_message.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_message.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane_discussion.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_discussion.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		// ====================

		gridBagConstraints.insets = new Insets(12, 12, 12, 12);
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.anchor = GridBagConstraints.BASELINE_TRAILING;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weighty = 0.05;
		this.add(label_localIP, gridBagConstraints);
		
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.anchor = GridBagConstraints.BASELINE;
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.weightx = 0.7;
		gridBagConstraints.weighty = 0.05;
		this.add(scrollPane_message, gridBagConstraints);
		
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.weightx = 0.1;
		this.add(button_sendMessage, gridBagConstraints);

		gridBagConstraints.gridx = 2;
		gridBagConstraints.weightx = 0.1;
		this.add(button_changeSettings, gridBagConstraints);

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.weightx = 0.9;
		gridBagConstraints.weighty = 0.9;
		gridBagConstraints.gridwidth = 3;
		this.add(scrollPane_discussion, gridBagConstraints);

		// ====================

		label_localIP.addFocusListener(this);

		button_sendMessage.addActionListener(this);
		button_changeSettings.addActionListener(this);
	}
	
	private void initDialog() {
		dialog_settings = new JDialog();

		gridBagConstraints = new GridBagConstraints();

		textField_name = new JTextField();
		textField_ip = new JTextField();
		textField_port = new JTextField();
		textField_username = new JTextField();

		button_applySettings = new JButton();

		// ====================

		dialog_settings.setTitle("Paramètres");
		dialog_settings.setSize(350, 270);
		dialog_settings.setResizable(false);
		dialog_settings.setLocationRelativeTo(null);
		dialog_settings.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog_settings.setModal(true);
		dialog_settings.getContentPane().setLayout(new GridBagLayout());
		
		textField_name.setText("Nom du groupe");
		textField_name.setForeground(Color.GRAY);
		textField_name.setPreferredSize(new Dimension(200, 20));
		textField_name.setMinimumSize(new Dimension(200, 20));
		textField_ip.setText("Adresse IP");
		textField_ip.setForeground(Color.GRAY);
		textField_ip.setPreferredSize(new Dimension(200, 20));
		textField_ip.setMinimumSize(new Dimension(200, 20));
		textField_port.setText("Port");
		textField_port.setForeground(Color.GRAY);
		textField_port.setPreferredSize(new Dimension(200, 20));
		textField_port.setMinimumSize(new Dimension(200, 20));
		textField_username.setText("Pseudo");
		textField_username.setForeground(Color.GRAY);
		textField_username.setPreferredSize(new Dimension(200, 20));
		textField_username.setMinimumSize(new Dimension(200, 20));

		button_applySettings.setText("Appliquer");

		// ====================

		gridBagConstraints.insets = new Insets(12, 12, 12, 12);
		gridBagConstraints.fill = GridBagConstraints.NONE;
		gridBagConstraints.anchor = GridBagConstraints.BASELINE;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = GridBagConstraints.RELATIVE;
		gridBagConstraints.weightx = 0.8;
		
		dialog_settings.add(textField_name, gridBagConstraints);

		dialog_settings.add(textField_ip, gridBagConstraints);

		dialog_settings.add(textField_port, gridBagConstraints);
		
		dialog_settings.add(textField_username, gridBagConstraints);

		gridBagConstraints.anchor = GridBagConstraints.BASELINE_TRAILING;
		
		dialog_settings.add(button_applySettings, gridBagConstraints);

		// ====================

		textField_name.addFocusListener(this);
		textField_ip.addFocusListener(this);
		textField_port.addFocusListener(this);
		textField_username.addFocusListener(this);

		button_applySettings.addActionListener(this);
	}

	// ======================================

	public void stop() {
		running = false;
	}

	public void changeSettings() {
		textField_name.setText(name);
		textField_ip.setText(ip);
		textField_port.setText(Integer.toString(port));
		textField_username.setText(username);

		dialog_settings.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		dialog_settings.setVisible(true);
	}

	public void sendMessage() {
		textArea_discussion.append(message + "\n");
		textArea_message.setText(null);

		new Thread(transmitter).start();
	}

	// ======================================

	public void setName(String name) {
		this.name = name;
	}

	public void setIP(String ip) {
		this.ip = ip;

		transmitter.setIP(ip);
	}

	public void setPort(int port) {
		this.port = port;

		collector.setPort(port);
		transmitter.setPort(port);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setMessage(String message) {
		String date = new SimpleDateFormat("dd/MM/yy - HH mm ss").format(new Date());
		this.message = "date:" + date + ",username:" + username + ",message:" + message + ";";

		transmitter.setMessage(message);
	}

	// ======================================

	public String getName() {
		return name;
	}
	
	public int getPort() {
		return port;
	}
 
	public String getDiscussion() {
		return discussion;
	}
	
	public String getRecievedMessage() {
		String recievedMessage = this.recievedMessage;
		this.recievedMessage = "";
		
		return recievedMessage;
	}

	// ======================================

	@Override
	public void run() {
		Thread thread = new Thread(collector);
		thread.start();

		while (running) {
			if (!thread.isAlive()) {
				String recievedMessage = collector.getMessage();
				discussion += recievedMessage;
				this.recievedMessage += recievedMessage;
				
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {

				}

				thread.start();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == button_sendMessage) {
			String message = textArea_message.getText();

			if (!message.isEmpty()) {
				setMessage(message);

				sendMessage();
			} else {
				JOptionPane.showMessageDialog(this, "Message vide", "Impossible d'envoyer le message",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}

		if (event.getSource() == button_changeSettings) {
			changeSettings();
		}

		if (event.getSource() == button_applySettings) {
			try {
				Integer.parseInt(textField_port.getText());
				
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Le numéro de port n'est pas un nombre", "Warning !",
						JOptionPane.WARNING_MESSAGE);
			}if (!textField_name.getText().isEmpty() && !textField_ip.getText().isEmpty()
					&& !textField_port.getText().isEmpty() && !textField_username.getText().isEmpty()) {
				setName(textField_name.getText());

				setIP(textField_ip.getText());
				setPort(Integer.parseInt(textField_port.getText()));

				setUsername(textField_username.getText());

				dialog_settings.setVisible(false);
			}
		}
	}

	@Override
	public void focusGained(FocusEvent event) {
		if (event.getSource() == textArea_message) {
			if (textArea_message.getText().equals("Write your message here")
					&& textArea_message.getForeground().equals(Color.GRAY)) {
				textArea_message.setText("");
				textArea_message.setForeground(Color.BLACK);
			}
		}

		if (event.getSource() == textField_name) {
			if (textField_name.getText().equals("Nom du groupe") && textField_name.getForeground().equals(Color.GRAY)) {
				textField_name.setText("");
				textField_name.setForeground(Color.BLACK);
			}
		}

		if (event.getSource() == textField_ip) {
			if (textField_ip.getText().equals("Adresse IP") && textField_ip.getForeground().equals(Color.GRAY)) {
				textField_ip.setText("");
				textField_ip.setForeground(Color.BLACK);
			}
		}

		if (event.getSource() == textField_port) {
			if (textField_port.getText().equals("Port") && textField_port.getForeground().equals(Color.GRAY)) {
				textField_port.setText("");
				textField_port.setForeground(Color.BLACK);
			}
		}

		if (event.getSource() == textField_username) {
			if (textField_username.getText().equals("Pseudo")
					&& textField_username.getForeground().equals(Color.GRAY)) {
				textField_username.setText("");
				textField_username.setForeground(Color.BLACK);
			}
		}
	}

	@Override
	public void focusLost(FocusEvent event) {
		if (event.getSource() == textArea_message) {
			if (textArea_message.getText().isEmpty()) {
				textArea_message.setForeground(Color.GRAY);
				textArea_message.setText("Write your message here");
			}
		}

		if (event.getSource() == textField_name) {
			if (textField_name.getText().isEmpty()) {
				textField_name.setForeground(Color.GRAY);
				textField_name.setText("Nom du groupe");
			}
		}

		if (event.getSource() == textField_ip) {
			if (textField_ip.getText().isEmpty()) {
				textField_ip.setForeground(Color.GRAY);
				textField_ip.setText("Adresse IP");
			}
		}

		if (event.getSource() == textField_port) {
			if (textField_port.getText().isEmpty()) {
				textField_port.setForeground(Color.GRAY);
				textField_port.setText("Port");
			}
		}

		if (event.getSource() == textField_username) {
			if (textField_username.getText().isEmpty()) {
				textField_username.setForeground(Color.GRAY);
				textField_username.setText("Pseudo");
			}
		}
	}
}
