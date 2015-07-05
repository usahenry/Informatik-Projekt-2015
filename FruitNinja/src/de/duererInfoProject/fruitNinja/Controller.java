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

	public static void main(String[] args) {
		new Controller();
	}
	
	public Controller() {
		//Initialize other Classes
		preferences = Preferences.userNodeForPackage(de.duererInfoProject.fruitNinja.Controller.class);
		highscore = new Highscore();
		universe = new Universe(this, Controller.class.getResource("img/background.jpg").getPath());
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
			System.err.println(e.getMessage());
		}
	}
	
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
	
	//Easter egg: Because I'm too lazy I need this
	public void log(String str) {
		System.out.println(str);
	}
}
