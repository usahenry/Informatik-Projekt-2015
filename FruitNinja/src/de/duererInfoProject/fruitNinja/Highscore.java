package de.duererInfoProject.fruitNinja;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Comparator;
import java.util.LinkedList;

//Controls the Highscore
public class Highscore {

	private File highscoreFile;
	private LinkedList<String[]> highscoreList;
	public final int TOP_NUMBER = 20;

	public Highscore() {
		// Initialize Attributes
		highscoreFile = new File("C://Users//"
				+ System.getProperty("user.name")
				+ "//Documents//FruitNinjaHighscore.txt");
		if (!highscoreFile.isFile()) {
			try {
				highscoreFile.getParentFile().mkdirs();
				highscoreFile.createNewFile();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}

		highscoreList = new LinkedList<String[]>();
	}

	// Save to HighscoreFile
	public void save() {
		if (highscoreList == null) return;
		
		shortenHighscoreList(TOP_NUMBER);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(highscoreFile));
			for (String[] stringArray : highscoreList) {
				for (String string : stringArray) {
					writer.write(string);
					writer.newLine();
				}
			}
			writer.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	// Load from HighscoreFile
	public void load() {
		if (highscoreList == null) return;
		
		try {
			highscoreList.clear();
			BufferedReader reader = new BufferedReader(new FileReader(highscoreFile));
			String line = reader.readLine();
			while (line != null) {
				highscoreList.add(new String[] {line, reader.readLine(), reader.readLine()});
				line = reader.readLine();
			}
			reader.close();
			
			shortenHighscoreList(TOP_NUMBER);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void newHighscore(String[] highscore) {
		shortenHighscoreList(TOP_NUMBER - 1);
		highscoreList.add(highscore);
		sort(1, true);
	}
	
	public void shortenHighscoreList(int number) {
		sort(1, true);
		while (highscoreList.size() > number) {
			highscoreList.removeLast();
		}
	}
	
	public boolean isHighscore(int score) {
		sort(1, true);
		if (highscoreList.size() < TOP_NUMBER) return true;
		return (Integer.parseInt(highscoreList.getLast()[1]) < score);
	}

	// Sorts highscoreList by highcoreList[@valueToSort], sorting order depends on @desc
	public void sort(int valueToSort, boolean desc) {
		highscoreList.sort(new Comparator<String[]>() {
			@Override
			public int compare(String[] o1, String[] o2) {
				int x1 = Integer.parseInt(o1[valueToSort]);
				int x2 = Integer.parseInt(o2[valueToSort]);
				if (x1 > x2 && desc) return -1;
				else if (x1 < x2 && !desc) return -1;
				else if (x1 == x2) return 0;
				else return 1;
			}
		});
	}
	
	public LinkedList<String[]> getHighscoreList() {
		return highscoreList;
	}
}
