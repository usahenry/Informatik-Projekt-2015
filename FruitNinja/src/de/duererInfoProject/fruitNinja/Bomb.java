package de.duererInfoProject.fruitNinja;

import java.awt.Color;
import java.awt.Graphics2D;

public class Bomb extends Item {

	public static final int ItemTypeID = 2;
	
	public Bomb(int x, Game game, Universe universe) {
		super(x, game, universe);
	}
	
	//Overrides Items paint method to draw the bomb red
	public void paint(Graphics2D g2d) {
		Color c = g2d.getColor();
		g2d.setColor(Color.RED);
		g2d.fillOval(x, y, 50, 50);
		g2d.setColor(c);
	}
	
	public int getItemTypeID() {
		return ItemTypeID;
	}
}
