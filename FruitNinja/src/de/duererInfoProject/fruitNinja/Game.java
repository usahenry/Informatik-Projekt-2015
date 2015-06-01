package de.duererInfoProject.fruitNinja;

import javax.swing.UIManager;

public class Game {
	
	private GUI gui;
	private Highscore highscore;
	private Kinect kinect;
	private final Universe universe;

	public static void main(String[] args) {
		new Game();

	}
	
	public Game() {
		//Initialize other Classes
		universe = new Universe(this);
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

}
