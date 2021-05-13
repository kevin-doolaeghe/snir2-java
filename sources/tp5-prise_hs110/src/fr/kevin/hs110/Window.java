package fr.kevin.hs110;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class Window extends JFrame implements ActionListener {
	private Box line, column;
	
	private JButton button_relayOn, button_relayOff, button_info, button_measures;
	private JTextField textField_ip, textField_info, textField_measures;
	private JLabel label_ip;
	private JScrollPane scrollPane_info, scrollPane_measures;
	
	public Window() {
		this.setTitle("Horloge");
		this.setSize(720, 480);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		button_relayOff = new JButton("Relay Off");
		button_relayOn = new JButton("Relay On");
		button_info = new JButton("Get infos");
		button_measures = new JButton("Get measures");
		textField_ip = new JTextField();
		textField_info = new JTextField();
		textField_measures = new JTextField();
		label_ip = new JLabel("IP address :");
		scrollPane_info = new JScrollPane(textField_info);
		scrollPane_measures = new JScrollPane(textField_measures);
		
		textField_ip.setMaximumSize(new Dimension(500, 20));
		textField_ip.setText("172.16.66.190");
		scrollPane_info.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane_measures.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		//****************************************
		
		line = Box.createHorizontalBox();
		
		column = Box.createVerticalBox();
		column.add(label_ip);
		column.add(textField_ip);
		line.add(column);
		
		column = Box.createVerticalBox();
		column.add(button_relayOn);
		column.add(button_relayOff);
		line.add(column);
		
		this.getContentPane().add(line);
		
		//****************************************
		
		line = Box.createHorizontalBox();
		
		column = Box.createVerticalBox();
		column.setAlignmentX(CENTER_ALIGNMENT);
		column.add(button_info);
		column.add(scrollPane_info);
		line.add(column);
		
		this.getContentPane().add(line);
		
		//****************************************
		
		line = Box.createHorizontalBox();
		
		column = Box.createVerticalBox();
		column.add(button_measures);
		column.add(scrollPane_measures);
		line.add(column);
		
		this.getContentPane().add(line);
		
		//****************************************
		
		button_relayOff.addActionListener(this);
		button_relayOn.addActionListener(this);
		button_info.addActionListener(this);
		button_measures.addActionListener(this);
		
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		try {
			if(event.getSource() == button_relayOff) {
				HS110 prise = new HS110(textField_ip.getText());
				prise.relayOff();
				prise.finalize();
			}
			
			if(event.getSource() == button_relayOn) {
				HS110 prise = new HS110(textField_ip.getText());
				prise.relayOn();
				prise = new HS110(textField_ip.getText());
				prise.finalize();
			}
			
			if(event.getSource() == button_info) {
				HS110 prise = new HS110(textField_ip.getText());
				prise.relayOn();
				prise = new HS110(textField_ip.getText());
				
				textField_info.setText(prise.getInfos());
				
				prise.finalize();
			}
			
			if(event.getSource() == button_measures) {
				HS110 prise = new HS110(textField_ip.getText());
				prise.relayOn();
				prise = new HS110(textField_ip.getText());

				textField_measures.setText(prise.getMeasures());
				
				prise.finalize();
			}
		} catch (IOException e) {
			System.out.println("Impossible de rejoindre la prise");
		}
	}
}
