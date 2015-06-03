package de.duererInfoProject.fruitNinja;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Universe extends JPanelBG {
	private Game game;
	
	public Universe (Game g, String img) {
		super(img);
		game = g;
		setLayout(null);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				game.getGui().pauseUniverse();
			}
		});
		lblNewLabel.setIcon(new ImageIcon(Universe.class.getResource("/de/duererInfoProject/fruitNinja/img/pause-icon.png")));
		lblNewLabel.setBounds(10, 11, 48, 48);
		add(lblNewLabel);
	}
	
	//Override paint Method to also paint the game
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		//Paint parts
	}
}
