package de.duererInfoProject.fruitNinja;

import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game {

	private Controller controller;
	private Universe universe;
	private Cursor cursor;
	private LinkedList<Item> itemList;
	private LinkedList<ItemPart> itemPartsList;
	private Random random;
	private Timer timer;
	private int lives, score, playtime;
	private boolean active;
	private final int SPAWN_BORDER = 200, FRAME_TIME = 10; //Time between each repaint, 20 -> 50 fps

	public Game(Controller controller) {
		this.controller = controller;
		universe = controller.getUniverse();
		cursor = new Cursor(this, universe);
		itemList = new LinkedList<Item>();
		itemPartsList = new LinkedList<ItemPart>();
		random = new Random();
		timer = new Timer();
		lives = 3;
		score = 0;
		playtime = 0;
		active = true;
	}
	
	public void spawnItemRandom(int itemTypeID) {
		int x = random.nextInt(universe.getWidth() - SPAWN_BORDER * 2) + SPAWN_BORDER;
		if (itemTypeID == Fruit.ItemTypeID) {
			itemList.add(new Fruit(x, this, universe));
		} else if (itemTypeID == Bomb.ItemTypeID) {
			itemList.add(new Bomb(x, this, universe));
		}
	}
	
	public void start() {
		universe.setLives(3);
		countdown(3);
		tick(10);
		randomItemTick(3500);
	}
	
	public void stop() {
		timer.cancel();
		itemList.clear();
		itemPartsList.clear();
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
	
	public void itemOutOfScreen(Item item) {
		itemList.remove(item);
		lives--;
		updateLives();
	}
	
	public void hit(Item item) {
		controller.log("hit");
		if (item.getItemTypeID() == Fruit.ItemTypeID) {
			for (ItemPart itemPart : item.createItemParts()) {
				itemPartsList.add(itemPart);
			}
			score++;
			updateScore();
		} else if (item.getItemTypeID() == Bomb.ItemTypeID) {
			lives--;
			updateLives();
		}
		itemList.remove(item);
	}
	
	public void updateScore() {
		universe.setScore(score);
	}
	
	public void updateLives() {
		universe.setLives(lives);
	}
	
	public Cursor getCursor() {
		return cursor;
	}
	
	public Controller getController() {
		return controller;
	}
	
	public LinkedList<Item> getItemList() {
		return itemList;
	}
	
	public LinkedList<ItemPart> getItemPartsList() {
		return itemPartsList;
	}
}
