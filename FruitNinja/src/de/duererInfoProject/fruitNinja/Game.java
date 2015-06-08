package de.duererInfoProject.fruitNinja;

import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game {

	private Controller controller;
	private Universe universe;
	private LinkedList<Item> itemList;
	private Random random;
	private Timer timer;
	private int lives, score, playtime;
	private boolean active;
	private final int SPAWN_BORDER = 200, FRAME_TIME = 10; //Time between each repaint, 20 -> 50 fps

	public Game(Controller controller) {
		this.controller = controller;
		universe = controller.getUniverse();
		itemList = new LinkedList<Item>();
		random = new Random();
		timer = new Timer();
		lives = 3;
		score = 0;
		playtime = 0;
		active = true;
	}
	
	public void spawnItemRandom(String itemType) {
		int x = random.nextInt(universe.getWidth() - SPAWN_BORDER * 2) + SPAWN_BORDER;
		int y = universe.getHeight() + 40;
		int speedX = random.nextInt(10) + 5;
		int speedY = random.nextInt(10) + 5;
		if (itemType == "Fruit") {
			itemList.add(new Fruit(x, y, speedX, speedY, universe)); //Fruit(x, y, speedX, speedY, universe)
		} else if (itemType == "Bomb") {
			itemList.add(new Bomb(x, y, speedX, speedY, universe));
		}
	}
	
	public void start() {
		countdown(3);
		tick(3000);
		randomItemTick(3500);
	}
	
	public void countdown(int count) {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				universe.setCountdown(count);
				if (count > 0) countdown(count - 1);
			}
		}, 1000);
	}
	
	public void tick(int time) {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				playtime++;
				universe.repaint();
				tick(FRAME_TIME);
			}
		}, time);
	}
	
	public void randomItemTick(int time) {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (random.nextInt(10) > 1) {
					spawnItemRandom("Fruit");
				} else {
					spawnItemRandom("Bomb");
				}
				randomItemTick(random.nextInt(1000) + 500);
			}
		}, time);
	}
}
