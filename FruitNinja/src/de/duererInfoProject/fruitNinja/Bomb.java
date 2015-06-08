package de.duererInfoProject.fruitNinja;

public class Bomb extends Item {
	
	private int x, y, speedX, speedY;
	private Universe universe;
	
	public Bomb(int x, int y, int speedX, int speedY, Universe universe) {
		this.x = x;
		this.y = y;
		this.speedX = speedX;
		this.speedY = speedY;
		this.universe = universe;
	}
}
