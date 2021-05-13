package server;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import server.ServerSettings;

@SuppressWarnings("serial")
public class ServerPanel extends JPanel implements ActionListener {
	private Server server;
	private String name;
	private boolean running;

	// ============================

	private GridBagConstraints gridBagConstraints;

	private JButton button_settings;
	private JLabel label_localIP;
	private JList<String> list_clients;
	private JScrollPane scrollPane_clients;

	// ============================

	public ServerPanel(ServerSocket server, String name) {
		this.server = new Server(server);
		this.name = name;
		running = true;

		init();
		
		getMessages();
	}

	// ============================

	private void init() {
		button_settings = new JButton();
		label_localIP = new JLabel();
		list_clients = new JList<String>();
		scrollPane_clients = new JScrollPane();

		this.setLayout(new GridBagLayout());

		Dimension dimension = new Dimension(150, 50);
		button_settings.setMinimumSize(dimension);
		button_settings.setPreferredSize(dimension);
		button_settings.setText("Paramètres");
		button_settings.addActionListener(this);

		dimension = new Dimension(200, 40);
		label_localIP.setMinimumSize(dimension);
		label_localIP.setPreferredSize(dimension);
		try {
			label_localIP.setText("IP locale : " + InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			label_localIP.setText("Aucune adresse IP locale");
		}

		dimension = new Dimension(300, 200);
		list_clients.setMinimumSize(dimension);
		list_clients.setPreferredSize(dimension);

		scrollPane_clients.getViewport().setView(list_clients);
		scrollPane_clients.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_clients.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		initGridBagConstraints();
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0.1;
		this.add(label_localIP, gridBagConstraints);

		initGridBagConstraints();
		gridBagConstraints.gridx = 1;
		this.add(button_settings, gridBagConstraints);

		initGridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.weighty = 0.9;
		this.add(scrollPane_clients, gridBagConstraints);
	}

	private void initGridBagConstraints() {
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(12, 12, 12, 12); // Marges
		gridBagConstraints.fill = GridBagConstraints.NONE; // Redimensionnement
		gridBagConstraints.anchor = GridBagConstraints.BASELINE; // Ancrage si place disponible
		gridBagConstraints.gridx = 0; // Ligne sélectionnée
		gridBagConstraints.gridy = 0; // Colonne sélectionnée
		gridBagConstraints.gridwidth = 1; // Nombre de cases occupées horizontalement
		gridBagConstraints.gridheight = 1; // Nombre de cases occupées verticalement
		gridBagConstraints.ipadx = 0; // Marges internes horizontales
		gridBagConstraints.ipady = 0; // Marges internes verticales
		gridBagConstraints.weightx = 0; // Ratio de l'espace supplémentaire horizontal attribué
		gridBagConstraints.weighty = 0; // Ratio de l'espace supplémentaire vertical attribué
	}

	public void edit(ServerSocket server, String name) {
		this.server.edit(server);
		this.name = name;
	}

	public void terminate() {
		running = false;
		server.terminate();
	}

	private void getMessages() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (running) {
					try {
						List<Socket> clients = server.getClients();
						if(clients.size() != 0) {
							String[] list = new String[clients.size()];
							list = clients.toArray(list);
							
							System.out.println(list);
							list_clients.setListData(list);
						}
						
						Thread.sleep(50);
					} catch (Exception e) {

					}
				}
			}
		}).start();
	}

	public ServerSocket getServer() {
		return server.getServer();
	}

	public String getName() {
		return name;
	}

	// ============================

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == button_settings) {
			new ServerSettings(this);
		}
	}
}
