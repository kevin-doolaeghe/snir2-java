package window.frame;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import engine.chat.Client;
import engine.chat.Server;

@SuppressWarnings("serial")
public class Window extends JFrame implements ActionListener {
	private JMenuBar menuBar;
	
	private JMenu menu_application;
	private JMenu menu_serveurs;
	private JMenu menu_aide;
	
	private JMenuItem menuItem_application_quitter;
	private JMenuItem menuItem_serveurs_creerServeur;
	private JMenuItem menuItem_serveurs_supprimerServeur;
	private JMenuItem menuItem_serveurs_rejoindreServeur;
	private JMenuItem menuItem_aide_aPropos;
	
private JTabbedPane tabbedPane;
	
	private JPanel panel_current;
	
	private GridBagConstraints gridBagConstraints;
	
	private JLabel label_message;
	
	private JButton button_envoyer;
	
	private JTextArea textArea_message;
	private JTextArea textArea_discussion;
	
	private JScrollPane scrollPane_message;
	private JScrollPane scrollPane_discussion;
	
	private List<Server> serveurs;
	private List<Client> clients;
	
	public Window() {
		//Frame
		
		this.setTitle("Chat");
		this.setSize(720, 480);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//MenuBar
		
		menuBar = new JMenuBar();
		 
		menu_application = new JMenu("Fichier");
		menu_serveurs = new JMenu("Serveurs");
		menu_aide = new JMenu("?");
 
		menuItem_application_quitter = new JMenuItem("Quitter");
		menuItem_serveurs_creerServeur = new JMenuItem("Créer un serveur");
		menuItem_serveurs_supprimerServeur = new JMenuItem("Supprimer un serveur");
		menuItem_serveurs_rejoindreServeur = new JMenuItem("Rejoindre un serveur");
		menuItem_aide_aPropos = new JMenuItem("A propos");
		
		menu_application.add(menuItem_application_quitter);
		menu_serveurs.add(menuItem_serveurs_creerServeur);
		menu_serveurs.add(menuItem_serveurs_supprimerServeur);
		menu_serveurs.add(new JSeparator());
		menu_serveurs.add(menuItem_serveurs_rejoindreServeur);
		menu_aide.add(menuItem_aide_aPropos);
		
		menuBar.add(menu_application);
		menuBar.add(menu_serveurs);
		menuBar.add(menu_aide);
 
		this.setJMenuBar(menuBar);
		
		
		
		//TabbedPane
		
				tabbedPane = new JTabbedPane();
				
				panel_current = new JPanel();
				
				gridBagConstraints = new GridBagConstraints();
				
				label_message = new JLabel("Message :");
				
				button_envoyer = new JButton("Envoyer");

				textArea_message = new JTextArea();
				textArea_discussion = new JTextArea();
				
				scrollPane_message = new JScrollPane(textArea_message);
				scrollPane_discussion = new JScrollPane(textArea_discussion);
				
				
				
				tabbedPane.addTab("Current", panel_current);
				
				panel_current.setLayout(new GridBagLayout());
				
				textArea_discussion.setEditable(false);
				
				//scrollPane_message.setPreferredSize(new Dimension(999, 20));
				scrollPane_message.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				scrollPane_message.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
				
				//scrollPane_discussion.setPreferredSize(scrollPane_discussion.getSize());
				scrollPane_discussion.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				scrollPane_discussion.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
				
				

				int insets = 12;
				gridBagConstraints.insets = new Insets(insets, insets, insets, insets);
				
				gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
				gridBagConstraints.anchor = GridBagConstraints.BASELINE;
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = 0;
				gridBagConstraints.weightx = 0.1;
				gridBagConstraints.weighty = 0.1;
				panel_current.add(label_message, gridBagConstraints);
				
				gridBagConstraints.fill = GridBagConstraints.BOTH;
				gridBagConstraints.gridx = 1;
				gridBagConstraints.gridy = 0;
				gridBagConstraints.weightx = 0.8;
				gridBagConstraints.gridwidth = 3;
				panel_current.add(scrollPane_message, gridBagConstraints);
				
				gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
				gridBagConstraints.gridx = 4;
				gridBagConstraints.gridy = 0;
				gridBagConstraints.weightx = 0.1;
				gridBagConstraints.gridwidth = 1;
				panel_current.add(button_envoyer, gridBagConstraints);
				
				gridBagConstraints.fill = GridBagConstraints.BOTH;
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = 1;
				gridBagConstraints.gridwidth = 5;
				gridBagConstraints.weighty = 0.9;
				panel_current.add(scrollPane_discussion, gridBagConstraints);
				
				
				
				this.getContentPane().add(tabbedPane);
				
				
				
				button_envoyer.addActionListener(this);
		
		
		
		menuItem_application_quitter.addActionListener(this);
		menuItem_serveurs_creerServeur.addActionListener(this);
		menuItem_serveurs_supprimerServeur.addActionListener(this);
		menuItem_serveurs_rejoindreServeur.addActionListener(this);
		menuItem_aide_aPropos.addActionListener(this);
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == menuItem_application_quitter) {
			System.exit(0);
		}
		
		if(event.getSource() == menuItem_serveurs_creerServeur) {
			
		}
		
		if(event.getSource() == menuItem_serveurs_supprimerServeur) {
			
		}
		
		if(event.getSource() == menuItem_serveurs_rejoindreServeur) {
			JDialog dialog_rejoindreServeur = new JDialog(this, "Rejoindre un serveur", true);
			
			dialog_rejoindreServeur.setLocationRelativeTo(null);
			dialog_rejoindreServeur.setSize(350, 270);
			dialog_rejoindreServeur.setResizable(false);
			
			dialog_rejoindreServeur.getContentPane().setLayout(new GridBagLayout());
			
			dialog_rejoindreServeur.setVisible(true);
		}
		
		if(event.getSource() == menuItem_aide_aPropos) {
			JOptionPane.showMessageDialog(this, "Auteur : Kevin Doolaeghe", "A propos", JOptionPane.INFORMATION_MESSAGE);
		}
		
		
		if(event.getSource() == button_envoyer) {
			String message = textArea_message.getText();
			
			if(!message.isEmpty()) {
				String date = new SimpleDateFormat("dd/MM/yy - HH mm ss").format(new Date());
				
				textArea_discussion.append(date + " - Me : " + message + "\n");
				
				textArea_message.setText(null);
			} else {
				JOptionPane.showMessageDialog(this, "Message vide", "Impossible d'envoyer le message", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}
