package doolaeghe_tp_app_bonjour;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Window extends JFrame {
	private Box ligne1, ligne2, colonne1;
	
	private JButton bt;
	private JLabel label;

	public Window() throws IOException {
		
		ligne1 = Box.createHorizontalBox();
		ligne2 = Box.createHorizontalBox();
		colonne1 = Box.createVerticalBox();
		
		bt = new JButton("button");
		label = new JLabel("label");
		
		//------------------------------
		
		this.setTitle("Hello World");
		this.setSize(720, 480);
		//this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//------------------------------
		
		ligne1.add(label);
		ligne2.add(bt);
		
		colonne1.add(ligne1);
		colonne1.add(ligne2);
		
		this.setContentPane(colonne1);
		
		//------------------------------
		
		bt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				label.setText("Hello World !");
			}
		});
		
		//------------------------------
		
		this.setVisible(true);
	}
}
