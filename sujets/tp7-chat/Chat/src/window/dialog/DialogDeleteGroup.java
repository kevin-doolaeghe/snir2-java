package window.dialog;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class DialogDeleteGroup extends JDialog implements ActionListener {
	private JFrame frame;
	
	public DialogDeleteGroup(JFrame frame) {
		this.frame = frame;
		
		// ====================
		
		this.setTitle("Créer un groupe");
		this.setSize(350, 270);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		this.getContentPane().setLayout(new GridBagLayout());

		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
