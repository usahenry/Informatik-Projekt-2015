package de.duererInfoProject.fruitNinja;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Universe extends JPanelBG {
	
	private Controller controller;
	
	public Universe (Controller g, String img) {
		super(img);
		controller = g;
		setLayout(null);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				controller.getGui().pauseUniverse();
			}
		});
		lblNewLabel.setIcon(new ImageIcon(Universe.class.getResource("/de/duererInfoProject/fruitNinja/img/pause-icon.png")));
		lblNewLabel.setBounds(10, 11, 48, 48);
		add(lblNewLabel);
		
		
		
		JLabel pmgrn = new JLabel("");
		Icon pmgrnNormal = new ImageIcon(Universe.class.getResource("/de/duererInfoProject/fruitNinja/img/Pomegranate.png"));
		Icon pmgrnSplit = new ImageIcon(Universe.class.getResource("/de/duererInfoProject/fruitNinja/img/PomegranateSplit.png"));
		pmgrn.setIcon(pmgrnNormal);
		
		pmgrn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				pmgrn.setIcon(pmgrnSplit);
			}
		});
		
		pmgrn.setBounds(50, 50, 200, 200);
		add(pmgrn);

		
	}
	
	//Override paint Method to also paint the game
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
//		img = ImageIO.read(new File("Pomegrenate.png"));
//		g2d.drawString("Hello", 10, 10);
		//Paint parts
	}
}
