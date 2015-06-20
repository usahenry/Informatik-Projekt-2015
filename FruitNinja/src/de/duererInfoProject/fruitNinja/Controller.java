package de.duererInfoProject.fruitNinja;

import java.util.prefs.Preferences;

import javax.swing.UIManager;

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
		universe = new Universe(this, Controller.class.getResource("img/background.jpg").getPath());
		gui = new GUI(this);
		highscore = new Highscore();
		kinect = new Kinect();
	}

	public void newGame() {
		game = new Game(this);
		game.start();
	}
	
	public void stopGame() {
		game.stop();
	}
	
	public void setLookAndFeel() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
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
	
	public void log(String str) {
		System.out.println(str);
	}
}
