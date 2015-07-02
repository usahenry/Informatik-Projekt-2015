package de.duererInfoProject.fruitNinja;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.LinkedList;

public class Cursor {
	
	private Game game;
	private Universe universe;
	private LinkedList<Point> lastPoints;
	private final int LAST_POINTS_NUMBER = 7;
	
	
	public Cursor(Game game, Universe universe) {
		this.game = game;
		this.universe = universe;
		lastPoints = new LinkedList<Point>();
		lastPoints.add(new Point(universe.getWidth() / 2, universe.getHeight() / 2));
	}
	
	public void updatePoints() {
		while (lastPoints.size() >= LAST_POINTS_NUMBER) {
			lastPoints.remove();
		}
		lastPoints.add(universe.getMousePosition());
	}
	
	public LinkedList<Point> getPoints() {
		LinkedList<Point> returnList = new LinkedList<Point>();
		for (int i = 0; i < (lastPoints.size() - 1); i++) {
			for (Point p : getPointsOnLine(lastPoints.get(i), lastPoints.get(i + 1))) {
				returnList.add(p);
			}
		}
		return returnList;
	}
	
	public void paint(Graphics2D g2d) {
		g2d.setColor(Color.RED);
		g2d.setStroke(new BasicStroke(5));
		for (int i = 0; i < (lastPoints.size() - 1); i++) {
			Point p1 = lastPoints.get(i);
			Point p2 = lastPoints.get(i + 1);
			g2d.drawLine((int) p1.getX(), (int) p1.getY(), (int) p2.getX(), (int) p2.getY());
		}
	}
	
	public LinkedList<Point> getPointsOnLine(Point p1, Point p2) {
		int x = (int) p1.getX();
		int y = (int) p1.getY();
		int x2 = (int) p2.getX();
		int y2 = (int) p2.getY();
		LinkedList<Point> returnList = new LinkedList<Point>();
		int w = x2 - x;
	    int h = y2 - y;
	    int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
	    if (w<0) dx1 = -1 ; else if (w>0) dx1 = 1;
	    if (h<0) dy1 = -1 ; else if (h>0) dy1 = 1;
	    if (w<0) dx2 = -1 ; else if (w>0) dx2 = 1;
	    int longest = Math.abs(w);
	    int shortest = Math.abs(h);
	    if (!(longest>shortest)) {
	        longest = Math.abs(h);
	        shortest = Math.abs(w);
	        if (h<0) dy2 = -1 ; else if (h>0) dy2 = 1;
	        dx2 = 0;
	    }
	    int numerator = longest >> 1;
	    for (int i = 0; i <= longest; i++) {
	        returnList.add(new Point(x, y));
	        numerator += shortest;
	        if (!(numerator < longest)) {
	            numerator -= longest;
	            x += dx1;
	            y += dy1;
	        } else {
	            x += dx2;
	            y += dy2;
	        }
	    }
	    return returnList;
	}
}
