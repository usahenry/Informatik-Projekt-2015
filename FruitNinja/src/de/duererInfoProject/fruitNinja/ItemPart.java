package de.duererInfoProject.fruitNinja;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.LinkedList;

public class ItemPart extends Item {
	
	public static final int ItemTypeID = 3;
	
	public ItemPart(int x, int y, boolean leftSide, Game game, Universe universe) {
		super(x, y, leftSide, game, universe);
	}
	
	public void paint(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.fillOval(x, y, 20, 40);
	}
	
	public void move() {
		speedY += (universe.getGravity() * mass);
		x += speedX;
		y -= Math.round(speedY);
		if (!onScreen()) {
			game.itemPartOutOfScreen(this);
		}
	}
	
	public int getItemTypeID() {
		return ItemTypeID;
	}
}
