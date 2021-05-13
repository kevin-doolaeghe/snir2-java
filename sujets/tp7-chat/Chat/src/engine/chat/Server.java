package engine.chat;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import engine.com.PacketTransmitter;

@SuppressWarnings("serial")
public class Server extends Client implements Runnable, ActionListener, FocusListener {
	private PacketTransmitter transmitter;

	private boolean running;

	// ====================

	private String ip;
	private int port;

	private List<String> ipList;

	private String message;

	// ====================

	private GridBagConstraints gridBagConstraints;

	private JDialog dialog_hosts;

	private JTextArea textArea_hosts;

	private JTextField textField_editHost;

	private JButton button_ok;
	private JButton button_add;
	private JButton button_delete;

	// ======================================

	public Server() {
		super();

		transmitter = new PacketTransmitter();

		running = true;

		ipList = new ArrayList<String>();
		ipList.add("localhost");

		message = "";

		initDialog();
	}

	// ======================================

	private void initDialog() {
		dialog_hosts = new JDialog();

		gridBagConstraints = new GridBagConstraints();

		textArea_hosts = new JTextArea();

		textField_editHost = new JTextField();

		button_ok = new JButton();
		button_add = new JButton();
		button_delete = new JButton();

		// ====================

		dialog_hosts.setTitle("Hôtes");
		dialog_hosts.setSize(350, 270);
		dialog_hosts.setResizable(false);
		dialog_hosts.setLocationRelativeTo(null);
		dialog_hosts.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog_hosts.setModal(true);
		dialog_hosts.getContentPane().setLayout(new GridBagLayout());

		textArea_hosts.setEditable(false);

		textField_editHost.setText("Hôte");
		textField_editHost.setForeground(Color.GRAY);

		button_ok.setText("Ok");
		button_add.setText("Ajouter");
		button_delete.setText("Supprimer");

		// ====================

		gridBagConstraints.insets = new Insets(12, 12, 12, 12);
		gridBagConstraints.gridwidth = 1;

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		dialog_hosts.add(textArea_hosts, gridBagConstraints);

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		dialog_hosts.add(textField_editHost, gridBagConstraints);

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		dialog_hosts.add(button_add, gridBagConstraints);

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		dialog_hosts.add(button_delete, gridBagConstraints);

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		dialog_hosts.add(button_ok, gridBagConstraints);

		// ====================

		textField_editHost.addFocusListener(this);

		button_ok.addActionListener(this);
		button_add.addActionListener(this);
		button_delete.addActionListener(this);
	}

	// ======================================

	public void stop() {
		super.stop();
		running = false;
	}

	public void editHosts() {
		textArea_hosts.setText("");

		for (String ip : ipList) {
			textArea_hosts.append(ip + "\n");
		}

		dialog_hosts.setVisible(true);
	}

	public void addHost(String ip) {
		ipList.add(ip);
	}

	public void replaceHost(String ipBefore, String ipAfter) {
		ipList.set(ipList.indexOf(ipBefore), ipAfter);
	}

	public void deleteHost(String ip) {
		ipList.remove(ip);
	}

	// ======================================

	public void setIP(String ip) {
		this.ip = ip;

		transmitter.setIP(this.ip);
	}

	public void setPort(int port) {
		this.port = port;

		transmitter.setPort(this.port);
	}

	public void setMessage(String message) {
		this.message = message;

		transmitter.setMessage(message);
	}

	// ======================================

	@Override
	public void run() {
		while (running) {
			setMessage(super.getRecievedMessage());

			if (!message.isEmpty()) {
				for (String ip : ipList) {
					setIP(ip);
					setPort(super.getPort());

					new Thread(transmitter).start();
				}
			}

			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {

			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == button_ok) {
			dialog_hosts.setVisible(false);
		}

		if (event.getSource() == button_add) {
			if (!textField_editHost.getText().isEmpty()) {
				addHost(textField_editHost.getText());

				textField_editHost.setText("");
			}
		}

		if (event.getSource() == button_delete) {
			if (!textField_editHost.getText().isEmpty()) {
				deleteHost(textField_editHost.getText());

				textField_editHost.setText("");
			}
		}
	}

	@Override
	public void focusGained(FocusEvent event) {
		if (event.getSource() == textField_editHost) {
			if (textField_editHost.getText().equals("Hôte") && textField_editHost.getForeground().equals(Color.GRAY)) {
				textField_editHost.setText("");
				textField_editHost.setForeground(Color.BLACK);
			}
		}
	}

	@Override
	public void focusLost(FocusEvent event) {
		if (event.getSource() == textField_editHost) {
			if (textField_editHost.getText().isEmpty()) {
				textField_editHost.setForeground(Color.GRAY);
				textField_editHost.setText("Hôte");
			}
		}
	}
}
