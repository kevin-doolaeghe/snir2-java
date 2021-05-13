package doolaeghe_tp_demineur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JToggleButton;

@SuppressWarnings("serial")
public class Case extends JToggleButton implements MouseListener {
	private int state;
	
	public Case() {
		state = 0;
		this.setSelected(false);
	}
	
	public void setState(int state) {
		this.state = state;
	}
	
	private void setText() {
		setText("edfzr");
	}

	@Override
	public void mouseClicked(MouseEvent button) {
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setText();
				
			}
		});
	}

	@Override
	public void mouseEntered(MouseEvent button) {
		
	}

	@Override
	public void mouseExited(MouseEvent button) {
		
	}

	@Override
	public void mousePressed(MouseEvent button) {
		
	}

	@Override
	public void mouseReleased(MouseEvent button) {
		
	}
}
