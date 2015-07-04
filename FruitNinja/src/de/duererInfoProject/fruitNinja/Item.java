package de.duererInfoProject.fruitNinja;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.Random;

public abstract class Item {

	public int x, y, speedX, mass;
	public double speedY;
	public Game game;
	public Universe universe;
	public boolean wasVisible;
	public Random random;
	public static final int ItemTypeID = 0;
	
	public Item(int x, Game game, Universe universe) {
		//##### ### ##  ###
		//  #   # # # # # #  Koordinaten in float
		//  #   # # # # # #
		//  #   ### ##  ###
		
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
	
	public void paint(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.fillOval(x, y, 40, 40);
	}
	
	public double getSpeedY() {
		return speedY;
	}
	
	public int getSpeedX() {
		return speedX;
	}
	
	public LinkedList<ItemPart> createItemParts() {
		LinkedList<ItemPart> return_list = new LinkedList<ItemPart>();
		return_list.add(new ItemPart(x, y, true, game, universe));
		return_list.add(new ItemPart(x + 20, y, false, game, universe));
		return return_list;
	}
	
	public void checkHit(LinkedList<Point> points) {
		Ellipse2D.Double circle = new Ellipse2D.Double(x + 20, y + 20, 20, 20);
		for (Point p : points) {
			if (circle.contains((Point2D) p)) {
				game.hit(this);
				return;
			}
		}
	}
	
	public int getItemTypeID() {
		return ItemTypeID;
	}
	
	public boolean onScreen() {
		if (x > -50 && x < universe.getWidth() && y > -50 && y < universe.getHeight()) {
			return true;
		} else {
			return false;
		}
	}
}
