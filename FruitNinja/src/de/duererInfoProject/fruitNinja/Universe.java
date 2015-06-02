package de.duererInfoProject.fruitNinja;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Universe extends JPanel {
	private Game game;
	
	public Universe (Game g) {
		super();
		game = g;
	}
	
	//Override paint Method to also paint the game
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		//Paint parts
	}
}
