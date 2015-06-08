package de.duererInfoProject.fruitNinja;

public class Fruit extends Item {
	
	private int x, y, speedX, speedY;
	private Universe universe;
	
	public Fruit(int x, int y, int speedX, int speedY, Universe universe) {
		this.x = x;
		this.y = y;
		this.speedX = speedX;
		this.speedY = speedY;
		this.universe = universe;
	}
}
