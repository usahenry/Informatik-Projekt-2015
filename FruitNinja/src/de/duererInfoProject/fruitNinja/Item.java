package de.duererInfoProject.fruitNinja;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Item {

	public int x, y, speedX;
	public double speedY;
	public Universe universe;
	
	public Item(int x, int y, int speedX, int speedY, Universe universe) {
		this.x = x;
		this.y = y;
		this.speedX = speedX;
		this.speedY = speedY;
		this.universe = universe;
	}
	
	public void move() {
		speedY += universe.getGravity();
		x += speedX;
		y -= Math.round(speedY);
	}
	
	public void paint(Graphics2D g2d) {
		Color c = g2d.getColor();
		g2d.setColor(new Color(255, 255, 255));
		g2d.fillOval(x, y, 40, 40);
		g2d.setColor(c);
	}

}
