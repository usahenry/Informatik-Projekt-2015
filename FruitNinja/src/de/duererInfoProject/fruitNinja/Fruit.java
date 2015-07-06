package de.duererInfoProject.fruitNinja;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;


public class Fruit extends Item {
	
	public static final int ItemTypeID = 1;
	private BufferedImage img;
	private int rot, imgNumber;
	public static int[][] fruitSizes = new int[][] {{126, 88}, {87, 87}, {185, 104}, {129, 90}, {89, 89}, {134, 110}};
	private int width, height;
	
	public Fruit(int x, Game game, Universe universe) {
		super(x, game, universe);
		
		rot = 0;
		imgNumber = new Random().nextInt(6) + 1;
		width = fruitSizes[imgNumber - 1][0];
		height = fruitSizes[imgNumber - 1][1];
		
		try {
			img = ImageIO.read(new File(Fruit.class.getResource("img/fruit_" + imgNumber + ".png").getPath()));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
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
		Ellipse2D.Double circle = new Ellipse2D.Double(x - (width / 2), y - (height / 2), width, height);
		for (Point p : cursorPoints) {
			if (circle.contains((Point2D) p)) {
				game.hit(this);
				return;
			}
		}
	}
	
	//Called by Game if the Item gets hit
	//Creates and returns 2 ItemParts at the current position
	public LinkedList<ItemPart> createItemParts() {
		LinkedList<ItemPart> return_list = new LinkedList<ItemPart>();
		return_list.add(new ItemPart((int) (x - (Math.cos(Math.toRadians(rot)) * (width / 2))), (int) (y - (Math.sin(Math.toRadians(rot)) * (height / 2))), true, game, universe, imgNumber, rot));
		return_list.add(new ItemPart((int) (x + (Math.cos(Math.toRadians(rot)) * (width / 2))), (int) (y + (Math.sin(Math.toRadians(rot)) * (height / 2))), false, game, universe, imgNumber, rot));
		return return_list;
	}
	
	public int getItemTypeID() {
		return ItemTypeID;
	}
}
