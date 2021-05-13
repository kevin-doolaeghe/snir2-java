package fr.doolaeghe.tp_afficheur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Window extends JFrame {
	private AfficheurDM110 afficheur;
	
	private Box ligne1, ligne2, ligne3, colonne1;
	
	private JButton bt_eff, bt_gotoXY, bt_write, bt_affHeure;
	private JTextArea ta_text;
	@SuppressWarnings("rawtypes")
	private JComboBox cb_x, cb_y;
	private JLabel label_titre;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Window() throws IOException {
		afficheur = new AfficheurDM110();
		
		ligne1 = Box.createHorizontalBox();
		ligne2 = Box.createHorizontalBox();
		ligne3 = Box.createHorizontalBox();
		colonne1 = Box.createVerticalBox();
		
		bt_eff = new JButton("Effacer");
		bt_gotoXY = new JButton("Aller aux coord.");
		bt_write = new JButton("Ecrire");
		bt_affHeure = new JButton("Afficher heure");
		ta_text = new JTextArea();
		cb_x = new JComboBox();
		cb_y = new JComboBox();
		for(int i = 1; i < 21; i++) cb_x.addItem(Integer.toString(i));
		cb_y.addItem("1");
		cb_y.addItem("2");
		label_titre = new JLabel("DM110");
		
		//------------------------------
		
		this.setTitle("DM110");
		this.setSize(720, 480);
		//this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//------------------------------
		
		ligne1.add(label_titre);
		ligne1.add(bt_eff);
		ligne1.add(bt_affHeure);
		
		ligne2.add(cb_x);
		ligne2.add(cb_y);
		ligne2.add(bt_gotoXY);
		
		ligne3.add(ta_text);
		ligne3.add(bt_write);
		
		colonne1.add(ligne1);
		colonne1.add(ligne2);
		colonne1.add(ligne3);
		
		this.setContentPane(colonne1);
		
		//------------------------------
		
		bt_eff.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					afficheur.clearScreen();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		bt_gotoXY.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					afficheur.gotoXY((cb_x.getSelectedIndex() + 1), (cb_y.getSelectedIndex() + 1));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		bt_write.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					afficheur.write(ta_text.getText());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		bt_affHeure.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					afficheur.setTime();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		//------------------------------
		
		this.setVisible(true);
	}
}
