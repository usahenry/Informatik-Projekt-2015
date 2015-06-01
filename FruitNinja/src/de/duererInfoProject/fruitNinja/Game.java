package de.duererInfoProject.fruitNinja;

public class Game {
	
	private GUI gameGUI;
	private Highscore gameHighscore;

	public static void main(String[] args) {
		Game game = new Game();

	}
	
	public Game() {
		//Initialize other Classes
		gameGUI = new GUI();
		gameHighscore = new Highscore();
		gameHighscore.load();
		gameHighscore.sort(1);
		gameHighscore.save();
	}

}
