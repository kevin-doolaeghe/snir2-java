package window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.ClientMaker;
import client.ClientPanel;
import client.ClientSettings;
import server.ServerMaker;
import server.ServerPanel;
import server.ServerSettings;

@SuppressWarnings("serial")
public class Window extends JFrame implements ActionListener, ListSelectionListener {
	private List<ClientPanel> clients;
	private List<ServerPanel> servers;

	private JList<ClientPanel> list_editGroupClient, list_removeGroupClient;
	private JList<ServerPanel> list_editGroupServer, list_removeGroupServer;
	private JMenu menu_file, menu_edit, menu_help, menu_sub_editGroup, menu_sub_removeGroup;
	private JMenuBar menuBar;
	private JMenuItem menuItem_leave, menuItem_createGroup, menuItem_joinGroup, menuItem_help, menuItem_about;
	private JTabbedPane tabbedPane;

	public Window() {
		clients = new ArrayList<ClientPanel>();
		servers = new ArrayList<ServerPanel>();

		init();
	}

	public void init() {
		list_editGroupClient = new JList<ClientPanel>();
		list_removeGroupClient = new JList<ClientPanel>();
		list_editGroupServer = new JList<ServerPanel>();
		list_removeGroupServer = new JList<ServerPanel>();

		menu_file = new JMenu();
		menu_edit = new JMenu();
		menu_help = new JMenu();
		menu_sub_editGroup = new JMenu();
		menu_sub_removeGroup = new JMenu();

		menuBar = new JMenuBar();

		menuItem_leave = new JMenuItem();
		menuItem_createGroup = new JMenuItem();
		menuItem_joinGroup = new JMenuItem();
		menuItem_help = new JMenuItem();
		menuItem_about = new JMenuItem();

		tabbedPane = new JTabbedPane();

		this.setTitle("Chat");
		this.setSize(720, 480);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(menuBar);
		this.add(tabbedPane);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				for (ClientPanel client : clients) {
					client.terminate();
				}

				for (ServerPanel server : servers) {
					server.terminate();
				}

				dispose();
				System.exit(0);
			}
		});

		list_editGroupClient.addListSelectionListener(this);
		list_removeGroupClient.addListSelectionListener(this);
		list_editGroupServer.addListSelectionListener(this);
		list_removeGroupServer.addListSelectionListener(this);

		menu_file.setText("Fichier");
		menu_edit.setText("Editer");
		menu_help.setText("?");
		menu_sub_editGroup.setText("Editer un groupe");
		menu_sub_removeGroup.setText("Supprimer un groupe");

		menuItem_leave.setText("Quitter");
		menuItem_leave.addActionListener(this);
		menuItem_createGroup.setText("Créer un groupe");
		menuItem_createGroup.addActionListener(this);
		menuItem_joinGroup.setText("Rejoindre un groupe");
		menuItem_joinGroup.addActionListener(this);
		menuItem_help.setText("Aide");
		menuItem_help.addActionListener(this);
		menuItem_about.setText("A propos");
		menuItem_about.addActionListener(this);

		menu_file.add(menuItem_leave);

		menu_edit.add(menuItem_createGroup);
		menu_edit.add(menuItem_joinGroup);
		menu_edit.add(new JSeparator());
		menu_edit.add(menu_sub_editGroup);
		menu_edit.add(menu_sub_removeGroup);

		menu_help.add(menuItem_help);
		menu_help.add(menuItem_about);

		menu_sub_editGroup.add(list_editGroupClient);
		menu_sub_editGroup.add(new JSeparator());
		menu_sub_editGroup.add(list_editGroupServer);
		menu_sub_removeGroup.add(list_removeGroupClient);
		menu_sub_removeGroup.add(new JSeparator());
		menu_sub_removeGroup.add(list_removeGroupServer);

		menuBar.add(menu_file);
		menuBar.add(menu_edit);
		menuBar.add(menu_help);

		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == menuItem_leave) {
			for (ClientPanel client : clients) {
				client.terminate();
			}

			for (ServerPanel server : servers) {
				server.terminate();
			}

			dispose();
			System.exit(0);
		}

		if (event.getSource() == menuItem_createGroup) {
			int nb = servers.size();

			new ServerMaker(servers, tabbedPane);

			if (nb < servers.size()) {
				ServerPanel[] list = new ServerPanel[servers.size()];
				list = servers.toArray(list);
				list_editGroupServer.setListData(list);
				list_removeGroupServer.setListData(list);
			}
		}

		if (event.getSource() == menuItem_joinGroup) {
			int nb = clients.size();

			new ClientMaker(clients, tabbedPane);

			if (nb < clients.size()) {
				ClientPanel[] list = new ClientPanel[clients.size()];
				list = clients.toArray(list);
				list_editGroupClient.setListData(list);
				list_removeGroupClient.setListData(list);
			}
		}

		if (event.getSource() == menuItem_help) {
			JOptionPane.showMessageDialog(null, "Chat de discussion", "Aide", JOptionPane.INFORMATION_MESSAGE);
		}

		if (event.getSource() == menuItem_about) {
			JOptionPane.showMessageDialog(null, "Déc. 2018 | Kevin Doolaeghe", "A propos",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent event) {
		if (!event.getValueIsAdjusting()) {
			if (event.getSource() == list_editGroupClient) {
				ClientPanel client = list_editGroupClient.getSelectedValue();
				new ClientSettings(client);
			}

			if (event.getSource() == list_removeGroupClient) {
				ClientPanel client = list_removeGroupClient.getSelectedValue();

				clients.remove(client);
				client.terminate();

				tabbedPane.remove(client);
				ClientPanel[] list = new ClientPanel[clients.size()];
				list = clients.toArray(list);
				list_editGroupClient.setListData(list);
				list_removeGroupClient.setListData(list);
			}

			if (event.getSource() == list_editGroupServer) {
				ServerPanel server = list_editGroupServer.getSelectedValue();
				new ServerSettings(server);
			}

			if (event.getSource() == list_removeGroupServer) {
				ServerPanel server = list_removeGroupServer.getSelectedValue();

				servers.remove(server);
				server.terminate();
				
				tabbedPane.remove(server);
				ServerPanel[] list = new ServerPanel[servers.size()];
				list = servers.toArray(list);
				list_editGroupServer.setListData(list);
				//list_removeGroupServer.setListData(list);
			}
		}
	}
}
