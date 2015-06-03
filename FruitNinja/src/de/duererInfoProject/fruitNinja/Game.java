package de.duererInfoProject.fruitNinja;

import java.util.prefs.Preferences;

import javax.swing.UIManager;

public class Game {
	
	private GUI gui;
	private Highscore highscore;
	private Kinect kinect;
	private final Universe universe;
	private Preferences preferences;

	public static void main(String[] args) {
		new Game();

	}
	
	public Game() {
		//Initialize other Classes
		preferences = Preferences.userNodeForPackage(de.duererInfoProject.fruitNinja.Game.class);
		universe = new Universe(this, Game.class.getResource("img/background.jpg").getPath());
		gui = new GUI(this);
		highscore = new Highscore();
		kinect = new Kinect();
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
	
	public void log(String str) {
		System.out.println(str);
	}
}
