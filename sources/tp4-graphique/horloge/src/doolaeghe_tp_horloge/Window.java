package doolaeghe_tp_horloge;

import javax.swing.Box;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame {
	private Box colonne1;
	
	private HorlogeGraphique graphClock;

	public Window() {
		colonne1 = Box.createVerticalBox();
		
		graphClock = new HorlogeGraphique();
		
		//------------------------------
		
		this.setTitle("Horloge");
		this.setSize(720, 480);
		//this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//------------------------------
		
		colonne1.add(graphClock);
		
		this.setContentPane(colonne1);
		
		//------------------------------
		
		this.setVisible(true);
	}
}
