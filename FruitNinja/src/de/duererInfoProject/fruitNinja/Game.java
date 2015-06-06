package de.duererInfoProject.fruitNinja;

import java.util.LinkedList;
import java.util.Random;

public class Game {

	private Controller controller;
	private Universe universe;
	private LinkedList<Item> items;
	private Random random;

	public Game(Controller controller) {
		this.controller = controller;
		universe = controller.getUniverse();
		items = new LinkedList<Item>();
		random = new Random();
	}
	
	public void spawnFruit() {
		int x = random.nextInt(universe.getWidth() - 200) + 200;
		int y = universe.getHeight() + 40;
//		items.add(new Fruit(x, y, random.nextInt(10), random.nextInt(10), universe)); //Fruit(x, y, speedX, speedY, universe)
	}
	
}
