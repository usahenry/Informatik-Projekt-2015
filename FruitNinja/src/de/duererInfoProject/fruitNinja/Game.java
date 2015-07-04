package de.duererInfoProject.fruitNinja;

import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

//A new instance of this is created for every new game started
//Starts and controls a running game
public class Game {

	private Controller controller;
	private Universe universe;
	private Cursor cursor;
	private LinkedList<Item> itemList;
	private LinkedList<ItemPart> itemPartsList;
	private Random random;
	private Timer timer;
	private int lives, score, playtime;
	private final int SPAWN_BORDER = 200, FRAME_TIME = 10; //Time between each repaint, 20 -> 50 fps

	public Game(Controller controller) {
		//Getting other classes and initializing attributes
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
	}
	
	//Spawns a Item specified by @itemTypeID at a random position, respecting the SPAWN_BORDER
	public void spawnItemRandom(int itemTypeID) {
		int x = random.nextInt(universe.getWidth() - SPAWN_BORDER * 2) + SPAWN_BORDER;
		if (itemTypeID == Fruit.ItemTypeID) {
			itemList.add(new Fruit(x, this, universe));
		} else if (itemTypeID == Bomb.ItemTypeID) {
			itemList.add(new Bomb(x, this, universe));
		}
	}
	
	//Starts the game
	public void start() {
		universe.postInit();
		countdown(3);
		tick(10);
		randomItemTick(3500);
	}
	
	//Stops the game
	public void stop() {
		timer.cancel();
		itemList.clear();
		itemPartsList.clear();
	}
	
	//Starts a countdown for @count seconds and displays it on the universe
	public void countdown(int count) {
		universe.setCountdown(count);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (count > 0) countdown(count - 1);
			}
		}, 1000);
	}
	
	//Called every FRAME_TIME and moves and repaints all items
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
	
	//Spawns a random Item (10 % Bomb, 90 % Fruit) at a random position after @time and repeats it randomly every 0,5 - 1,5 seconds
	public void randomItemTick(int time) {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (random.nextInt(10) > 1) {
					spawnItemRandom(Fruit.ItemTypeID);
				} else {
					spawnItemRandom(Bomb.ItemTypeID);
				}
				randomItemTick(random.nextInt(1000) + 500);
			}
		}, time);
	}
	
	//Called by an item if it left the screen
	//Removes @item from the list and updates lives if necessary
	public void itemOutOfScreen(Item item) {
		itemList.remove(item);
		if (item.getItemTypeID() == Fruit.ItemTypeID) {
			lives--;
			updateLives();
		}
	}
	
	//Called by itemParts if they left the screen
	//Removes @itemPart from the list
	public void itemPartOutOfScreen(ItemPart itemPart) {
		itemPartsList.remove(itemPart);
	}
	
	//Called by an item if they get hit by the cursor
	//Removes the item from the list and updates score and lives if necessary
	public void hit(Item item) {
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
