package de.duererInfoProject.fruitNinja;

import java.util.prefs.Preferences;

import javax.swing.UIManager;

//Controls the application and holds all the references to the other classes 
public class Controller {
	
	private GUI gui;
	private Highscore highscore;
	private Kinect kinect;
	private Universe universe;
	private Preferences preferences;
	private Game game;
	private SoundManager soundManager;

	public static void main(String[] args) {
		new Controller();
	}
	
	public Controller() {
		//Initialize other Classes
		preferences = Preferences.userNodeForPackage(de.duererInfoProject.fruitNinja.Controller.class);
		highscore = new Highscore(this);
		soundManager = new SoundManager(this);
		universe = new Universe(this, "img/background.jpg");
		gui = new GUI(this);
		kinect = new Kinect();
	}

	//Starts a new Game
	public void newGame() {
		gui.showUniverse();
		game = new Game(this);
		game.start();
	}
	
	//Stops the running game
	public void stopGame() {
		gui.backToMenu();
		game.stop();
	}
	
	//Used by UI-Classes to set the look and feel
	public void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			errorMessage(e, "Error while setting style!");
		}
	}
	
	//Stops the running game and instantly starts an new one
	public void restartGame() {
		stopGame();
		newGame();
	}
	
	public GUI getGui() {
		return gui;
	}

	public Highscore getHighscore() {
		return highscore;
	}

	public Kinect getKinect() {
		return kinect;
	}

	public Universe getUniverse() {
		return universe;
	}
	
	public Preferences getPreferences() {
		return preferences;
	}
	
	public Game getGame() {
		return game;
	}
	
	public SoundManager getSoundManager() {
		return soundManager;
	}
	
	public void errorMessage(Exception e, String message) {
		gui.errorMessage(e, message);
	}
	
	//Easter egg: Because I'm too lazy I need this :P
	public void log(String str) {
		System.out.println(str);
	}
}
