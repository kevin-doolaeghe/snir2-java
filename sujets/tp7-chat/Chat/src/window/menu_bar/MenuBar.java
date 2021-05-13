package window.menu_bar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;

import engine.chat.Client;
import engine.chat.Server;
import window.dialog.DialogDeleteGroup;
import window.dialog.DialogLeaveGroup;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar implements ActionListener {
	private JTabbedPane tabbedPane;
	
	public List<Server> servers;
	public List<Client> clients;
	
	// ====================
	
	private JMenu menu_file;

	private JMenu menu_edit;

	private JMenu menu_help;

	// ====================

	private JMenuItem menuItem_file_saveGroup;
	private JMenuItem menuItem_file_importGroup;
	private JMenuItem menuItem_file_leave;

	private JMenuItem menuItem_edit_createGroup;
	private JMenuItem menuItem_edit_deleteGroup;
	private JMenuItem menuItem_edit_editGroupSettings;
	private JMenuItem menuItem_edit_editGroupUsers;
	private JMenuItem menuItem_edit_joinGroup;
	private JMenuItem menuItem_edit_leaveGroup;

	private JMenuItem menuItem_help_about;

	public MenuBar(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
		
		servers = new ArrayList<Server>();
		clients = new ArrayList<Client>();
		
		// ====================
		
		menu_file = new JMenu("Fichier");

		menu_edit = new JMenu("Editer");

		menu_help = new JMenu("?");

		// ====================

		menuItem_file_saveGroup = new JMenuItem("Sauvegarder un groupe");
		menuItem_file_importGroup = new JMenuItem("Importer un groupe");
		menuItem_file_leave = new JMenuItem("Quitter");

		menuItem_edit_createGroup = new JMenuItem("Créer un groupe");
		menuItem_edit_deleteGroup = new JMenuItem("Supprimer un groupe");
		menuItem_edit_editGroupSettings = new JMenuItem("Editer les paramètres d'un groupe");
		menuItem_edit_editGroupUsers = new JMenuItem("Editer les utilisateurs d'un groupe");
		menuItem_edit_joinGroup = new JMenuItem("Rejoindre un groupe");
		menuItem_edit_leaveGroup = new JMenuItem("Quitter un groupe");

		menuItem_help_about = new JMenuItem("A propos");

		// ====================

		menu_file.add(menuItem_file_saveGroup);
		menu_file.add(menuItem_file_importGroup);
		menu_file.add(new JSeparator());
		menu_file.add(menuItem_file_leave);

		menu_edit.add(menuItem_edit_createGroup);
		menu_edit.add(menuItem_edit_deleteGroup);
		menu_edit.add(menuItem_edit_editGroupSettings);
		menu_edit.add(menuItem_edit_editGroupUsers);
		menu_edit.add(new JSeparator());
		menu_edit.add(menuItem_edit_joinGroup);
		menu_edit.add(menuItem_edit_leaveGroup);

		menu_help.add(menuItem_help_about);

		// ====================

		this.add(menu_file);
		this.add(menu_edit);
		this.add(menu_help);

		// ====================

		menuItem_file_saveGroup.addActionListener(this);
		menuItem_file_importGroup.addActionListener(this);
		menuItem_file_leave.addActionListener(this);

		menuItem_edit_createGroup.addActionListener(this);
		menuItem_edit_deleteGroup.addActionListener(this);
		menuItem_edit_editGroupSettings.addActionListener(this);
		menuItem_edit_editGroupUsers.addActionListener(this);
		menuItem_edit_joinGroup.addActionListener(this);
		menuItem_edit_leaveGroup.addActionListener(this);

		menuItem_help_about.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == menuItem_file_saveGroup) {

		}

		if (e.getSource() == menuItem_file_importGroup) {

		}

		if (e.getSource() == menuItem_file_leave) {
			System.exit(0);
		}

		// ====================

		if (e.getSource() == menuItem_edit_createGroup) {
			Server server = new Server();
			servers.add(server);
			tabbedPane.addTab("server", server);
		}

		if (e.getSource() == menuItem_edit_deleteGroup) {
			
		}

		if (e.getSource() == menuItem_edit_editGroupSettings) {
			servers.get(0).editHosts();
		}

		if (e.getSource() == menuItem_edit_editGroupUsers) {
			
		}

		if (e.getSource() == menuItem_edit_joinGroup) {
			Client client = new Client();
			clients.add(client);
			tabbedPane.addTab(client.getName(), client);
		}

		if (e.getSource() == menuItem_edit_leaveGroup) {
			
		}

		// ====================

		if (e.getSource() == menuItem_help_about) {

		}
	}
}
