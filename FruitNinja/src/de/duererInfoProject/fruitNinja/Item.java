package de.duererInfoProject.fruitNinja;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public abstract class Item {

	public int x, y, speedX, mass;
	public double speedY;
	public Game game;
	public Universe universe;
	public boolean wasVisible;
	public Random random;
	public static final int ITEM_ID = 0;
	
	public Item(int x, Game game, Universe universe) {
		this.x = x;
		this.game = game;
		this.universe = universe;
		random = new Random();
		
		y = universe.getHeight() + 40;
		speedX = random.nextInt(5) + 2;
		if (x > universe.getWidth() / 2) speedX = -speedX;
		//TO-DO: Wahrscheinlichkeit nach links zu fliegen wird größer, je weiter rechts man ist!
		speedY = (random.nextInt(50) / 10) + 10;
		mass = 1 - random.nextInt(10) / 10;
		wasVisible = false;
	}
	
	public void move() {
		speedY += (universe.getGravity() * mass);
		x += speedX;
		y -= Math.round(speedY);
		if (!wasVisible) {
			if (onScreen()) {
				wasVisible = true;
			}
		} else {
			if (!onScreen()) {
				game.outOfScreen(this);
			}
		}
	}
	
	public void paint(Graphics2D g2d) {
		Color c = g2d.getColor();
		g2d.setColor(Color.WHITE);
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
