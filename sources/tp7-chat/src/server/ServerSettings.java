package server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.ServerSocket;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ServerSettings extends JDialog implements ActionListener, FocusListener {
	private ServerPanel server;

	// ============================

	private GridBagConstraints gridBagConstraints;

	private JButton button_apply;
	private JTextField textField_name, textField_port;

	// ============================

	public ServerSettings(ServerPanel server) {
		this.server = server;

		init();
	}

	// ============================

	private void init() {
		textField_name = new JTextField();
		textField_port = new JTextField();

		button_apply = new JButton();

		setTitle("Paramètres");
		setSize(350, 270);
		setResizable(false);
		setLocationRelativeTo(null);
		setModal(true);
		getContentPane().setLayout(new GridBagLayout());

		Dimension dimension = new Dimension(200, 20);
		textField_name.setText(server.getName());
		textField_name.setForeground(Color.GRAY);
		textField_name.setPreferredSize(dimension);
		textField_name.setMinimumSize(dimension);
		textField_name.addFocusListener(this);

		textField_port.setText(Integer.toString(server.getServer().getLocalPort()));
		textField_port.setForeground(Color.GRAY);
		textField_port.setPreferredSize(dimension);
		textField_port.setMinimumSize(dimension);
		textField_port.addFocusListener(this);

		button_apply.setText("Appliquer");
		button_apply.addActionListener(this);

		initGridBagConstraints();
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 0.8;
		getContentPane().add(textField_name, gridBagConstraints);

		initGridBagConstraints();
		gridBagConstraints.gridy = 1;
		gridBagConstraints.weightx = 0.8;
		getContentPane().add(textField_port, gridBagConstraints);

		initGridBagConstraints();
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.BASELINE_TRAILING;
		getContentPane().add(button_apply, gridBagConstraints);

		setVisible(true);
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

	// ============================

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == button_apply) {
			try {
				Integer.parseInt(textField_port.getText());

				if (!textField_name.getText().isEmpty() && !textField_port.getText().isEmpty()) {
					String name = textField_name.getText();

					int port = Integer.parseInt(textField_port.getText());
					ServerSocket socket = new ServerSocket(port);

					server.edit(socket, name);

					setVisible(false);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Le numéro de port n'est pas un nombre", "Warning !",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	@Override
	public void focusGained(FocusEvent event) {
		if (event.getSource() == textField_name) {
			if (textField_name.getText().equals("Nom du groupe") && textField_name.getForeground().equals(Color.GRAY)) {
				textField_name.setText("");
				textField_name.setForeground(Color.BLACK);
			}
		}

		if (event.getSource() == textField_port) {
			if (textField_port.getText().equals("Port") && textField_port.getForeground().equals(Color.GRAY)) {
				textField_port.setText("");
				textField_port.setForeground(Color.BLACK);
			}
		}
	}

	@Override
	public void focusLost(FocusEvent event) {
		if (event.getSource() == textField_name) {
			if (textField_name.getText().isEmpty()) {
				textField_name.setForeground(Color.GRAY);
				textField_name.setText("Nom du groupe");
			}
		}

		if (event.getSource() == textField_port) {
			if (textField_port.getText().isEmpty()) {
				textField_port.setForeground(Color.GRAY);
				textField_port.setText("Port");
			}
		}
	}
}
