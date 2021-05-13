package doolaeghe_tp_horloge;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class HorlogeGraphique extends JPanel implements ActionListener {
	private Timer timer;
	private JLabel label;

	public HorlogeGraphique() {
		this.setLayout(null);
		this.setMaximumSize(new Dimension(350, 380));
		this.setMinimumSize(new Dimension(350, 380));
		this.setSize(350, 380);
		this.setBackground(Color.lightGray);
		this.setBorder(BorderFactory.createLineBorder(Color.gray));
		
		//----------------------------
		
		label = new JLabel();
		
		label.setMaximumSize(new Dimension(350, 30));
		label.setMinimumSize(new Dimension(350, 30));
		label.setSize(350, 30);
		label.setBorder(BorderFactory.createDashedBorder(Color.gray));
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setLocation(0, 350);
		
		//----------------------------
		
		this.add(label);
		
		//----------------------------
		
		timer = new Timer(500, this);
		timer.start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
	
		//----------------------------
		
		Date date = new Date();
		@SuppressWarnings("deprecation")
		int hour = date.getHours();
		@SuppressWarnings("deprecation")
		int min = date.getMinutes();
		@SuppressWarnings("deprecation")
		int sec = date.getSeconds();
		
		//----------------------------
		double angle, x, y;
		int rayon = 150;
		int x_centre = (this.getWidth() - rayon * 2) / 2 + rayon;
		int y_centre = (this.getHeight() - 30 - rayon * 2) / 2 + rayon;
		
		for(int i = 0; i < 60; i++) {
			angle = Math.toRadians(i * 6) - Math.PI / 2;
			x = Math.cos(angle) * rayon;
			y = Math.sin(angle) * rayon;
			
			if(i % 5 == 0) {
				g2d.setColor(Color.red);
				if(i == 0) g2d.drawString("12", (float) x + x_centre, (float) y + y_centre);
				else g2d.drawString(Integer.toString(i / 5), (float) x + x_centre, (float) y + y_centre);
				
				g2d.setColor(Color.black);
			}
			else g2d.fillOval((int) x + x_centre, (int) y + y_centre, 3, 3);
		}
		
		//----------------------------
		
		angle = Math.toRadians(hour * 6) - Math.PI / 2;
		x = Math.cos(angle * 5) * rayon / 2.5;
		y = Math.sin(angle * 5) * rayon / 2.5;
		g2d.setColor(Color.green);
		g2d.drawLine(x_centre, y_centre, (int) x + x_centre, (int) y + y_centre);
		
		angle = Math.toRadians(min * 6) - Math.PI / 2;
		x = Math.cos(angle) * rayon / 1.5;
		y = Math.sin(angle) * rayon / 1.5;
		g2d.setColor(Color.yellow);
		g2d.drawLine(x_centre, y_centre, (int) x + x_centre, (int) y + y_centre);
		
		angle = Math.toRadians(sec * 6) - Math.PI / 2;
		x = Math.cos(angle) * rayon / 1.2;
		y = Math.sin(angle) * rayon / 1.2;
		g2d.setColor(Color.cyan);
		g2d.drawLine(x_centre, y_centre, (int) x + x_centre, (int) y + y_centre);
		
		g2d.setColor(Color.black);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		label.setText(format.format(date));
	}

}
