package de.duererInfoProject.fruitNinja;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

//Bomb that decreases the score by ten
public class ScoreBomb extends Item {

	public static final int ItemTypeID = 7;
	private BufferedImage img;
	private int rot;
	private int WIDTH = 96, HEIGHT = 124;
	
	public ScoreBomb(int x, Game game, Universe universe) {
		super(x, game, universe);

		try {
			img = ImageIO.read(ScoreBomb.class.getResource("img/score_bomb.png"));
		} catch (IOException e) {
			game.getController().errorMessage(e, "Error while loading points bomb image!");
		}
	}
	
	//Overrides Items paint method to draw the bomb red
	public void paint(Graphics2D g2d) {
		AffineTransform old = g2d.getTransform();
		g2d.rotate(Math.toRadians(rot), x, y); // Rotate the Graphics2D Element by rot around (x, y)
		g2d.drawImage(img, x - (img.getWidth() / 2), y - (img.getHeight() /2), null); //Draw the Image with the center at (x, y)
		g2d.setTransform(old); // Reset rotation
	}
	
	public void move() {
		super.move();
		rot += speedX;
	}
	
	public void checkHit(LinkedList<Point> cursorPoints) {
		Ellipse2D.Double circle = new Ellipse2D.Double(x - (WIDTH / 2), y - (HEIGHT / 2), WIDTH, HEIGHT);
		for (Point p : cursorPoints) {
			if (circle.contains((Point2D) p)) {
				game.hit(this);
				return;
			}
		}
	}
	
	public LinkedList<ItemPart> createItemParts() {
		LinkedList<ItemPart> return_list =  new LinkedList<ItemPart>();
		return_list.add(new ItemExplosion(game, universe));
		return return_list;
	}
	
	public int getItemTypeID() {
		return ItemTypeID;
	}
}
