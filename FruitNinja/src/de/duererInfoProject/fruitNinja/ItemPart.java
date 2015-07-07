package de.duererInfoProject.fruitNinja;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

//Parts of an Item flying away when it got hit
public class ItemPart extends Item {
	
	public static final int ItemTypeID = 3;
	private BufferedImage img;
	private int rot;
	
	public ItemPart(int x, int y, boolean leftSide, Game game, Universe universe, int imgNumber, int rot) {
		//Calling Item's alternative constructor
		super(x, y, leftSide, game, universe, rot);
		
		//Initializing attributes
		this.rot = rot;
		String side = "_right.png";
		if (leftSide) side = "_left.png";

		//Loading image
		try {
			img = ImageIO.read(Fruit.class.getResource("img/fruit_" + imgNumber + side));
		} catch (IOException e) {
			game.getController().errorMessage(e, "Error while loading fruit part image!");
		}
	}
	
	//Alternate cunstructor for ItemExplosions
	public ItemPart(Game game, Universe universe) {
		super(0, 0, false, game, universe, 0);
	}
	
	//Called in every universe.repaint()
	//Paints the ItemPart on the screen
	public void paint(Graphics2D g2d) {
		AffineTransform old = g2d.getTransform();
		g2d.rotate(Math.toRadians(rot), x, y); // Rotate the Graphics2D Element by rot around (x, y)
		g2d.drawImage(img, x - (img.getWidth() / 2), y - (img.getHeight() /2), null); //Draw the Image with the center at (x, y)
		g2d.setTransform(old); // Reset rotation
	}
	
	//Called in every universe.repaint()
	//Moves the ItemPart
	public void move() {
		speedY -= (universe.getGravity() * mass);
		x += speedX;
		y += Math.round(speedY);
		if (!onScreen()) {
			game.itemPartOutOfScreen(this);
		}
		rot += speedX;
	}
	
	public int getItemTypeID() {
		return ItemTypeID;
	}
}
