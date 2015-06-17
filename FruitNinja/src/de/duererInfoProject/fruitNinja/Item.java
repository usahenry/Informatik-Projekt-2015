package de.duererInfoProject.fruitNinja;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Item {

	public int x, y, speedX;
	public double speedY;
	public Game game;
	public Universe universe;
	public boolean wasOnScreen;
	
	public Item(int x, int y, int speedX, double speedY, Game game, Universe universe) {
		this.x = x;
		this.y = y;
		this.speedX = speedX;
		this.speedY = speedY;
		this.game = game;
		this.universe = universe;
		wasOnScreen = false;
	}
	
	public void move() {
		speedY += universe.getGravity();
		x += speedX;
		y -= Math.round(speedY);
		if (!onScreen()) {
			//SO machen das beim ersten auf bildschirm wasOnScreen true wird!
			game.outOfScreen(this);
		}
	}
	
	public void paint(Graphics2D g2d) {
		Color c = g2d.getColor();
		g2d.setColor(new Color(255, 255, 255));
		g2d.fillOval(x, y, 40, 40);
		g2d.setColor(c);
	}
	
	public double getSpeedY() {
		return speedY;
	}
	
	public int getSpeedX() {
		return speedX;
	}
	
	public boolean onScreen() {
		if (x > -50 && x > universe.getWidth() && y > -50 && y < universe.getHeight()) {
			return true;
		} else {
			return false;
		}
	}
}
