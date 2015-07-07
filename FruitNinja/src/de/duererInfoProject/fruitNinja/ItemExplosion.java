package de.duererInfoProject.fruitNinja;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

//Lets the screen flash when a bomb gets hit
public class ItemExplosion extends ItemPart {
	
	public static final int ItemTypeID = 4;
	public int alpha;
	
	public ItemExplosion(Game game, Universe universe) {
		super(game, universe);
		alpha = 255;
	}
	
	//Decreases alpha and checks if it's still high enough
	public void move() {
		alpha -= 3;
		if (alpha < 10) game.itemPartOutOfScreen(this);
	}
	
	//Paints the white screen flash
	public void paint(Graphics2D g2d) {
		g2d.setColor(new Color(255, 255, 255, alpha));
		g2d.fill(new Rectangle2D.Double(0, 0, universe.getWidth(), universe.getHeight()));
	}
	
	public int getItemTypeID() {
		return ItemTypeID;
	}
}
