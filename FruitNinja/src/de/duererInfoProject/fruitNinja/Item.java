package de.duererInfoProject.fruitNinja;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Random;

//Template for all other Items
public abstract class Item {

	public int x, y, speedX;
	public double speedY, mass;
	public Game game;
	public Universe universe;
	public boolean wasVisible;
	public Random random;
	public static final int ItemTypeID = 0; //Used to identify the type of an Item
	
	public Item(int x, Game game, Universe universe) {
		//Initializing attributes
		this.x = x;
		this.game = game;
		this.universe = universe;
		random = new Random();
		
		y = universe.getHeight() + 40;
		speedX = random.nextInt(5) + 2;
		if (changeDirection()) speedX = -speedX;
		speedY = (random.nextInt(50) / 10) + 10;
		mass = 1 - random.nextInt(10) / 10;
		wasVisible = false;
	}
	
	//Alternative constructor used by ItemParts
	public Item(int x, int y, boolean leftSide, Game game, Universe universe) {
		this.x = x;
		this.y = y;
		random = new Random();
		this.game = game;
		this.universe = universe;
		speedX = random.nextInt(5) + 2;
		if (leftSide) speedX = -speedX;
		speedY = 0;
		mass = 1 - random.nextInt(10) / 10;
		wasVisible = true;
	}
	
	//Called in every universe.repaint()
	//Calculates speedY change by gravity and moves the Item, checks if Item is still on the screen
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
				game.itemOutOfScreen(this);
			}
		}
	}
	
	//Called in every universe.repaint()
	//Paints the Item on the screen
	public void paint(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.fillOval(x, y, 50, 50);
	}
	
	public double getSpeedY() {
		return speedY;
	}
	
	public int getSpeedX() {
		return speedX;
	}
	
	//Choses randomly if the Item will have to change it's direction
	public boolean changeDirection() {
		double xMid = universe.getWidth() / 2;
		double chance = Math.abs((0.5 / ((universe.getWidth() - (game.SPAWN_BORDER * 1.9)) - xMid)) * (x - xMid)) + 0.5; //Chance the Item will fly towards the side it didn't spawn on, 50 % in the middle -> 100 % at 1.9 * SPAWN_BORDER distance from the border
		chance = Math.round(chance * 100);
		boolean otherSide = random.nextInt(100) <= chance;
		boolean onLeftSide = x < universe.getWidth() / 2;
		
		if (onLeftSide && !otherSide) return true;
		else if (!onLeftSide && otherSide) return true;
		else return false;
	}
	
	//Called by Game if the Item gets hit
	//Creates and returns 2 ItemParts at the current position
	public LinkedList<ItemPart> createItemParts() {
		return null;
	}
	
	//Called in every universe.repaint()
	//Checks if any @points are in the current bounds of the Item
	public void checkHit(LinkedList<Point> cursorBoints) {
		Ellipse2D.Double circle = new Ellipse2D.Double(x, y, 50, 50);
		for (Point p : cursorBoints) {
			if (circle.contains((Point2D) p)) {
				game.hit(this);
				return;
			}
		}
	}
	
	public int getItemTypeID() {
		return ItemTypeID;
	}
	
	//Checks if the Item is visible on the screen
	public boolean onScreen() {
		return (x > -50 && x < universe.getWidth() && y > -50 && y < universe.getHeight());
	}
}
