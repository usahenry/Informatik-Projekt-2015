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

	public int x, y, speedX, mass;
	public double speedY;
	public Game game;
	public Universe universe;
	public boolean wasVisible;
	public Random random;
	public static final int ItemTypeID = 0; //Used to identify the type of an Item
	
	public Item(int x, Game game, Universe universe) {
		//##### ### ##  ###
		//  #   # # # # # #  Koordinaten in float
		//  #   # # # # # #
		//  #   ### ##  ###
		//TO-DO: Wahrscheinlichkeit nach links zu fliegen wird größer, je weiter rechts man ist!
		
		//Initializing attributes
		this.x = x;
		this.game = game;
		this.universe = universe;
		random = new Random();
		
		y = universe.getHeight() + 40;
		speedX = random.nextInt(5) + 2;
		if (x > universe.getWidth() / 2) speedX = -speedX;
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
	
	//Called by Game if the Item gets hit
	//Creates and returns 2 ItemParts at the current position
	public LinkedList<ItemPart> createItemParts() {
		LinkedList<ItemPart> return_list = new LinkedList<ItemPart>();
		return_list.add(new ItemPart(x, y, true, game, universe));
		return_list.add(new ItemPart(x + 25, y, false, game, universe));
		return return_list;
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
