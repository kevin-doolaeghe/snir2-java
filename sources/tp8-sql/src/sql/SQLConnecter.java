package sql;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class SQLConnecter extends JDialog implements ActionListener, FocusListener {
	@SuppressWarnings("unused")
	private SQLServer mysql;
	
	private GridBagConstraints gridBagConstraints;
	
	private JButton button_apply;
	private JTextField textField_base, textField_ip, textField_login, textField_password;
	
	public SQLConnecter(SQLServer mysql) {
		this.mysql = mysql;
		
		init();
	}
	
	public void init() {
		setTitle("Connexion SQL");
		setSize(350, 270);
		setResizable(false);
		setLocationRelativeTo(null);
		setModal(true);
		setLayout(new GridBagLayout());
		
		button_apply = new JButton();
		
		textField_base = new JTextField();
		textField_ip = new JTextField();
		textField_login = new JTextField();
		textField_password = new JTextField();
		
		Dimension dimension = new Dimension(200, 20);
		textField_base.setText("Base");
		textField_base.setForeground(Color.GRAY);
		textField_base.setPreferredSize(dimension);
		textField_base.setMinimumSize(dimension);
		textField_base.addFocusListener(this);

		textField_ip.setText("Adresse IP");
		textField_ip.setForeground(Color.GRAY);
		textField_ip.setPreferredSize(dimension);
		textField_ip.setMinimumSize(dimension);
		textField_ip.addFocusListener(this);

		textField_login.setText("Login");
		textField_login.setForeground(Color.GRAY);
		textField_login.setPreferredSize(dimension);
		textField_login.setMinimumSize(dimension);
		textField_login.addFocusListener(this);

		textField_password.setText("Mot de passe");
		textField_password.setForeground(Color.GRAY);
		textField_password.setPreferredSize(dimension);
		textField_password.setMinimumSize(dimension);
		textField_password.addFocusListener(this);

		button_apply.setText("Appliquer");
		button_apply.addActionListener(this);

		initGridBagConstraints();
		add(textField_base, gridBagConstraints);

		initGridBagConstraints();
		gridBagConstraints.gridy = 1;
		add(textField_ip, gridBagConstraints);

		initGridBagConstraints();
		gridBagConstraints.gridy = 2;
		add(textField_login, gridBagConstraints);

		initGridBagConstraints();
		gridBagConstraints.gridy = 3;
		add(textField_password, gridBagConstraints);

		initGridBagConstraints();
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.BASELINE_TRAILING;
		add(button_apply, gridBagConstraints);

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
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == button_apply) {
			try {

				if (!textField_base.getText().isEmpty() && !textField_ip.getText().isEmpty() && !textField_login.getText().isEmpty() && !textField_password.getText().isEmpty()) {
					String base = textField_base.getText();
					String ip = textField_ip.getText();
					String login = textField_login.getText();
					String password = textField_password.getText();

					SQLServer mysql = new SQLServer(ip, base, login, password);
					this.mysql = mysql;
					
					setVisible(false);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erreur", "Warning !",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	@Override
	public void focusGained(FocusEvent event) {
		if (event.getSource() == textField_base) {
			if (textField_base.getText().equals("Base") && textField_base.getForeground().equals(Color.GRAY)) {
				textField_base.setText("");
				textField_base.setForeground(Color.BLACK);
			}
		}

		if (event.getSource() == textField_ip) {
			if (textField_ip.getText().equals("Adresse IP") && textField_ip.getForeground().equals(Color.GRAY)) {
				textField_ip.setText("");
				textField_ip.setForeground(Color.BLACK);
			}
		}

		if (event.getSource() == textField_login) {
			if (textField_login.getText().equals("Login") && textField_login.getForeground().equals(Color.GRAY)) {
				textField_login.setText("");
				textField_login.setForeground(Color.BLACK);
			}
		}

		if (event.getSource() == textField_password) {
			if (textField_password.getText().equals("Mot de passe") && textField_password.getForeground().equals(Color.GRAY)) {
				textField_password.setText("");
				textField_password.setForeground(Color.BLACK);
			}
		}
	}

	@Override
	public void focusLost(FocusEvent event) {
		if (event.getSource() == textField_base) {
			if (textField_base.getText().isEmpty()) {
				textField_base.setForeground(Color.GRAY);
				textField_base.setText("Base");
			}
		}

		if (event.getSource() == textField_ip) {
			if (textField_ip.getText().isEmpty()) {
				textField_ip.setForeground(Color.GRAY);
				textField_ip.setText("Adresse IP");
			}
		}

		if (event.getSource() == textField_login) {
			if (textField_login.getText().isEmpty()) {
				textField_login.setForeground(Color.GRAY);
				textField_login.setText("Login");
			}
		}

		if (event.getSource() == textField_password) {
			if (textField_password.getText().isEmpty()) {
				textField_password.setForeground(Color.GRAY);
				textField_password.setText("Mot de passe");
			}
		}
	}
}
