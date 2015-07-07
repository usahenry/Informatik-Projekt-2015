package de.duererInfoProject.fruitNinja;

import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JDialog;

//A new instance of this is created for every new game started
//Starts and controls a running game
public class Game {

	private Controller controller;
	private Universe universe;
	private Cursor cursor;
	private LinkedList<Item> itemList; //List of all currently active Items
	private LinkedList<ItemPart> itemPartsList; //List of all currently active ItemParts
	private Random random;
	private Timer timer;
	private SoundManager soundManager;
	private int lives, score, playtime;
	public final int SPAWN_BORDER = 200; //Items can only spawn SPAWN_BOREDER away from the window border (on the x-Axis) 
	private final int FRAME_TIME = 10; //Time between each repaint

	public Game(Controller controller) {
		//Getting and initializing attributes
		this.controller = controller;
		universe = controller.getUniverse();
		soundManager = controller.getSoundManager();
		cursor = new Cursor(universe);
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
		soundManager.playSwoosh(itemTypeID);
	}
	
	//Starts the game
	public void start() {
		universe.postInit();
		updateLives();
		updateScore();
		updateTime();
		countdown(3);
		tick(10);
		randomItemTick(3500); //Starts spawning Items 0.5 seconds after the countdown finished
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
	
	//Calls itself every FRAME_TIME milliseconds
	//Tells the universe to repaint and counts up the playtime
	public void tick(int time) {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				playtime++;
				if (playtime % 100 == 0) updateTime(); //Update Time if a full second is reached
				universe.repaint();
				
				tick(FRAME_TIME);
			}
		}, time);
	}
	
	//Spawns a random Item (10 % Bomb, 90 % Fruit) at a random position after @time and repeats it randomly, speeding up over time
	public void randomItemTick(int time) {
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if (random.nextInt(10) > 1) spawnItemRandom(Fruit.ItemTypeID);
				else spawnItemRandom(Bomb.ItemTypeID);
				int bonusTimeToNextTick = Math.round(((-800) / 60) * (playtime / 100)) + 1000; //Calculate additional time to next tick depending on playtime
				if (bonusTimeToNextTick < 200) bonusTimeToNextTick = 300;
				randomItemTick(random.nextInt(700) + bonusTimeToNextTick);
			}
		}, time);
	}
	
	//Called by Items if they left the screen
	//Removes @item from the itemList and updates lives if necessary
	public void itemOutOfScreen(Item item) {
		itemList.remove(item);
		
		if (item.getItemTypeID() == Fruit.ItemTypeID) {
			lives--;
			updateLives();
			soundManager.playMiss();
		}
	}
	
	//Called by ItemParts if they left the screen
	//Removes @itemPart from the itemPartsList
	public void itemPartOutOfScreen(ItemPart itemPart) {
		itemPartsList.remove(itemPart);
	}
	
	//Called by an Items if they got hit by the cursor
	//Removes the item from the list of active items, creates ItemParts and updates score and lives if necessary
	public void hit(Item item) {
		if (item.getItemTypeID() == Fruit.ItemTypeID) {
			for (ItemPart itemPart : item.createItemParts()) itemPartsList.add(itemPart); //Create 2 ItemParts and add them to the list of active itemParts
			score++;
			updateScore();
			soundManager.playSplash();
		} else if (item.getItemTypeID() == Bomb.ItemTypeID) {
			itemPartsList.add(item.createItemParts().getFirst());
			lives--;
			updateLives();
			soundManager.playBomb();
		}
		itemList.remove(item);
	}
	
	public void gameOver() {
		stop();
		GameOverDialog dialog = new GameOverDialog(controller, score, playtime);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}
	
	public void updateLives() {
		universe.setLives(lives);
		if (lives == 0) {
			gameOver();
		}
	}
	
	public void updateScore() {
		universe.setScore(score);
	}
	
	public void updateTime() {
		universe.setTime(Math.round(playtime / 100));
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
