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
	
	public void spawnItemRandom(int itemID) {
		int x = random.nextInt(universe.getWidth() - SPAWN_BORDER * 2) + SPAWN_BORDER;
		if (itemID == Fruit.ITEM_ID) {
			itemList.add(new Fruit(x, this, universe));
		} else if (itemID == Bomb.ITEM_ID) {
			itemList.add(new Bomb(x, this, universe));
		}
	}
	
	public void start() {
		countdown(3);
		tick(10);
		//itemList.add(new Fruit(universe.getWidth() / 4, universe.getHeight() - 20, 4, 15, universe));
		randomItemTick(3500);
	}
	
	public void stop() {
		timer.cancel();
		itemList.clear();
	}
	
	public void countdown(int count) {
		universe.setCountdown(count);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
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
					spawnItemRandom(1);
				} else {
					spawnItemRandom(2);
				}
				randomItemTick(random.nextInt(1000) + 500);
			}
		}, time);
	}
	
	public void outOfScreen(Item item) {
		itemList.remove(item);
	}
	
	public LinkedList<Item> getItemList() {
		return itemList;
	}
}
