package window.frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import window.menu_bar.MenuBar;

@SuppressWarnings("serial")
public class Frame extends JFrame implements ActionListener {
	public JTabbedPane tabbedPane;

	public Frame() {
		this.setTitle("Chat");
		this.setSize(720, 480);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ====================

		tabbedPane = new JTabbedPane();

		// ====================

		this.setJMenuBar(new MenuBar(tabbedPane));

		this.add(tabbedPane);

		// ====================

		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
