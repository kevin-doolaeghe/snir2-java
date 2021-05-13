package old.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import client.ClientSettings;

@SuppressWarnings("serial")
public class Client extends JPanel implements ActionListener, FocusListener {
	private PacketCollector client;
	private String name;
	private String username;

	// ============================

	private GridBagConstraints gridBagConstraints;

	private JButton button_send, button_settings;
	private JList<String> list_discussion;
	private JScrollPane scrollPane_discussion;
	private JTextArea textArea_message;

	// ============================

	public Client(String name, String ip, int port, String username) {
		this.name = name;
		this.username = username;
		client = new PacketCollector(ip, port);

		init();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					List<String> list = client.getMessages();
					String[] messages = new String[list.size()];
					list.toArray(messages);
					list_discussion.setListData(messages);
				}
			}
		}).start();
	}

	// ============================

	private void init() {
		button_send = new JButton();
		button_settings = new JButton();
		list_discussion = new JList<String>();
		scrollPane_discussion = new JScrollPane();
		textArea_message = new JTextArea();

		this.setLayout(new GridBagLayout());

		Dimension dimension = new Dimension(150, 50);
		button_send.setMinimumSize(dimension);
		button_send.setPreferredSize(dimension);
		button_send.setText("Envoyer");
		button_send.addActionListener(this);

		button_settings.setMinimumSize(dimension);
		button_settings.setPreferredSize(dimension);
		button_settings.setText("Paramètres");
		button_settings.addActionListener(this);

		dimension = new Dimension(300, 200);
		list_discussion.setMinimumSize(dimension);
		list_discussion.setPreferredSize(dimension);

		scrollPane_discussion.getViewport().setView(list_discussion);
		scrollPane_discussion
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_discussion
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

		dimension = new Dimension(200, 40);
		textArea_message.setMinimumSize(dimension);
		textArea_message.setPreferredSize(dimension);
		textArea_message.setText("Message");
		textArea_message.setForeground(Color.GRAY);
		textArea_message.addFocusListener(this);

		initGridBagConstraints();
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 0.1;
		this.add(textArea_message, gridBagConstraints);

		initGridBagConstraints();
		gridBagConstraints.gridx = 1;
		this.add(button_send, gridBagConstraints);

		initGridBagConstraints();
		gridBagConstraints.gridx = 2;
		this.add(button_settings, gridBagConstraints);

		initGridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.weighty = 0.9;
		this.add(scrollPane_discussion, gridBagConstraints);
	}

	private void initGridBagConstraints() {
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(12, 12, 12, 12); // Marges
		gridBagConstraints.fill = GridBagConstraints.NONE; // Redimensionnement
		gridBagConstraints.anchor = GridBagConstraints.BASELINE; // Ancrage si
																	// place
																	// disponible
		gridBagConstraints.gridx = 0; // Ligne sélectionnée
		gridBagConstraints.gridy = 0; // Colonne sélectionnée
		gridBagConstraints.gridwidth = 1; // Nombre de cases occupées
											// horizontalement
		gridBagConstraints.gridheight = 1; // Nombre de cases occupées
											// verticalement
		gridBagConstraints.ipadx = 0; // Marges internes horizontales
		gridBagConstraints.ipady = 0; // Marges internes verticales
		gridBagConstraints.weightx = 0; // Ratio de l'espace supplémentaire
										// horizontal attribué
		gridBagConstraints.weighty = 0; // Ratio de l'espace supplémentaire
										// vertical attribué
	}

	public void changeSettings(String name, String ip, int port, String username) {
		this.name = name;
		this.username = username;

		client.update(ip, port);
	}

	public void delete() {
		client.finalize();
	}

	// ============================

	public String getName() {
		return name;
	}

	// ============================

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == button_send) {
			String message = textArea_message.getText();

			if (textArea_message.getForeground() != Color.GRAY) {
				String date = new SimpleDateFormat("dd/MM/yy - HH:mm:ss")
						.format(new Date());
				message = date + " => " + username + " : " + message + "\n";

				client.send(message);
			} else {
				JOptionPane.showMessageDialog(null, "Message vide", "Attention !",
						JOptionPane.WARNING_MESSAGE);
			}
		}

		if (event.getSource() == button_settings) {
			new ClientSettings(this);
		}
	}

	@Override
	public void focusGained(FocusEvent event) {
		if (event.getSource() == textArea_message) {
			if (textArea_message.getText().equals("Message")
					&& textArea_message.getForeground().equals(Color.GRAY)) {
				textArea_message.setText("");
				textArea_message.setForeground(Color.BLACK);
			}
		}
	}

	@Override
	public void focusLost(FocusEvent event) {
		if (event.getSource() == textArea_message) {
			if (textArea_message.getText().isEmpty()) {
				textArea_message.setForeground(Color.GRAY);
				textArea_message.setText("Message");
			}
		}
	}
}
