package sql;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class Window extends JFrame implements ActionListener {
	private SQLServer mysql;
	
	private JButton button_getData;
	private JTable table_data;
	private JMenu menu;
	private JMenuBar menuBar;
	private JMenuItem menuItem_leave;
	private JPanel panel;
	private JTextField textField_table;
	
	public Window() {
		new SQLConnecter(mysql);
		
		init();
	}
	
	public void init() {
		button_getData = new JButton();
		table_data = new JTable();
		menu = new JMenu();
		menuBar = new JMenuBar();
		menuItem_leave = new JMenuItem();
		panel = new JPanel();
		textField_table = new JTextField();

		this.setTitle("SQL");
		this.setSize(720, 480);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(menuBar);
		this.add(panel);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				mysql.terminate();

				dispose();
				System.exit(0);
			}
		});
		
		button_getData.setText("Demander les données");
		button_getData.addActionListener(this);
		
		textField_table.setText("admin");

		menu.setText("Menu");

		menuItem_leave.setText("Quitter");
		menuItem_leave.addActionListener(this);

		menu.add(menuItem_leave);

		menuBar.add(menu);
		
		panel.add(textField_table);
		panel.add(button_getData);
		panel.add(table_data);

		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == button_getData) {
			try {
				ResultSet result = mysql.getData(textField_table.getText());
				
				if(result != null) {

					String column[] = { "id", "login", "password" };
					String content[][] = new String[100][3];
					
					int i = 0;
					while (result.next()) {
						int id = result.getInt("id");
						String login = result.getString("login");
						String password = result.getString("password");
						
						content[i][0] = id + "";
						content[i][1] = login;
						content[i][2] = password;
						i++;
					}
				   
					DefaultTableModel model = new DefaultTableModel(content, column);
					table_data = new JTable(model);
					table_data.setShowGrid(true);
					table_data.setShowVerticalLines(true);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(event.getSource() == menuItem_leave) {
			mysql.terminate();
			
			dispose();
			System.exit(0);
		}
	}
}
